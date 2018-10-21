package appPackage.courses;

import appPackage.model.User;
import appPackage.model.dto.CourseDTO;
import appPackage.topics.Topic;
import appPackage.topics.TopicService;
import appPackage.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Controller
public class CoursesController {
    //@Autowired
    @Value("${upload.path}")
    private String uploadPath;

    private final CourseService courseService;
    private final TopicService topicService;

    @Autowired
    public CoursesController(CourseService courseService, TopicService topicService) {
        this.courseService = courseService;
        this.topicService = topicService;
    }

    @GetMapping("/topics/{topicId}/courses")
    public String main(
//            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(value = "searchName", required = false) String searchName,
            @PathVariable String topicId,
            Model model,
            @AuthenticationPrincipal User user,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable
            ) {
        Page<CourseDTO> pageJavaCourses;
        if (!(searchName == null) && !searchName.isEmpty()) {
            model.addAttribute("searchName", searchName);
            pageJavaCourses = courseService.getCoursesBySearchName(searchName, pageable, user, topicId);
        } else {
            pageJavaCourses = courseService.getAllCourses(topicId, pageable, user);
        }

        model.addAttribute("page", pageJavaCourses);
        model.addAttribute("url", "/topics/" + topicId + "/courses");
        model.addAttribute("topicName", topicService.getTopic(topicId).getName());
        return "courses";
    }

    @PostMapping("/topics/{topicId}/courses")
    public String addCourse(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable String topicId,
            @AuthenticationPrincipal User user,
            @Valid Course course,
            BindingResult bindingResult, //- всегда должен идти перед Mo del!!!
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable) throws IOException {

        course.setTopic(new Topic(topicId, "", ""));
        course.setAddedDate(LocalDate.now());
        course.setAuthor(user);
        if (bindingResult.hasErrors()) {

            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            model.addAttribute("course", course);

        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
//                String uuid = UUID.randomUUID().toString();
                String resultFileName =LocalDateTime.now().hashCode()+ file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                course.setFilename(resultFileName);
            }
            if (!courseService.addCourse(course,topicId)) {
                model.addAttribute("courseNameError", "Курс с таким именем существует");
            }
            model.addAttribute("course", null);
        }
        Page<CourseDTO> javaCourses = courseService.getAllCourses(topicId, pageable, user);
        model.addAttribute("page", javaCourses);
        model.addAttribute("topicId", topicId);
        model.addAttribute("topicName", topicService.getTopic(topicId).getName());

        return "courses";

    }

    @GetMapping("/courses/{course}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Course course,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = course.getLikes();
        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();

    }


}
