package appPackage.courses;

import java.util.List;

import appPackage.model.User;
import appPackage.model.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
//
//	@Autowired
//	private EntityManager entityManager;
//
//
	public Page<CourseDTO>getAllCourses(String topicId, Pageable pageable, User user){
		//List<Lesson>courses = new ArrayList<>();
		//courseRepository.findByTopicId(topicId)
		//.forEach(courses::add);
		//return courses;
		return courseRepository.findByTopicId(topicId, pageable, user);
	}
	
	public Course getCourse(Long id) {
		return courseRepository.findById(id).get();
	}

	public Page<CourseDTO> getCoursesByName(String name, Pageable pageable, User user){
		return courseRepository.findByName(name, pageable, user);
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

    public Page<CourseDTO> getCoursesListForUser(Pageable pageable, User author, User currentUser) {
		return courseRepository.findByUser(pageable, author, currentUser);
    }
}
