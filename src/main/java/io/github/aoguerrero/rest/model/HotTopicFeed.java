package io.github.aoguerrero.rest.model;

/**
 * Value Object for transfering to the REST interface the information about the
 * Hot Topic Feeds
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
public class HotTopicFeed {

	private final String title;

	private final String link;

	public HotTopicFeed(String title, String link) {
		this.title = title;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

}
