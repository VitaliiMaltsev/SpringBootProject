package appPackage.courses;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long>{
	
	public Page<Course> findByTopicId(String topicId, Pageable pageable);
	
	public Page <Course> findByName(String name, Pageable pageable);
	public Page <Course> findAll(Pageable pageable);

}
