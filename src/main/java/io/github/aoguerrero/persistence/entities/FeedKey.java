package io.github.aoguerrero.persistence.entities;

import java.io.Serializable;

/**
 * Class for the compound key of the Feed entity
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
public class FeedKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private String execution;

	private Long id;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.execution.equals(((FeedKey) obj).getExecution()) && this.id.equals(((FeedKey) obj).getId());
	}

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

}
