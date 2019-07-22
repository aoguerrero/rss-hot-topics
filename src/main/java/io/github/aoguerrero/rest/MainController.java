package io.github.aoguerrero.rest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rometools.rome.io.FeedException;

import io.github.aoguerrero.rest.model.HotTopic;
import io.github.aoguerrero.services.AnalysisService;
import io.github.aoguerrero.services.FrequencyService;

/**
 * Main Rest Controller, containing all the endpoints available
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@RestController
public class MainController {

	final static Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private AnalysisService analysisService;

	@Autowired
	private FrequencyService frequencyService;

	/**
	 * Takes two or more RSS as parameter, fetch their contents and analyse the
	 * returned feeds identifying wich are the more frequent terms or hot topics
	 * present in the titles, excluding words defined in the configuration
	 * (application.properties) as stopwords or ignored words.
	 * 
	 * The results are saved and identified with an exeution ID, which is returned
	 * for future query (using the /frequency/{execution} API)
	 * 
	 * @param urls List of URLs of the RSS to analyse
	 * @return Identifier that can be used to query the results
	 */
	@Transactional
	@RequestMapping(value = "/analyse/new", method = RequestMethod.POST)
	public ResponseEntity<String> analyseNew(@RequestBody List<String> urls) {

		String execution = UUID.randomUUID().toString();

		try {
			if (urls.size() < 2) {
				logger.error("At least two urls are required");
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			for (String url : urls) {
				/* Invokes the analysis of each URL in asynchronous way */
				analysisService.fetchProcessSave(execution, url);
			}
			return new ResponseEntity<String>(execution, HttpStatus.OK);
		} catch (FeedException fe) {
			logger.error("There was an error parsing the RSS feed", fe);
		} catch (IOException ioe) {
			logger.error("There was an error getting the data from the URL", ioe);
		} catch (Exception e) {
			logger.error("Unexpected error", e);
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * Receives an ID that references the results of a previous feed analysis and
	 * returns the name of the three top hot topics with the titles and links which
	 * contains the topic.
	 * 
	 * @param execution Identifier of the feed analysis, executed previously
	 * @return The name of the hot topics with the information of the feeds which
	 *         contains the topic
	 */
	@RequestMapping(value = "/frequency/{execution}", method = RequestMethod.GET)
	public ResponseEntity<List<HotTopic>> frequency(@PathVariable String execution) {
		try {
			List<HotTopic> result = frequencyService.getFrequencies(execution);
			if (result.size() == 0) {
				return new ResponseEntity<List<HotTopic>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<HotTopic>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Unexpected error", e);
			return new ResponseEntity<List<HotTopic>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
