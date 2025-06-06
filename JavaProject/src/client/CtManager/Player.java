package client.CtManager;

import dataSet.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player {
	private String id;
	private String Nickname;
	private String password;
	private String tier;
	
	
	
	
	public Player(User user) {
		this.id = user.getId();
		this.Nickname = user.getNickname();
		this.tier = user.getTier();
	}
	
	
}

