package appPackage.service;

import appPackage.model.Role;
import appPackage.model.User;
import appPackage.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;


//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user==null)
        {throw new UsernameNotFoundException ("Пользователь не найден !"); }
        return user;
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByName(user.getName());
        if (userFromDB != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setRegistrationDate(LocalDate.now());
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setPassword2(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        //TODO Очередность влияет?
        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравстуйте, %s!\n" + "Добро пожаловать на VVM.com!\n" +
                            "Пожалуйста перейдите по следующей ссылке для активации вашего аккаунта\n" +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(), user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }


    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void saveUser(User userWithChanges, Long userId, String userName, String password, Set<Role> userRoles) {

        User user = userRepository.findById(userId).get();
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(userRoles);

//        user.setPassword(password);
        user.setName(userName);
//        user.setRoles(userWithChanges.getRoles());
        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChaned = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChaned) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if(isEmailChaned) {
            sendMessage(user);
            user.setActive(false);
        }
        userRepository.save(user);
    }
}
