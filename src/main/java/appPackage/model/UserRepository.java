package appPackage.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Long> {
    User findByName(String name);
    List<User> findBySurname(String surname);
    List<User> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);

    User findByActivationCode(String code);
}
