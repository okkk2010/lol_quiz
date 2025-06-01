package database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import dataSet.user.SignUpUser;
import dataSet.user.User;

public class HttpConnecter {
	
	public static final HttpConnecter instance = new HttpConnecter();
	private static final String HEROKU_URL = "https://lol-quiz-node-server-2b48f85da5a8.herokuapp.com";
	private static final String LOCAL_URL = "http://localhost:3000";
	private final HttpClient client = HttpClient.newHttpClient();
	
	public HttpConnecter() { }
	
	public String getUserJson() {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(HEROKU_URL)).GET().build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() != 200) {
				throw new RuntimeException("API 호출 실패: " + response.statusCode());
			}

			return response.body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String signUpUser(String id, String nickName, String password) {
		User user = new User();
		user.setId(id); user.setNickname(nickName); user.setPassword(password);
		String customUrl = HEROKU_URL + "/user/sign-up-user";
		try {
			String userJson = JSONManager.mapper.writeValueAsString(user);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.PUT(HttpRequest.BodyPublishers.ofString(userJson))
					.build(); 
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			
			if(apiRes.isSuccess()) {
				//System.out.println(apiRes.getContent());
				return apiRes.getContent();
			} else {
				//System.out.println(apiRes.getError().getCode());
				return apiRes.getError().getCode();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String signInUser(String id, String password) {
		return "";
	}
}
