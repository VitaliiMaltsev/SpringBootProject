package appPackage.lessons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
	
	@Autowired
	private LessonRepository lessonRepository;
		
	
	public List<Lesson>getAllLessons(Long courseId){
		//List<Lesson>courses = new ArrayList<>();
		//lessonRepository.findByTopicId(topicId)
		//.forEach(courses::add);
		//return courses;
		return lessonRepository.findByCourseId(courseId);
	}

	public Lesson getLesson(Long id) {
		return lessonRepository.findById(id).get();
	}

	public void addLesson(Lesson lesson) {
         lessonRepository.save(lesson);
	}

	public void updateLesson(Lesson lesson) {
		lessonRepository.save(lesson);
	}

	public void deleteLesson(Long id) {
		lessonRepository.deleteById(id);
		
	}
}
