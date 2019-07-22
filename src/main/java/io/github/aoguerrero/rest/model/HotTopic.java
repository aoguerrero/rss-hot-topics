package io.github.aoguerrero.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Value Object for transfering to the REST interface the information about the
 * Hot Topic
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
public class HotTopic {

	private String name;
	private List<HotTopicFeed> hotTopicFeeds;

	public HotTopic(String name) {
		this.name = name;
		this.hotTopicFeeds = new ArrayList<HotTopicFeed>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HotTopicFeed> getHotTopicFeeds() {
		return hotTopicFeeds;
	}

	public void setHotTopicFeeds(List<HotTopicFeed> hotTopicFeeds) {
		this.hotTopicFeeds = hotTopicFeeds;
	}

}
