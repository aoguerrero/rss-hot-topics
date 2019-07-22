package io.github.aoguerrero.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility class for getting the topics of a feed
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Component
public class TopicPicker {

	@Value("${stopwords}")
	private String stopwords;

	@Value("${ignoredwords}")
	private String ignoredwords;

	/**
	 * Removes stopwords and ignored from input and return the remaining words as an
	 * array
	 * 
	 * @param input Original RSS feed header
	 * @return The words remained afther stopwords removal
	 */
	public Set<String> getTopics(String input) {

		/* Apply lowercase, remove accents, and non alphanumeric characters */
		String output = input.toLowerCase();
		output = StringUtils.stripAccents(output);
		output = output.replaceAll("[^a-z0-9 ]", "");

		/* Remove stopwords */
		for (String stopword : stopwords.split(",")) {
			output = output.replaceAll("\\b" + stopword + "\\b", "");
		}

		/* Remove ignored terms */
		for (String ignoredword : ignoredwords.split(",")) {
			output = output.replaceAll("\\b" + ignoredword + "\\b", "");
		}

		Set<String> result = new HashSet<>();
		Collections.addAll(result, output.trim().split(" +"));
		return result;
	}
}
