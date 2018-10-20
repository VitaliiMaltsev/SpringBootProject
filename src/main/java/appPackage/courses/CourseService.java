package appPackage.courses;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import appPackage.model.User;
import appPackage.model.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
//
//	@Autowired
//	private EntityManager entityManager;
    @Value("${upload.path}")
    private String uploadPath;

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
		return courseRepository.findCoursesByName(name, pageable, user);
	}
	public Page<CourseDTO> getCoursesBySearchName(String searchTerm, Pageable pageable, User user, String topicId){
		Page<CourseDTO> searchCourses = courseRepository.findByNameContainingIgnoreCase(searchTerm, pageable, user, topicId);
		return searchCourses;
	}
	
	public boolean addCourse(Course course, String topicId) {
		Course courseFromDB = courseRepository.findByNameAndTopicId(course.getName(),topicId);
		if (courseFromDB != null && courseFromDB.getTopic().getId().equals(course.getTopic().getId())) {
			return false;
		}
         courseRepository.save(course);
		return true;
	}

	public boolean updateCourse(Course course, String courseName, String courseDescription, MultipartFile file ) throws IOException {
		Course courseFromDB = courseRepository.findByNameAndTopicId(courseName,course.getTopic().getId());
		if (courseFromDB != null && courseFromDB.getTopic().getId().equals(course.getTopic().getId())) {
			return false;
		}
		if (!StringUtils.isEmpty(courseName)) {
			course.setName(courseName);
		}
		if (!StringUtils.isEmpty(courseDescription)) {
			course.setDescription(courseDescription);
		}
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String uuid = UUID.randomUUID().toString();
			String resultFileName = /*uuid + "." +*/file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFileName));
			course.setFilename(resultFileName);

		}
		courseRepository.save(course);
		return true;
	}


	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}

    public Page<CourseDTO> getCoursesListForUser(Pageable pageable, User author, User currentUser) {
		return courseRepository.findByUser(pageable, author, currentUser);
    }

	public void updateCourse(Course course) {
	}
}
