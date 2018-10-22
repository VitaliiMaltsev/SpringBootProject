package appPackage.global;

import appPackage.topics.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {


	private final TopicService topicService;

	@Autowired
	public GlobalController(TopicService topicService) {
		this.topicService=topicService;
	}

	@ModelAttribute
	public void layout(Model model) {
		model.addAttribute("topics", topicService.getAllTopics());

	}
}
