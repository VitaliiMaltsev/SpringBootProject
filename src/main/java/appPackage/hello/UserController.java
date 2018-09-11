package appPackage.hello;

import appPackage.model.Role;
import appPackage.model.User;
import appPackage.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String userList(Model model){
        model.addAttribute("userList",userRepository.findAll());
        return "userList";
    }
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PostMapping
    public String updateUser(
            @ModelAttribute(value="user") User user,
            @RequestParam Long userId,
            @RequestParam String userName){
        User user1 = userRepository.findById(userId).get();

        user1.setName(userName);
        user1.setRoles(user.getRoles());
       userRepository.save(user1);
        return "redirect:/users";
    }
}
