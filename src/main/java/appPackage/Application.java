package appPackage;


import appPackage.model.User;
import appPackage.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//		User user = new User(1,"Vasya");
//		UserService userService = new UserService();
//		userService.addUser(user);
//		TestClass testClass = new TestClass();
//        testClass.proceed();
//
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		session.save(user);
//		session.getTransaction().commit();

    }

//    @Bean
//    public CommandLineRunner demo(UserRepository repository) {
//        return args -> {
//            // save a couple of customers
//            repository.save(new User(1L, "Jack", "Bauer"));
//            repository.save(new User(2L, "Chloe", "O'Brian"));
//            repository.save(new User(3L, "Kim", "Bauer"));
//            repository.save(new User(4L, "David", "Palmer"));
//            repository.save(new User(5L, "Michelle", "Dessler"));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (User customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            repository.findById(1L)
//                    .ifPresent(customer -> {
//                        log.info("Customer found with findById(1L):");
//                        log.info("--------------------------------");
//                        log.info(customer.toString());
//                        log.info("");
//                    });
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findBySurname("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
//            // for (Customer bauer : repository.findByLastName("Bauer")) {
//            // 	log.info(bauer.toString());
//            // }
//            log.info("");
//        };
//    }


}
