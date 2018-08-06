package com.uscis.news.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uscis.news.api.NewsSearchApi;
import com.uscis.news.api.impl.BingNewsSearchApiImpl;
import com.uscis.news.api.impl.GoogleNewsSearchApiImpl;
import com.uscis.news.config.Config;

/**
 * Hello world!
 *
 */
public class App {
	
	static NewsSearchApi newsApi = null;
	static Config config = new Config();

	public static void main(String[] args) {

		switch (args[0]) {
		case "bing":
			newsApi = new BingNewsSearchApiImpl(config);
			break;
		case "google":
			newsApi = new GoogleNewsSearchApiImpl(config);
			break;
		default:
			newsApi = new GoogleNewsSearchApiImpl(config);
			break;
		}

		
		String result;
		try {
			result = newsApi.searchNews(args[1]);
			System.out.println("\nJSON Response:\n");
			System.out.println(prettify(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// pretty-printer for JSON; uses GSON parser to parse and re-serialize
	public static String prettify(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

}


