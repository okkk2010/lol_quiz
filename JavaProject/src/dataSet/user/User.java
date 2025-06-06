package dataSet.user;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String id;
	private String nickname;
	private String password;
	private String new_password;
	private String create_at;
	private String tier;
	private String ranking;
	
	public User() {}
	
}