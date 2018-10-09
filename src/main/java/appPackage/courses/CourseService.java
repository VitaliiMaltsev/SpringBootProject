package appPackage.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
		
	
	public Page<Course>getAllCourses(String topicId, Pageable pageable){
		//List<Lesson>courses = new ArrayList<>();
		//courseRepository.findByTopicId(topicId)
		//.forEach(courses::add);
		//return courses;
		return courseRepository.findByTopicId(topicId, pageable);
	}
	
	public Course getCourse(Long id) {
		return courseRepository.findById(id).get();
	}

	public Page<Course> getCoursesByName(String name, Pageable pageable){
		return courseRepository.findByName(name, pageable);
	}
	
	public void addCourse(Course course) {
         courseRepository.save(course);		
	}

	public void updateCourse(Course course) {
		courseRepository.save(course);
	}

	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
		
	}
}
