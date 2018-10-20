package appPackage.lessons;

import appPackage.courses.Course;
import appPackage.topics.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RESTLessonController {
	
	@Autowired
	private LessonService lessonService;

	//TODO fix pagination
	
	@RequestMapping("/rest/topics/{topicId}/courses/{courseId}/lessons")
	public List<Lesson> getLessons(@PathVariable Long courseId) {
		return lessonService.getAllLessons(courseId);
	}

		@RequestMapping("/rest/topics/{topicId}/courses/{courseId}/lessons/{lessonId}")
		public Lesson getLesson(@PathVariable Long lessonId/*, @PathVariable("courseId") String courseId*/) {
		return lessonService.getLesson(lessonId);
	}

		@RequestMapping(value="/rest/topics/{topicId}/courses/{courseId}/lessons", method=RequestMethod.POST)
		public void addLesson(@RequestBody Lesson lesson, @PathVariable Long courseId,@PathVariable String topicId) {
			lesson.setCourse(new Course(courseId,"","",topicId));
			lessonService.addLesson(lesson);
		}
		@RequestMapping(value="/rest/topics/{topicId}/courses/{courseId}/lessons/{lessonId}", method=RequestMethod.PUT)
		public void updateCourse(@RequestBody Lesson lesson,
								 @PathVariable Long courseId,
								 @PathVariable String topicId
								 /*@PathVariable String lessonId */ ) {
			lesson.setCourse(new Course(courseId,"","",topicId));
			//lesson.setId(courseId);
			lessonService.updateLesson(lesson);
		}
		@RequestMapping(value="/rest/topics/{topicId}/courses/{courseId}/lessons/{lessonId}", method=RequestMethod.DELETE)
		public void deleteLesson(@PathVariable Long lessonId) {
			lessonService.deleteLesson(lessonId);
		}
}
