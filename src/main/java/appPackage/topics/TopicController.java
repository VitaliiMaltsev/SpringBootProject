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





}
