package appPackage.lessons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long>{
	
	public Page<Lesson> findByCourseId(Long courseId, Pageable pageable);
	public Page<Lesson> findByName(String name, Pageable pageable);

	public List<Lesson> findByName(String name);

}
