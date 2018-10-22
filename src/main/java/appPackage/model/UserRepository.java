package appPackage.model;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends CrudRepository<User,Long> {
    User findByName(String name);

    List<User> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);

    User findByActivationCode(String code);
}
