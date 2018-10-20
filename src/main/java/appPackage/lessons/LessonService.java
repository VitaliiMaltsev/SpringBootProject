package appPackage.lessons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

	@Autowired
	private LessonRepository lessonRepository;


	public Page<Lesson> getAllLessons(Long courseId,  Pageable pageable){
		//List<Lesson>courses = new ArrayList<>();
		//lessonRepository.findByTopicId(topicId)
		//.forEach(courses::add);
		//return courses;
		return lessonRepository.findByCourseId(courseId, pageable);
	}
	public Page<Lesson> getLessonsByName(String name, Pageable pageable){
		return lessonRepository.findByName(name, pageable);
	}

	public Page<Lesson>getLessonbySearchName(String searchName, Pageable pageable){
		return lessonRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchName,pageable);
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

    public Page<Lesson> getLessonsBySearchName(String searchTerm, Pageable pageable, Long courseId) {
		Page<Lesson> searchLessons = lessonRepository.findByNameContainingIgnoreCase(searchTerm, pageable, courseId);
		return searchLessons;
    }

	public List<Lesson> getAllLessons(Long courseId) {
		return lessonRepository.findByCourseId(courseId);
	}
}
