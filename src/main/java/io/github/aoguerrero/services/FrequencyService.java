package io.github.aoguerrero.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.aoguerrero.persistence.TopicRepository;
import io.github.aoguerrero.rest.model.HotTopic;

/**
 * Service for querying the results of a previous analysis
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Service
public class FrequencyService {

	@Autowired
	private TopicRepository topicRepository;

	/**
	 * Gets results of a previous analysis referenced by the given execution Id.
	 * 
	 * @param execution Identifier of the previous analysis
	 * @return Information about the hot topics
	 */
	public List<HotTopic> getFrequencies(String execution) {
		return topicRepository.getHotTopics(execution);
	}

}
