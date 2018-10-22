package appPackage.courses;

import appPackage.model.User;
import appPackage.model.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository=courseRepository;
    }
//	@Autowired
//	private EntityManager entityManager;

    @Value("${upload.path}")
    private String uploadPath;

    public Page<CourseDTO> getAllCourses(String topicId, Pageable pageable, User user) {
               return courseRepository.findByTopicId(topicId, pageable, user);
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).get();
    }

    public Page<CourseDTO> getCoursesByName(String name, Pageable pageable, User user) {
        return courseRepository.findCoursesByName(name, pageable, user);
    }

    public Page<CourseDTO> getCoursesBySearchName(String searchTerm, Pageable pageable, User user, String topicId) {
        Page<CourseDTO> searchCourses = courseRepository.findByNameContainingIgnoreCase(searchTerm, pageable, user, topicId);
        return searchCourses;
    }

    public boolean addCourse(Course course, String topicId) {
        Course courseFromDB = courseRepository.findByNameAndTopicId(course.getName(), topicId);
        if (courseFromDB != null && courseFromDB.getTopic().getId().equals(course.getTopic().getId())) {
            return false;
        }
        courseRepository.save(course);
        return true;
    }

    public boolean updateCourse(Course course, String courseName, String courseDescription, MultipartFile file) throws IOException {
        Course courseFromDB = courseRepository.findByNameAndTopicId(courseName, course.getTopic().getId());
		if (courseFromDB != null && ! course.getName().equals(courseName)) {

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
            String resultFileName =LocalDateTime.now().hashCode()+ file.getOriginalFilename();
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

    public List<Course> getAllCourses(String topicId) {
        return courseRepository.findByTopicId(topicId);
    }
}
