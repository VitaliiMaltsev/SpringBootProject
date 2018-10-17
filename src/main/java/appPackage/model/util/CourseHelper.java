package appPackage.model.util;

import appPackage.model.User;
import appPackage.topics.Topic;

public abstract class CourseHelper {
    public static String getAuthorName(User author){
        return author!=null?author.getName():"<none>";
    }

    public static String getTopicId(Topic topic) {
        return topic!=null?topic.getId():"<none>";

    }
}
