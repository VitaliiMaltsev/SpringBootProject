package appPackage.courses;

import appPackage.courses.Course;
import appPackage.courses.CourseService;
import appPackage.model.User;
import appPackage.topics.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class CoursesController {
   //@Autowired
   private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/topics/java/courses")
    public String main( @RequestParam(required = false, defaultValue = "")String name, Model model){
        List<Course> javaCourses;
        if(!(name ==null) &&!name.isEmpty()) {
            javaCourses = courseService.getCoursesByName(name);
        } else {
            javaCourses =courseService.getAllCourses("java");
        }
        model.addAttribute("courses", javaCourses);
        model.addAttribute("name", name);
        return "javaCourses";
    }
    @PostMapping("/topics/java/courses")
    public String addCourse(
//            @PathVariable String topicId,
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String description,
            Map<String,Object>model){
        Course course = new Course(name, description, new Topic("java","",""),user);
        courseService.addCourse(course);
        List<Course> javaCourses = courseService.getAllCourses("java");
        model.put("courses", javaCourses);
        return "javaCourses";
    }

    @PostMapping("/cfilter")
    public String getCourseByName(@RequestParam String name, Map<String,Object>model){
        Iterable<Course> courses;
        if(!(name ==null) &&!name.isEmpty()) {
            courses = courseService.getCoursesByName(name);
        } else {
            courses =courseService.getAllCourses("java");
        }
        model.put("courses", courses);
        return "javaCourses";
    }

}
