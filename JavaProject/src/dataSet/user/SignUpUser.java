package dataSet.user;

import lombok.Getter;

@Getter
public class SignUpUser {
	private String id;
	private String nickname;
	private String password;
	
	public SignUpUser(String id, String nickname, String password ) {
		this.id = id;
		this.nickname = nickname;
		this.password = password;
	}
	
	
}
