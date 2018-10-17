package appPackage.hello;

import appPackage.topics.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class GlobalController {

	@Autowired
	private TopicRepository topicRepository;

	@ModelAttribute
	public void layout(Model model) {
		model.addAttribute("topics", topicRepository.findAll());

	}
}
