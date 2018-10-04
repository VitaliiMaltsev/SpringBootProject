package appPackage.hello;

import appPackage.courses.Course;
import appPackage.courses.CourseRepository;
import appPackage.courses.CourseService;
import appPackage.model.User;
import appPackage.topics.Topic;
import appPackage.topics.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseService courseService;
    @GetMapping("/")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "Boy") String name, Model model){
        model.addAttribute("name", name);
        model.addAttribute("topics", topicRepository.findAll());
        return "index";
    }

    @GetMapping("/main")
    public String main( @RequestParam(required = false, defaultValue = "")String name, Model model){
        Iterable<Topic> topics;
        if(!(name ==null) &&!name.isEmpty()) {
            topics = topicRepository.findByName(name);
        } else {
            topics =topicRepository.findAll();
        }
        model.addAttribute("topics",topics);
        return "main";
    }

    @GetMapping("/maif")
    public String maif(){
        return "javaCourses2";
    }


    @PostMapping("/maif")
    public String addCourse(
            @AuthenticationPrincipal User user,
            @Valid Course course,
            BindingResult bindingResult, //- всегда должен идти перед Model!!!
            Model model) {

        course.setTopic(new Topic("java", "", ""));
        course.setAuthor(user);
        if (bindingResult.hasErrors()) {

//            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            model.addAttribute("course", course);

        } else {
//        Course course = new Course(name, description, new Topic("java","",""),user);
//            model.addAttribute("course", null);
            courseService.addCourse(course);
        }
        List<Course> javaCourses = courseService.getAllCourses("java");
        model.addAttribute("courses", javaCourses);
        return "javaCourses2";

    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @PostMapping("/main")
    public String addTopic(
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam String description,
            Model model) throws IOException {
        Topic topic = new Topic("java",name, description);
        if(file!=null&&!file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String resultFileName = /*uuid + "." +*/file.getOriginalFilename();
            file.transferTo(new File(uploadPath+"/"+resultFileName));
            topic.setFilename(resultFileName);
        }
        topicRepository.save(topic);
        Iterable<Topic> topics = topicRepository.findAll();
        model.addAttribute("topics", topics);
        return "main";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/errors/access-denied";
    }
}
