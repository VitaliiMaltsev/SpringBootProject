package appPackage.lessons;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long>{
	
	public List<Lesson> findByCourseId(Long courseId);
	
	public List<Lesson> findByName(String name);

}
