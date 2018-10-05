package appPackage.hello;

import appPackage.courses.Course;
import appPackage.courses.CourseService;
import appPackage.model.Role;
import appPackage.model.User;
import appPackage.model.UserRepository;
import appPackage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

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

    @GetMapping("/user-courses/{user}")
    public String userCourses(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(required = false) Course course,
            Model model
    ) {
        Set<Course> courses = user.getCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("course", course);
        model.addAttribute("userChannel", user);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        return "user/userCourses";
    }

    @PostMapping("/user-courses/{user}")
    public String updateCorse(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Course course,
            @RequestParam("name") String name,
            @RequestParam("description") String description

    ) throws IOException {
        if (course.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(name)) {
                course.setName(name);
            }
            if (!StringUtils.isEmpty(description)) {
                course.setDescription(description);
            }
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuid = UUID.randomUUID().toString();
                String resultFileName = /*uuid + "." +*/file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                course.setFilename(resultFileName);

            }
            courseService.updateCourse(course);
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
