package appPackage.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import appPackage.topics.Topic;

@RestController
public class RESTCourseController {

    private final CourseService courseService;

    @Autowired
    public RESTCourseController(CourseService courseService) {
        this.courseService=courseService;
    }

	@RequestMapping("/rest/topics/{topicId}/courses")
	public List<Course> getCourses(@PathVariable String topicId) {
		return courseService.getAllCourses(topicId);
	}

    @RequestMapping("/rest/topics/{topicId}/courses/{courseId}")
    public Course getCourse(@PathVariable("topicId") String topicId, @PathVariable("courseId") Long courseId) {
        return courseService.getCourse(courseId);
    }

    @RequestMapping(value = "/rest/topics/{topicId}/courses", method = RequestMethod.POST)
    public void addCourse(@RequestBody Course course, @PathVariable String topicId) {
        course.setTopic(new Topic(topicId, "", ""));
        courseService.addCourse(course, topicId);
    }

    @RequestMapping(value = "/rest/topics/{topicId}/courses/{courseId}", method = RequestMethod.PUT)
    public void updateCourse(@RequestBody Course course, @PathVariable Long courseId, @PathVariable String topicId) {
        course.setTopic(new Topic(topicId, "", ""));
        course.setId(courseId);
        courseService.updateCourse(course);
    }

    @RequestMapping(value = "/rest/topics/{topicId}/courses/{courseId}", method = RequestMethod.DELETE)
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }
}
