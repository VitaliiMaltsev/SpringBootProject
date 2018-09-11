package appPackage.lessons;

import appPackage.courses.Course;
import appPackage.topics.Topic;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Lesson {
	@Id
	private String id;
	private String name;
	private String description;
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Course course;
	
	public Lesson() {
			}
	
	public Lesson(String id, String name, String description, int courseId, String topicId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.course = new Course(courseId,"","", topicId);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}
