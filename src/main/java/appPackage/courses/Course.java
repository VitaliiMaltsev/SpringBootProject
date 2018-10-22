package appPackage.courses;

import appPackage.model.User;
import appPackage.model.util.CourseHelper;
import appPackage.topics.Topic;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Length(max = 255)
	private String name;

	@NotBlank
	@Length(max = 2048)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "topic_id")
	private Topic topic;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;

	@ManyToMany
	@JoinTable(
			name="course_likes",
			joinColumns = {@JoinColumn(name="course_id")},
			inverseJoinColumns = {@JoinColumn(name="user_id")}
	)
	private Set<User>likes=new HashSet<>();

	@Length(max = 255)
	private String link="#";

	private String filename;

	private LocalDate addedDate;

	public Course() {
	}

	public Course(String name, String description, Topic topic, User author) {

		this.name = name;
		this.description = description;
		this.topic = topic;
		this.author = author;
	}

	public Course(Long id, String name, String description, String topicId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.topic = new Topic(topicId,"","");
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getAuthorName(){
		return CourseHelper.getAuthorName(author);
	}
	public String getTopicId() {
		return CourseHelper.getTopicId(topic);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
