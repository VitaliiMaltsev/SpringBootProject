package appPackage.model.dto;

import appPackage.courses.Course;
import appPackage.model.User;
import appPackage.model.util.CourseHelper;
import appPackage.topics.Topic;

import java.time.LocalDate;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private User author;
    private String filename;
    private Long likes;
    private Boolean meLiked;
    private Topic topic;
    private String link;

    public CourseDTO(Course course, Long likes, Boolean meLiked) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.author = course.getAuthor();
        this.topic = course.getTopic();
        this.addedDate = course.getAddedDate();
        this.link = course.getLink();
        this.filename = course.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getLink() {
        return link;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    private LocalDate addedDate;

    public Topic getTopic() {
        return topic;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    public String getAuthorName() {
        return CourseHelper.getAuthorName(author);
    }

    public String getTopicId() {
        return CourseHelper.getTopicId(topic);
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                ", topic=" + topic +
                '}';
    }
}
