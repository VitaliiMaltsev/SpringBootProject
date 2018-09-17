package appPackage.courses;

import appPackage.model.User;
import appPackage.topics.Topic;
import appPackage.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class CoursesController {
    //@Autowired
    @Value("${upload.path}")
    private String uploadPath;

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/topics/java/courses")
    public String main(@RequestParam(required = false, defaultValue = "") String name, Model model) {
        List<Course> javaCourses;
        if (!(name == null) && !name.isEmpty()) {
            javaCourses = courseService.getCoursesByName(name);
        } else {
            javaCourses = courseService.getAllCourses("java");
        }
        model.addAttribute("courses", javaCourses);
        model.addAttribute("name", name);
        return "javaCourses";
    }

    @PostMapping("/topics/java/courses")
    public String addCourse(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @Valid Course course,
            BindingResult bindingResult, //- всегда должен идти перед Mo del!!!
            Model model) throws IOException {

        course.setTopic(new Topic("java", "", ""));
        course.setAuthor(user);
        if (bindingResult.hasErrors()) {

            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            model.addAttribute("course", course);

        } else {
            if(file!=null&&!file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuid = UUID.randomUUID().toString();
                String resultFileName = /*uuid + "." +*/file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                course.setFilename(resultFileName);
            }
            courseService.addCourse(course);
            model.addAttribute("course", null);
        }
        List<Course> javaCourses = courseService.getAllCourses("java");
        model.addAttribute("courses", javaCourses);
        return "javaCourses";

    }

}
