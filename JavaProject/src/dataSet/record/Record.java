package dataSet.record;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Record {
	private String id;
	private String title;
	private String user_id;
	private String nickname;
	private int total_quiz;
	private int answer_quiz;
	private String play_date;
	private int rank_num;
	 
	public Record() { }
}
