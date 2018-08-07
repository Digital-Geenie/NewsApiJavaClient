package com.uscis.news.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uscis.news.api.NewsSearchApi;
import com.uscis.news.api.impl.BingNewsSearchApiImpl;
import com.uscis.news.api.impl.GoogleNewsSearchApiImpl;
import com.uscis.news.config.Config;


public class NewsSearch {
	
	NewsSearchApi newsApi;
	Config config;
	
	public NewsSearch() {
	    this.newsApi = null;
		this.config = new Config();
	}

	public String newsSearch(String searchQuery, String newsSource) {

		String respone = null;
		
		switch (newsSource) {
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
			result = newsApi.searchNews(searchQuery);
			System.out.println("\nJSON Response:\n");
			respone = prettify(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respone;
	}

	// pretty-printer for JSON; uses GSON parser to parse and re-serialize
	public static String prettify(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

}


