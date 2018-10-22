package appPackage.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TopicController {

    @Value("${upload.path}")
    private String uploadPath;

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService=topicService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("topics", topicService.getAllTopics());
        return "index";
    }

    @GetMapping("/topics")
    public String main( @RequestParam(required = false, defaultValue = "")String name, Model model){
        Iterable<Topic> topics;
        if(!(name ==null) &&!name.isEmpty()) {
            topics = topicService.findByName(name);
        } else {
            topics =topicService.getAllTopics();
        }
        model.addAttribute("topics",topics);
        return "topics";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @PostMapping("/topics")
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
            String resultFileName = /*uuid + "." +*/file.getOriginalFilename();
            file.transferTo(new File(uploadPath+"/"+resultFileName));
            topic.setFilename(resultFileName);
        }
        topicService.addTopic(topic);
        Iterable<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "topics";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/errors/access-denied";
    }
}
