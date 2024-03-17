package com.tomer.hostedftp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpJsonRequestHandler {
	
	public static String getJson(String endpoint) throws IOException {
		URL url = new URL(endpoint);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			return null;
		}
		
		BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder json = new StringBuilder();
		String line;
		while ((line = input.readLine()) != null) {
			json.append(line);
		}
		input.close();
		
		return json.toString();
		
	}
}
