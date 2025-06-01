package dataSet.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String id;
	private String nickname;
	private String password;
	private String create_at;
	private String tier;
	
	public User() {}
	
	
}