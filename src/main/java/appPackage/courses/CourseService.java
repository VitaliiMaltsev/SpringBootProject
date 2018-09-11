package appPackage.courses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
		
	
	public List<Course>getAllCourses(String topicId){
		//List<Lesson>courses = new ArrayList<>();
		//courseRepository.findByTopicId(topicId)
		//.forEach(courses::add);
		//return courses;
		return courseRepository.findByTopicId(topicId);
	}
	
	public Course getCourse(int id) {
		return courseRepository.findById(id).get();
	}

	public List<Course>getCoursesByName(String name){
		return courseRepository.findByName(name);
	}
	
	public void addCourse(Course course) {
         courseRepository.save(course);		
	}

	public void updateCourse(Course course) {
		courseRepository.save(course);
	}

	public void deleteCourse(int id) {
		courseRepository.deleteById(id);
		
	}
}
