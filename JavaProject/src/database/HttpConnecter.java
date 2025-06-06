package database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import dataSet.quiz.Quiz;
import dataSet.user.SignUpUser;
import dataSet.user.User;
import dataSet.user.UserNTier;

public class HttpConnecter {
	
	public static final HttpConnecter instance = new HttpConnecter();
	private static final String HEROKU_URL = "https://lol-quiz-node-server-2b48f85da5a8.herokuapp.com";
	private static final String LOCAL_URL = "http://localhost:3000";
	private static final String URL = HEROKU_URL;
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
	
	public ApiResponse changePassword(String id, String curPassword, String newPassword) {
		User user = new User();
		user.setId(id); user.setPassword(curPassword); user.setNew_password(newPassword);
		String customUrl = URL + "/user/user-password-change";
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse changeNickname(String id, String newNickname) {
		User user = new User();
		user.setId(id); user.setNickname(newNickname);
		String customUrl = URL + "/user/user-nickname-change";
		
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse userDelete(String id) {
		User user = new User();
		user.setId(id);
		String customUrl = URL + "/user/user-delete";
		
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse createQuiz(String title, String quizName, String answer, String mimeType, byte[] imgData) {
		if(mimeType == null || imgData == null) {
			System.out.println("이미지 데이터가 없습니다.");
			return null; // 이미지 데이터가 없을 경우 처리
		}
		
		ApiResponse apiRes = getImgIdUrl(title, quizName, answer, mimeType);
		if(apiRes == null || !apiRes.isSuccess()) {
			System.out.println("이미지 ID URL 생성 실패");
			return null; // 이미지 ID URL 생성 실패
		}
		
		Quiz quiz = JSONManager.getJsonData(apiRes.getContent(), Quiz.class);
		if(quiz == null) {
			System.out.println("Quiz 객체 생성 실패");
			return null; // Quiz 객체 생성 실패
		}
		
		uploadQuizImg(quiz.getS3_url(), mimeType, imgData);
		
		ApiResponse imgApiRes = uploadQuizImgUrl(quiz.getId());
		if(imgApiRes == null || !imgApiRes.isSuccess()) {
			System.out.println("이미지 URL 업로드 실패");
			return null; // 이미지 URL 업로드 실패
		}
		return imgApiRes; // 최종적으로 이미지 URL 업로드 성공
	}
	
	public ApiResponse getImgIdUrl(String title, String quizName, String answer, String mimeType) {
		Quiz quiz = new Quiz();
		quiz.setTitle(title); quiz.setQuiz_name(quizName); quiz.setAnswer(answer); quiz.setMime_type(mimeType);
		String customUrl = URL + "/quiz/create-quiz-basic";
		
		try {
			String quizJson = JSONManager.mapper.writeValueAsString(quiz);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(quizJson))
					.build();
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void uploadQuizImg(String presignedUrl, String mimeType, byte[] imgData) {
		if(mimeType == null || imgData == null) {
			return; // 이미지 데이터가 없을 경우 처리
		}
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(presignedUrl))
				.header("Content-Type", mimeType)
				.PUT(HttpRequest.BodyPublishers.ofByteArray(imgData))
				.build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if(response.statusCode() != 200) {
				throw new RuntimeException("이미지 업로드 실패: " + response.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ApiResponse uploadQuizImgUrl(int id) {
		Quiz quiz = new Quiz();
		quiz.setId(id);
		String customUrl = URL + "/quiz/set-image-url";
		try {
			String quizJson = JSONManager.mapper.writeValueAsString(quiz);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(quizJson))
					.build();
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse getQuizs(String title) {
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		String customUrl = URL + "/quiz/get-quizs";
		
		try {
			String quizJson = JSONManager.mapper.writeValueAsString(quiz);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(quizJson))
					.build();
			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] loadImage(String imageUrl) {
		if(imageUrl == null || imageUrl.isEmpty()) {
			System.out.println("이미지 URL이 비어 있습니다.");
			return null; // 이미지 URL이 비어 있을 경우 처리
		}
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(imageUrl))
				.GET()
				.build();
		
		try {
			HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
			if(response.statusCode() != 200) {
				throw new RuntimeException("이미지 로드 실패: " + response.statusCode());
			}
			return response.body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse recordGameHistory(String userid, String title, int answerQuiz, String playDate ) {
		dataSet.record.Record record = new dataSet.record.Record();
		
		record.setUser_id(userid); record.setTitle(title); record.setAnswer_quiz(answerQuiz); record.setPlay_date(playDate);
		String customUrl = URL + "/record/record-game-history";
		
		try {
			String recordJson = JSONManager.mapper.writeValueAsString(record);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(recordJson))
					.build();
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse updateUserTier(String id) {
		User user = new User();	
		user.setId(id);
		String customUrl = URL + "/user/user-tier-update";
		
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse updateAllUserTier() {
		String customUrl = URL + "/user/user-tier-all-update";
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.noBody())
					.build();
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse getUserRecord(String id) {
		User user = new User();
		user.setId(id);
		String customUrl = URL + "/record/get-user-records";
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse getUserRanking(String id) {
		User user = new User();
		user.setId(id);
		String customUrl = URL + "/user/get-user-ranking";
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
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApiResponse getRanking() {
		
		String customUrl = URL + "/user/get-ranking";
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(customUrl))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.noBody())
					.build();
			
			HttpResponse<String> putResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = putResponse.body();
			ApiResponse apiRes = JSONManager.mapper.readValue(responseBody, ApiResponse.class);
			return apiRes;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
