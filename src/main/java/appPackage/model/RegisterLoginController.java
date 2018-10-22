package appPackage.model;

import appPackage.model.dto.CaptchaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegisterLoginController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;

    private final RestTemplate restTemplate;

    @Autowired
    public RegisterLoginController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

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
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            @RequestParam String name,
            RedirectAttributes redirectAttributes,
            Model model) {

        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO responseDTO = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

        if (bindingResult.hasErrors() || !responseDTO.isSuccess()) {
            model.addAttribute("captchaError", "Captcha не подтверждена!");
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("userNameError", "User exists");
            return "registration";
        }
        redirectAttributes.addFlashAttribute("checkEmail", "Проверьте Ваш e-mail для активации аккаунта");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        model.addAttribute("message", isActivated);

        return "login";
    }

}
