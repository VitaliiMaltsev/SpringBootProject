package appPackage.topics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTTopicController {
	
	private final TopicService topicService;

	@Autowired
	public RESTTopicController(TopicService topicService) {
		this.topicService=topicService;
	}

	@RequestMapping("/rest/topics")
	public List<Topic> getTopics() {
		return topicService.getAllTopics();
	}
		@RequestMapping("/rest/topics/{id}")
		public Topic getTopic(@PathVariable String id) {
		return topicService.getTopic(id);
	}
		
		@RequestMapping(value="/rest/topics", method=RequestMethod.POST/*consumes = MediaType.APPLICATION_JSON_VALUE*/)
		public void addTopic(@RequestBody Topic topic) {
			topicService.addTopic(topic);
		}
		@RequestMapping(value="/rest/topics/{id}", method=RequestMethod.PUT/*consumes = MediaType.APPLICATION_JSON_VALUE*/)
		public void updateTopic(@RequestBody Topic topic, @PathVariable String id) {
			topicService.updateTopic(id, topic);
		}
		@RequestMapping(value="/rest/topics/{id}", method=RequestMethod.DELETE/*consumes = MediaType.APPLICATION_JSON_VALUE*/)
		public void deleteTopic(/*@RequestBody Topic topic,*/ @PathVariable String id) {
			topicService.deleteTopic(id);
		}
}
