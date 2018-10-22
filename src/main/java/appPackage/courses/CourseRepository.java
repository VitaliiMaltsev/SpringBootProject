package appPackage.courses;

import appPackage.model.User;
import appPackage.model.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("select new appPackage.model.dto.CourseDTO(" +
            " c, " +
            " count (cl), " +
            " sum(case when cl= :user then 1 else 0 end) > 0" +
            ") " +
            " from Course c left join c.likes cl" +
            " where c.topic.id =:topicId "+
            " group by c"
    )
    Page<CourseDTO> findByTopicId(@Param("topicId") String topicId, Pageable pageable, @Param("user") User user);
    @Query("select c " +
            " from Course c " +
            " where c.name =:name "+
            " and c.topic.id =:topicId "+
            " group by c"
    )
    Course findByNameAndTopicId(@Param("name") String name, @Param("topicId") String topicId);

    @Query("select new appPackage.model.dto.CourseDTO(" +
            " c, " +
            " count (cl), " +
            " sum(case when cl= :user then 1 else 0 end)>0" +
            ") " +
            " from Course c left join c.likes cl" +
            " where (upper(c.name) like upper(concat('%',:searchTerm,'%')) "+
            " or upper(c.description) like upper(concat('%',:searchTerm,'%'))) "+
            " and c.topic.id =:topicId "+
            " group by c"
    )
    Page<CourseDTO> findByNameContainingIgnoreCase(@Param("searchTerm") String searchName, Pageable pageable, @Param("user") User user, @Param("topicId") String topicId);

    Page<Course> findAll(Pageable pageable);

    @Query("select new appPackage.model.dto.CourseDTO(" +
            " c, " +
            " count (cl), " +
            " sum(case when cl= :user then 1 else 0 end) > 0" +
            ") " +
            " from Course c left join c.likes cl" +
            " where c.author =:author "+
            " group by c"
    )
//    @Query("from Course c where c.author= :author")
    Page<CourseDTO> findByUser(Pageable pageable,
                               @Param("author") User author,
                               @Param("user") User user);
        @Query("select new appPackage.model.dto.CourseDTO(" +
            " c, " +
            " count (cl), " +
            " sum(case when cl= :user then 1 else 0 end) > 0" +
            ") " +
            " from Course c left join c.likes cl" +
            " where c.name =:name "+
            " group by c"
    )
        Page<CourseDTO> findCoursesByName(@Param("name") String name, Pageable pageable, @Param("user") User user);

    @Query("select c " +
            " from Course c " +
            " where c.topic.id =:topicId "+
            " group by c"
    )
    List<Course> findByTopicId(@Param("topicId")String topicId);
}
