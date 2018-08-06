package com.uscis.news.api.impl;

import static com.uscis.news.constant.Constant.BING_HOST;
import static com.uscis.news.constant.Constant.BING_PATH;
import static com.uscis.news.constant.Constant.BING_SUBSCRIPTION_KEY;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.uscis.news.api.NewsSearchApi;
import com.uscis.news.config.Config;

public class BingNewsSearchApiImpl implements NewsSearchApi {
	
	private Config appConfig; 
	
	public BingNewsSearchApiImpl(Config config) {
		this.appConfig = config;
	}
   
    public String searchNews (String searchQuery) throws Exception {
        // construct URL of search request (endpoint + query string)
        URL url = new URL(appConfig.getProperty(BING_HOST) + appConfig.getProperty(BING_PATH) + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", appConfig.getProperty(BING_SUBSCRIPTION_KEY));

        // receive JSON body
        InputStream stream = connection.getInputStream();
        Scanner in = new Scanner(stream);
        String response = in.useDelimiter("\\A").next();
        
        // construct result object for return
        //SearchResults results = new SearchResults(new HashMap<String, String>(), response);

        // extract Bing-related HTTP headers
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (String header : headers.keySet()) {
            if (header == null) continue;      // may have null key
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                //results.relevantHeaders.put(header, headers.get(header).get(0));
            	System.out.println(headers.get(header).get(0));
            }
        }
        
        stream.close();
        in.close();
        return response;
    }

    
}

