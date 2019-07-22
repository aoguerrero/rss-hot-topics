package io.github.aoguerrero.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import io.github.aoguerrero.persistence.entities.Feed;
import io.github.aoguerrero.rest.model.HotTopic;
import io.github.aoguerrero.rest.model.HotTopicFeed;

/**
 * Customized repository for the Topic entity
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Repository
public class TopicRepository {

	@Value("${hottopics}")
	private String hottopics;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<HotTopic> getHotTopics(String execution) {
		
		List<Object[]> results = entityManager.createQuery(
				"SELECT t.name, COUNT(t) AS total "
						+ "FROM Topic t " + "WHERE t.feedItem.execution = :execution " + "GROUP BY t.name "
						+ "ORDER BY total DESC, t.name ASC",
				Object[].class).setParameter("execution", execution).setMaxResults(Integer.valueOf(hottopics)).getResultList();

		List<HotTopic> hotTopics = new ArrayList<>();
		
		for (Object[] result : results) {
			HotTopic hotTopic = new HotTopic(result[0].toString());
			
			List<Feed> feedItems = entityManager
					.createQuery("SELECT t.feedItem FROM Topic t WHERE t.name = :name", Feed.class)
					.setParameter("name", hotTopic.getName()).getResultList();
			
			hotTopic.setHotTopicFeeds(feedItems.stream().map(item -> {
				return new HotTopicFeed(item.getTitle(), item.getLink());
			}).collect(Collectors.toList()));
			
			hotTopics.add(hotTopic);
		}
		
		return hotTopics;
	}

}
