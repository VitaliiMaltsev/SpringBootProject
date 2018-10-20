package appPackage.topics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;
		
	
	public List<Topic>getAllTopics(){
		List<Topic>topics = new ArrayList<>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}
	
	public Topic getTopic(String id) {
		//return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
		return topicRepository.findById(id).get();
				//.filter(Topic.class::isInstance)
				//.map(Topic.class::cast);;
	}
	
	public void addTopic(Topic topic) {
          topicRepository.save(topic);		
	}

	public void updateTopic(String id, Topic topic) {
		topicRepository.save(topic);
		
		//for(int i=0;i<topics.size();i++) {
		//	Topic t =topics.get(i);
		//	if(t.getId().equals(id)) {
		//		topics.set(i, topic);
		//		return;
		//	}
		//}
		
	}

	public void deleteTopic(String id) {
		//topics.removeIf(t->t.getId().equals(id));
		topicRepository.deleteById(id);
		//for(int i=0;i<topics.size();i++) {
		//	Topic t =topics.get(i);
		//	if(t.getId().equals(id)) {
		//		topics.remove(i)/*(i, topic)*/;
		//		return;
		//	}
		//}
	}

	public Iterable<Topic> findByName(String name) {
		return topicRepository.findByName(name);
	}
}
