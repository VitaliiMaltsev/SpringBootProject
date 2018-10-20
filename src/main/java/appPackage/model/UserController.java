package appPackage.model;

import appPackage.courses.Course;
import appPackage.courses.CourseService;
import appPackage.lessons.Lesson;
import appPackage.lessons.LessonService;
import appPackage.model.dto.CourseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    LessonService lessonService;

    @Value("${upload.path}")
    private String uploadPath;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String updateUser(
            @ModelAttribute(value = "user") User userWithChanges,
            @RequestParam Long userId,
            @RequestParam Set<Role> userRoles,
            @RequestParam String password,
            @RequestParam String userName) {
        userService.saveUser(userWithChanges, userId, userName, password, userRoles);

        return "redirect:/users";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);
        return "redirect:/users/profile";
    }

    @GetMapping("/user-courses/{author}")
    public String userCourses(
            @AuthenticationPrincipal User currentUser,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable,
            @PathVariable User author,
            @RequestParam(required = false) Course course,
            @RequestParam(required = false) Lesson lesson,
            Model model
    ) {
        Page<CourseDTO> courses = courseService.getCoursesListForUser(pageable, author, currentUser);
        model.addAttribute("pageUserCourses", courses);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("userChannel", author);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("url", "/users/user-courses/"+author.getId());
        return "user/userCourses";
    }
//    @Transactional
    @PostMapping("/user-courses/{user}")
    public String updateCorse(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam(value = "lessonId", required = false) Lesson lesson,
            @RequestParam(value = "lessonName", required = false) String lessonName,
            @RequestParam(value = "lessonDescription", required = false) String lessonDescription,
            @RequestParam(value = "lessonLink", required = false) String lessonLink,
            @RequestParam(value = "courseId", required = false) Course course,
            @RequestParam(value = "courseName", required = false) String courseName,
            @RequestParam(value = "courseDescription", required = false) String courseDescription,
            Model model,
            RedirectAttributes redir
    ) throws IOException {
        if (course!=null && course.getAuthor().equals(currentUser)) {
            if (!courseService.updateCourse(course, courseName, courseDescription, file )) {
                redir.addFlashAttribute("courseUpdateError", "Курс с таким именем существует в разделе " + course.getTopic().getName());
                return "redirect:/users/user-courses/" + user;
            }

        } else if(lesson.getAuthor().equals(currentUser)){
            if (!StringUtils.isEmpty(lessonName)) {
                lesson.setName(lessonName);
            }
            if (!StringUtils.isEmpty(lessonDescription)) {
                lesson.setDescription(lessonDescription);
            }
            if (!StringUtils.isEmpty(lessonLink)) {
                lesson.setLink(lessonLink);
            }
            lessonService.updateLesson(lesson);
            redir.addFlashAttribute("lessonUpdateSuccess", "Урок успешно обновлен" );
            return "redirect:/topics/"+lesson.getCourse().getTopic().getId()+"/courses/"+lesson.getCourse().getId()+"/lessons";
        }
        return "redirect:/users/user-courses/" + user;
    }
    @GetMapping("subscribe/{user}")
    public String subscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser){
        userService.subscribe(currentUser,user);
        return "redirect:/users/user-courses/" + user.getId();
    }
    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser){
        userService.unsubscribe(currentUser,user);
        return "redirect:/users/user-courses/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            Model model
    ){
        model.addAttribute("userChannel", user );
        model.addAttribute("type", type );
        if ("subscriptions".equals(type)){
            model.addAttribute("users", user.getSubscriptions());
        }else{
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscriptions";
    }

}
