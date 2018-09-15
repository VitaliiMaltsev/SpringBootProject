package appPackage.courses;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long>{
	
	public List<Course> findByTopicId(String topicId);
	
	public List<Course> findByName(String name);

}
