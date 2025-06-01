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
	private static final String URL = LOCAL_URL;
	private final HttpClient client = HttpClient.newHttpClient();
	
	public HttpConnecter() { }
	
//	public String getUserJson() {
//		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(LOCAL_URL)).GET().build();
//		
//		try {
//			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			
//			if(response.statusCode() != 200) {
//				throw new RuntimeException("API 호출 실패: " + response.statusCode());
//			}
//
//			return response.body();
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//			return "";
//		}
//  }
	
	public ApiResponse signUpUser(String id, String nickName, String password) {
		User user = new User();
		user.setId(id); user.setNickname(nickName); user.setPassword(password);
		String customUrl = URL + "/user/sign-up-user";
		try {
			String userJson = JSONManager.mapper.writeValueAsString(user);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.PUT(HttpRequest.BodyPublishers.ofString(userJson))
					.build(); 
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
//			if(apiRes.isSuccess()) {
//				//System.out.println(apiRes.getContent());
//				return apiRes.getContent();
//			} else {
//				//System.out.println(apiRes.getError().getCode());
//				return apiRes.getError().getCode();
//			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse signInUser(String id, String password) {
		User user = new User();
		user.setId(id); user.setPassword(password);
		String customUrl = URL + "/user/sign-in-user";
		
		try {
			String userJson = JSONManager.mapper.writeValueAsString(user);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(userJson))
					.build();
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
//			
//			if(apiRes.isSuccess()) {
//				return apiRes.getContent();
//			} else {
//				return apiRes.getError().getCode();
//			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse userInfo(String id) {
		User user = new User();
		user.setId(id);
		String customUrl = URL + "/user/user-info";
		
		try {
			String userJson = JSONManager.mapper.writeValueAsString(user);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(userJson))
					.build();
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			
			return apiRes;
//			if(apiRes.isSuccess()) {
//				return apiRes.getContent();
//			} else {
//				
//				return apiRes.getError().getCode();
//			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
