package database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	private boolean success;
	private String content;
	private ApiError error;
	
	@Getter
	@Setter
	public class ApiError {
		private String code;
		private String message;
		
	}
}
