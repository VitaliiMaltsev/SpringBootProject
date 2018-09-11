package appPackage.topics;
import appPackage.model.User;

import javax.persistence.*;


@Entity
public class Topic {
	@Id
	private String id;
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "user_id")
//	private User author;

	public Topic(String name, String description) {
		this.name = name;
		this.description = description;
	}

//	public User getAuthor() {
//		return author;
//	}

//	public void setAuthor(User author) {
//		this.author = author;
//	}

	public Topic() {
			}
	
	public Topic(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

//	public String getAuthorName(){
//		return author!=null?author.getName():"none";
//	}

//	public Topic(String name, String description) {
////		this.author =user;
//		this.name = name;
//		this.description = description;
//	}

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
	private String name;
	private String description;
}
