package appPackage.lessons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long>{
	
	public Page<Lesson> findByCourseId(Long courseId, Pageable pageable);
	public Page<Lesson> findByName(String name, Pageable pageable);
	public Page<Lesson> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, Pageable pageable);

	public List<Lesson> findByName(String name);

	@Query("select l"+
			" from Lesson l " +
			" where (upper(l.name) like upper(concat('%',:searchTerm,'%')) "+
			" or upper(l.description) like upper(concat('%',:searchTerm,'%'))) "+
			" and l.course.id =:courseId "+
			" group by l"
	)
    Page<Lesson> findByNameContainingIgnoreCase(@Param("searchTerm")String searchTerm, Pageable pageable,@Param("courseId") Long courseId);

	List<Lesson> findByCourseId(Long courseId);

}
