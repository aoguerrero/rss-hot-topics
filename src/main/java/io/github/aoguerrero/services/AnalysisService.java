package io.github.aoguerrero.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.github.aoguerrero.persistence.FeedRepository;
import io.github.aoguerrero.persistence.entities.Feed;
import io.github.aoguerrero.persistence.entities.Topic;
import io.github.aoguerrero.util.TopicPicker;

/**
 * Service for processing the RSS feeds
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Service
@EnableAsync
public class AnalysisService {

	@Autowired
	private TopicPicker topicPicker;

	@Autowired
	private FeedRepository feedRepository;

	/**
	 * Fetch the contents of the feed, process the title identifying the relevant
	 * terms and save the results.
	 * 
	 * @param execution Identifier for the current analysis
	 * @param url       URL of the RSS feed
	 * @throws IOException   When an IO error happens, like network or disk access
	 * @throws FeedException Thtowed if the there are problems with the feed parsing
	 */
	@Async
	public void fetchProcessSave(String execution, String url) throws IOException, FeedException {
		SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(new URL(url)));
		List<SyndEntry> entries = syndFeed.getEntries();

		for (SyndEntry entry : entries) {
			Feed feed = new Feed();
			feed.setExecution(execution);
			feed.setTitle(entry.getTitle());
			feed.setLink(entry.getLink());
			List<Topic> topics = new ArrayList<>();
			Set<String> topicNames = topicPicker.getTopics(entry.getTitle());
			for (String name : topicNames) {
				Topic topic = new Topic();
				topic.setName(name);
				topics.add(topic);
			}
			feed.setTopics(topics);
			feedRepository.save(feed);
		}
	}

}
