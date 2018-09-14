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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String index(@RequestParam(name = "name", required = false, defaultValue = "Boy") String name, Map<String,Object>model){
        model.put("name", name);
        return "home";
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
    public String main2(@RequestParam(required = false, defaultValue = "")String name, Model model){
    List<Course> javaCourses = courseService.getAllCourses("java");
        if(!(name ==null) &&!name.isEmpty()) {
            javaCourses = courseService.getCoursesByName(name);
        } else {
            javaCourses =courseService.getAllCourses("java");
        }
        model.addAttribute("courses", javaCourses);
        model.addAttribute("name", name);
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
            Map<String,Object>model) throws IOException {
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
        model.put("topics", topics);
        return "main";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/errors/access-denied";
    }
}
