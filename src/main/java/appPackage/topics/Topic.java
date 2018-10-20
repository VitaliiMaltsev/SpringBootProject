package appPackage.topics;

import appPackage.model.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
public class Topic {
    @Id
    private String id;

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Topic() {
    }

    public Topic(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
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

    @NotBlank(message = "Пожалуйста заполните имя раздела")
    @Length(max = 255, message = "Имя слишком длинное")
    private String name;
    @NotBlank(message = "Пожалуйста заполните описание раздела")
    @Length(max = 2048, message = "Описание раздела слишком длинное")
    private String description;
}
