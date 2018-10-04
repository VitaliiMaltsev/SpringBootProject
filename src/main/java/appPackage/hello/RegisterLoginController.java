package appPackage.hello;

import appPackage.model.User;
import appPackage.model.dto.CaptchaResponseDTO;
import appPackage.service.UserService;
import appPackage.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegisterLoginController {
    private static final String CAPTCHA_URL ="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response")String captchaResponse,
            @Valid User user,
                          BindingResult bindingResult,
                          @RequestParam String name,
                          Model model) {

        String url = String.format(CAPTCHA_URL,secret,captchaResponse);
        CaptchaResponseDTO responseDTO = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

//        if(!responseDTO.isSuccess()){
//        }

//        if(user.getPassword()!=null&&!user.getPassword().equals(user.getPassword2())){
//            model.addAttribute("passwordError", "Введенные пароли не совпадают");
//        }
        if (bindingResult.hasErrors() || !responseDTO.isSuccess()){
//            Map<String, String> errorsMap = ControllerUtil.getErrors(bindingResult);
//            model.mergeAttributes(errorsMap);
            model.addAttribute("captchaError", "Captcha не подтверждена!");
            return "registration";

        }
        if (!userService.addUser(user)) {
            model.addAttribute("userNameError", "User exists");
            return "registration";
        }
        model.addAttribute("checkEmail","Check your e-mail for activation account");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

            model.addAttribute("message", isActivated);

        return "login";
    }

}
