package com.springboot.assignment2376;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SpringBootApplication

//REST Controller class
@RestController
public class Assignment2376Application {
	public static void main(String[] args) {
		SpringApplication.run(Assignment2376Application.class, args);


	}

	//get Mapping to grab all tweets
	@GetMapping("/getAllTweets")
	public Map<Long,Map<String,Object>> getTweets() throws JsonProcessingException {

		//rest template
		RestTemplate restTemplate = new RestTemplate();

		//URL to grab data
		String result = restTemplate.getForObject("https://foyzulhassan.github.io/files/favs.json", String.class);

		//parse JSON in map, puts all tweets into key, value pairs, mimicking the JSON format
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> allTweets = objectMapper.readValue(result, new TypeReference<List<Map<String, Object>>>() {
		});

		Map<Long, Map<String, Object>> superMap = new HashMap<>();

		//pulls specific data and stores inside of the submap
		allTweets.forEach(tweet -> {

			String createTime = (String) tweet.get("created_at");
			Long id = (Long) tweet.get("id");
			String tweetText = (String) tweet.get("text");

			if (createTime != null && id != null && tweetText != null) {

				Map<String, Object> subMap = new HashMap<>();

				//stores info in submap
				subMap.put("created_at", createTime);
				subMap.put("id", id);
				subMap.put("text", tweetText);

				//stores info in supermap by id
				superMap.put(id, subMap);
			}
		});

		return superMap;
	}

	//get mapping to grab url list
	@GetMapping("/urlList")
	public Map<Long, List<String>> getUrls() throws JsonProcessingException {

		//rest template
		RestTemplate restTemplate = new RestTemplate();

		//URL to grab data
		String result = restTemplate.getForObject("https://foyzulhassan.github.io/files/favs.json", String.class);

		//parse JSON in map, puts all tweets into key, value pairs, mimicking the JSON format
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> allTweets = objectMapper.readValue(result, new TypeReference<List<Map<String, Object>>>() {
		});

		Map<Long, List<String>> listByTweetId = new HashMap<>();


		// loops through all tweets
		for (Map<String, Object> tweet : allTweets) {
			Long id = (Long) tweet.get("id");

			if (id != null) {
				List<String> urls = new ArrayList<>();
				JsonNode entitiesNode = objectMapper.valueToTree(tweet.get("entities"));

				//pulls data for url from entities, then stores url
				if (entitiesNode != null) {

					JsonNode superUrlNode = entitiesNode.get("urls");
					if (superUrlNode != null && superUrlNode.isArray()) {

						for (JsonNode urlNode : superUrlNode) {
							String url = urlNode.get("url").asText();
							urls.add(url);
						}
					}
				}
				//stores info in listByTweetId, based on id
				listByTweetId.put(id, urls);
			}
		}

		return listByTweetId;
	}

	//request mapping to return based on id
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUser(@PathVariable Long id) throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();

		//URL to grab data
		String result = restTemplate.getForObject("https://foyzulhassan.github.io/files/favs.json", String.class);

		//parse JSON in map, puts all tweets into key, value pairs, mimicking the JSON format
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> allTweets = objectMapper.readValue(result, new TypeReference<List<Map<String, Object>>>() {});

		//map to store tweet details
		Map<String, Object> tweetDetails = new HashMap<>();

		//loops through all tweets
		for (Map<String, Object> tweet : allTweets) {

			Long tweetId = (Long) tweet.get("id");

			if (tweetId.equals(id)) {

				//grabs user using the tweet
				Map<String, Object> user = (Map<String, Object>) tweet.get("user");

				if (user != null) {

					//stores info in tweetDetails
					tweetDetails.put("created_at", tweet.get("created_at"));
					tweetDetails.put("text", tweet.get("text"));
					tweetDetails.put("screen_name", user.get("screen_name"));
					tweetDetails.put("lang", user.get("lang"));
					break;
				}
			}
		}

		return tweetDetails;
	}

	//request mapping to return based on screen name
	@RequestMapping("/userProfile/{screenName}")
	public Map<String, Object> getUserProfile(@PathVariable String screenName) throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();
		//URL to grab data
		String result = restTemplate.getForObject("https://foyzulhassan.github.io/files/favs.json", String.class);

		//parse JSON in map, puts all tweets into key, value pairs, mimicking the JSON format
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> allTweets = objectMapper.readValue(result, new TypeReference<List<Map<String, Object>>>() {});

		//map to store user profile information
		Map<String, Object> userProfileInfo = new HashMap<>();

		//loops through all tweets
		for (Map<String, Object> tweet : allTweets) {

			Map<String, Object> user = (Map<String, Object>) tweet.get("user");


			if (user != null) {

				//checks if user's screen name is equal to the inputted screen name
				String userScreenName = (String) user.get("screen_name");
				if (userScreenName != null && userScreenName.equalsIgnoreCase(screenName)) {

					//stores info in userProfileInfo
					userProfileInfo.put("name", user.get("name"));
					userProfileInfo.put("location", user.get("location"));
					userProfileInfo.put("description", user.get("description"));
					break;
				}
			}
		}

		return userProfileInfo;
	}




}

