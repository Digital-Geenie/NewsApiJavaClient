package com.uscis.news.config;

import java.util.Properties;

public class Config {
	Properties configFile;

	public Config() {
		configFile = new java.util.Properties();
		try {
			configFile.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}
}
