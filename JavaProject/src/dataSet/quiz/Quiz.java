package dataSet.quiz;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz {
	private int id;
	private String title;
	private String quiz_name;
	private String answer;
	private String img_url;
	private String s3_url;
	private String mime_type;
	
	public Quiz() { }
}	
