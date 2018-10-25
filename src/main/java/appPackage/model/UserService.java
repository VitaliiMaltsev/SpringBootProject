package appPackage.model;

import appPackage.model.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailSender mailSender;
    @Autowired    
    PasswordEncoder passwordEncoder;

//     @Autowired
//     public UserService(UserRepository userRepository, MailSender mailSender, PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.mailSender = mailSender;
//         this.passwordEncoder = passwordEncoder;
//     }

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
        userRepository.save(user);
        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравстуйте, %s!\n" + "Добро пожаловать на VVM.com!\n" +
                            "Пожалуйста перейдите по следующей ссылке для активации вашего аккаунта\n" +
                            "https://vvm-com.herokuapp.com/activate/%s",
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
        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void saveUser( Long userId, String userName, String password, Set<Role> userRoles) {

        User user = userRepository.findById(userId).get();
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if(userRoles!=null) {

            user.setRoles(userRoles);
        }
        if (!StringUtils.isEmpty(userName)) {
            user.setName(userName);
        }
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

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
