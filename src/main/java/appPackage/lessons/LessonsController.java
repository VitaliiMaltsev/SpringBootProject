package appPackage.lessons;

import appPackage.courses.Course;
import appPackage.courses.CourseService;
import appPackage.model.User;
import appPackage.model.util.ControllerUtil;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class LessonsController {

    private final LessonService lessonService;
    private final CourseService courseService;

    @Autowired
    public LessonsController(LessonService lessonService, CourseService courseService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
    }

    @GetMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public String main(@RequestParam(required = false, defaultValue = "") String name,
                       @RequestParam(value = "searchName", required = false) String searchName,
                       @PathVariable String topicId,
                       @PathVariable Long courseId,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        Page<Lesson> pageLessons;

        if (!(searchName == null) && !searchName.isEmpty()) {

            model.addAttribute("searchName", searchName);
            pageLessons = lessonService.getLessonsBySearchName(searchName, pageable, courseId);

        } else {

            pageLessons = lessonService.getAllLessons(courseId, pageable);
        }
        model.addAttribute("courseName", courseService.getCourse(courseId).getName());
        model.addAttribute("pageLessons", pageLessons);
        model.addAttribute("urlLessons", "/topics/" + topicId + "/courses/" + courseId + "/lessons");
        model.addAttribute("name", name);
        return "lessons";
    }

    @PostMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public String addLesson(
            @PathVariable String topicId,
            @PathVariable Long courseId,
            @AuthenticationPrincipal User user,
            @Valid Lesson lesson,
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) throws IOException {

        lesson.setCourse(new Course(courseId, "", "", topicId));
        lesson.setAddedDate(LocalDate.now());
        lesson.setAuthor(user);
        if (bindingResult.hasErrors()) {

            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            model.addAttribute("lesson", lesson);

        } else {
            lessonService.addLesson(lesson);

            model.addAttribute("lesson", null);
        }
        Page<Lesson> pageLessons = lessonService.getAllLessons(courseId, pageable);
        model.addAttribute("pageLessons", pageLessons);
        model.addAttribute("courseId", courseId);
        model.addAttribute("topicId", topicId);
        model.addAttribute("courseName", courseService.getCourse(courseId).getName());

        return "lessons";

    }

}
