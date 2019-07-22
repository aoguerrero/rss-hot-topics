package io.github.aoguerrero.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Entity for storing the RSS Feed data
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Entity
@IdClass(FeedKey.class)
public class Feed {

	@Id
	private String execution;

	@Id
	@GeneratedValue
	private Long id;

	@Column(length=2048)
	private String title;

	@Column(length=2048)
	private String link;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "feed_item_execution", referencedColumnName = "execution")
	@JoinColumn(name = "feed_item_id", referencedColumnName = "id")
	private List<Topic> topics;

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

}
