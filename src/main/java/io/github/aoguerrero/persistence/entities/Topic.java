package io.github.aoguerrero.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Entity for storing the topics related to a feed
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Entity
public class Topic {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Feed feedItem;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Feed getFeedItem() {
		return feedItem;
	}

	public void setFeedItem(Feed feedItem) {
		this.feedItem = feedItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
