package io.github.aoguerrero.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit Test for the TopicPicker utility
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TopicPickerTest {

	@Autowired
	private TopicPicker topicPicker;

	@Test
	public void test() {

		Set<String> topics = topicPicker.getTopics("The quick brown Fox jumps over 123 the lazy ... Dog ignore me");
		assertTrue(topics.size() == 5);
		assertTrue(topics.contains("brown"));
		assertTrue(topics.contains("fox"));
		assertTrue(topics.contains("jumps"));
		assertTrue(topics.contains("lazy"));
		assertTrue(topics.contains("dog"));
		assertFalse(topics.contains("123"));
	}

}
