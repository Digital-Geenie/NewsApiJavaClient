package com.uscis.news.api.impl;

import static com.uscis.news.constant.Constant.GOOGLE_HOST;
import static com.uscis.news.constant.Constant.GOOGLE_REFERAL_URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.uscis.news.api.NewsSearchApi;
import com.uscis.news.config.Config;

public class GoogleNewsSearchApiImpl implements NewsSearchApi {
	
private Config appConfig; 
	
	public GoogleNewsSearchApiImpl(Config config) {
		this.appConfig = config;
	}

	public String searchNews(String searchQuery) throws Exception {
		URL url = new URL(appConfig.getProperty(GOOGLE_HOST) + "v=1.0&q=" + URLEncoder.encode(searchQuery, "UTF-8"));
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("Referer", appConfig.getProperty(GOOGLE_REFERAL_URL));

		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		return builder.toString();
	}

}
