package appPackage.topics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

	private final TopicRepository topicRepository;

	@Autowired
	public TopicService(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	public List<Topic>getAllTopics(){
		List<Topic>topics = new ArrayList<>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}
	
	public Topic getTopic(String id) {
		return topicRepository.findById(id).get();
	}
	
	public void addTopic(Topic topic) {
          topicRepository.save(topic);		
	}

	public void updateTopic(String id, Topic topic) {
		topicRepository.save(topic);
	}

	public void deleteTopic(String id) {
		topicRepository.deleteById(id);
	}

	public Iterable<Topic> findByName(String name) {
		return topicRepository.findByName(name);
	}
}
