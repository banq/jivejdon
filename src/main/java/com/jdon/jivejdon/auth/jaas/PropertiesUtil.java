package com.jdon.jivejdon.auth.jaas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {
	
	private static Properties props = new Properties();
	
	public static String getProperty(String propertyName)throws IOException {
		if (props.size() == 0) {
			InputStream input = PropertiesUtil.class.getResourceAsStream("/jaas.properties");
			props.load(input);
			input.close();
		}
		return (String) props.get(propertyName);
	}
	
}

