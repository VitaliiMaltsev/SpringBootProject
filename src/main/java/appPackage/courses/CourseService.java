package appPackage.courses;

import appPackage.model.User;
import appPackage.model.dto.CourseDTO;
import appPackage.model.util.CloudinaryConfig;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CloudinaryConfig cloudinaryConfig;


    @Autowired
    public CourseService(CourseRepository courseRepository, CloudinaryConfig cloudinaryConfig) {
        this.courseRepository=courseRepository;
        this.cloudinaryConfig=cloudinaryConfig;
    }

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

    public boolean updateCourse(Course course, String courseName, String courseDescription, MultipartFile multipartFile) throws IOException {
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
		if (multipartFile != null && !multipartFile.getOriginalFilename().isEmpty()) {

            course.setFilename(LocalDate.now().hashCode()+multipartFile.getOriginalFilename());
            Map uploadResult = cloudinaryConfig.upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("recource_type", "auto"));
            course.setFileURL(uploadResult.get("url").toString());
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
