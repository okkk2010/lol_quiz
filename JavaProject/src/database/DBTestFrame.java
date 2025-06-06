package database;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataSet.quiz.Quiz;
import dataSet.user.User;
import dataSet.record.Record;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import java.awt.GridLayout;
import java.awt.Color;

public class DBTestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFSignUpId;
	private JTextField tFSignUpNickName;
	private JTextField tFSignUpPassword;
	private JTextField tFSignInId;
	private JTextField tFSignInPassword;
	
	private static DatabaseManager dbm;
	private JTextField tFInput;
	private JTextField tFOutput;
	private JButton btnSaveImg;
	
	private String curImgPath;
	private JTextField tFName;
	private JTextField tFAnswer;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel quizImg;
	private JButton btnNextQuiz;
	private JTextField tFInputAnswer;
	
	private int currentQuizCnt = 0;
	private ArrayList<Quiz> quizs = new ArrayList<>();
	private JButton tFSubmit;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JTextField tFInputUserId;
	private JTextField tFOutputUserRecord;
	private JTextField tFInputRecordToUserId;
	private JButton btnSaveRecord;
	private JTextField tFInputRecordToTitle;
	private JTextField tFInputRecordToAnswerQuiz;
	private JTextField tFInputRecordToPlayDate;
	private JButton btnUpdateAllUserTier;
	private JTextField tFInputUserIdForRank;
	private JTextField tFOutputUserRank;
	private JButton getUserRank;
	private JTextField tFRanking;
	private JButton getRank;
	private JButton getUserHighScore;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton getUserHighScore_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBTestFrame frame = new DBTestFrame();
					dbm = new DatabaseManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DBTestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1042, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tFSignUpId = new JTextField();
		tFSignUpId.setBounds(37, 50, 116, 21);
		contentPane.add(tFSignUpId);
		tFSignUpId.setColumns(10);
		
		tFSignUpNickName = new JTextField();
		tFSignUpNickName.setBounds(37, 97, 116, 21);
		contentPane.add(tFSignUpNickName);
		tFSignUpNickName.setColumns(10);
		
		tFSignUpPassword = new JTextField();
		tFSignUpPassword.setBounds(37, 137, 116, 21);
		contentPane.add(tFSignUpPassword);
		tFSignUpPassword.setColumns(10);
		
		tFSignInId = new JTextField();
		tFSignInId.setBounds(264, 50, 116, 21);
		contentPane.add(tFSignInId);
		tFSignInId.setColumns(10);
		
		tFSignInPassword = new JTextField();
		tFSignInPassword.setBounds(264, 97, 116, 21);
		contentPane.add(tFSignInPassword);
		tFSignInPassword.setColumns(10);
		
		JButton btnSignUp = new JButton("sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tFSignUpId.getText();
				String nickName = tFSignUpNickName.getText();
				String password = tFSignUpPassword.getText();
				
				ApiResponse apiRes = HttpConnecter.instance.signUpUser(id, nickName, password);
				if(apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "회원가입 성공!!");
				} else {
					switch(apiRes.getError().getCode()) {
						case "DUPLICATE_ID":
							JOptionPane.showMessageDialog(contentPane, "아이디 중복");
							break;
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "회원가입 실패");
							break;
					}
				}
			}
		});
		
		btnSignUp.setBounds(37, 197, 116, 23);
		contentPane.add(btnSignUp);
		
		JButton btnSignIn = new JButton("sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tFSignInId.getText();
				String password = tFSignInPassword.getText();
				
				ApiResponse apiRes = HttpConnecter.instance.signInUser(id, password);
				if(apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "로그인 성공!");
				} else {
					switch(apiRes.getError().getCode()) {
						case "NOT_MATCH_PASSWORD":
							JOptionPane.showMessageDialog(contentPane, "비밀번호가 틀립니다.");
							break;
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "로그인 실패");
							break;
					}
				}
			}
		});
		btnSignIn.setBounds(264, 197, 116, 23);
		contentPane.add(btnSignIn);
		
		tFInput = new JTextField();
		tFInput.setBounds(480, 50, 116, 21);
		contentPane.add(tFInput);
		tFInput.setColumns(10);
		
		tFOutput = new JTextField();
		tFOutput.setBounds(480, 79, 116, 108);
		contentPane.add(tFOutput);
		tFOutput.setColumns(10);
		
		JButton btnFind = new JButton("find user");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tFInput.getText();
				
				ApiResponse apiRes = HttpConnecter.instance.userInfo(id);
				
				if(apiRes.isSuccess()) 
				{
					String content = apiRes.getContent();
					User user = JSONManager.getJsonData(content, User.class);
						
				} 
				else 
				{
					switch(apiRes.getError().getCode()) {
						case "NOT_FOUND_ID":
							JOptionPane.showMessageDialog(contentPane, "test");
							return;
					}
				}
				
//				User user = new User();
//				
//				try {
//					user = JSONManager.mapper. readValue(content, User.class);
//				} catch (JsonProcessingException e1) {
//					e1.printStackTrace();
//				}
			}
		});
		btnFind.setBounds(490, 197, 97, 23);
		contentPane.add(btnFind);
		
		JLabel imgIcon = new JLabel("");
		imgIcon.setBounds(60, 281, 259, 206);
		contentPane.add(imgIcon);
		
		JButton btnImgSelect = new JButton("select img");
		btnImgSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				File curDir = new File(".");
				fc.setCurrentDirectory(curDir);
				
				fc.showOpenDialog(fc);
				File selectedFile = fc.getSelectedFile();
				
				curImgPath = selectedFile.getPath();
				
				try {
					BufferedImage wimg = ImageIO.read(new File(selectedFile.getPath()));
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(wimg, "png", baos);
					byte[] pngBytes = baos.toByteArray();
					baos.close();
					
					ImageIcon icon = new ImageIcon(pngBytes);
					imgIcon.setIcon(icon);	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnImgSelect.setBounds(60, 510, 97, 23);
		contentPane.add(btnImgSelect);
		
		btnSaveImg = new JButton("save img");
		btnSaveImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Path imagePath = Path.of(curImgPath);
					byte[] imgData = Files.readAllBytes(imagePath);
					String mimeType = Files.probeContentType(imagePath);
					
					ApiResponse apiRes = HttpConnecter.instance.createQuiz("lol", tFName.getText(), tFAnswer.getText(), mimeType, imgData);
					if(apiRes == null) {
						JOptionPane.showMessageDialog(contentPane, "이미지 데이터가 없습니다.");
						return; // 이미지 데이터가 없을 경우 처리
					}
					if(apiRes.isSuccess()) {
						JOptionPane.showMessageDialog(contentPane, "퀴즈 생성!");
					} else {
						switch(apiRes.getError().getCode()) {
							case "NOT_FOUND_QUIZ":
								JOptionPane.showMessageDialog(contentPane, "퀴즈 생성 실패");
								break;
							case "SERVER_ERROR":
								JOptionPane.showMessageDialog(contentPane, "입력값이 잘못되었습니다.");
								break;
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveImg.setBounds(187, 510, 97, 23);
		contentPane.add(btnSaveImg);
		
		tFName = new JTextField();
		tFName.setBounds(331, 363, 116, 21);
		contentPane.add(tFName);
		tFName.setColumns(10);
		
		tFAnswer = new JTextField();
		tFAnswer.setBounds(331, 428, 116, 21);
		contentPane.add(tFAnswer);
		tFAnswer.setColumns(10);
		
		lblNewLabel = new JLabel("answer");
		lblNewLabel.setBounds(361, 403, 57, 15);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("quiz name");
		lblNewLabel_1.setBounds(344, 338, 86, 15);
		contentPane.add(lblNewLabel_1);
		
		quizImg = new JLabel("");
		quizImg.setBounds(480, 281, 259, 206);
		contentPane.add(quizImg);
		
		btnNextQuiz = new JButton("next quiz");
		btnNextQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApiResponse apiRes = HttpConnecter.instance.getQuizs("lol");
				if(apiRes == null || !apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "퀴즈 로드 실패");
					return;
				}
				
				quizs = JSONManager.getJsonDataList(apiRes.getContent(), Quiz.class);
				if(quizs == null || quizs.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "퀴즈가 없습니다.");
					return;
				}
				
				byte[] imgData = HttpConnecter.instance.loadImage(Integer.toString(quizs.get(currentQuizCnt).getId()));
				if(imgData == null) {
					JOptionPane.showMessageDialog(contentPane, "이미지 로드 실패");
					return;
				}
				
				ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
				try {
					BufferedImage img = ImageIO.read(bais);
					quizImg.setIcon(new ImageIcon(img));
					tFInputAnswer.setText(quizs.get(currentQuizCnt).getAnswer());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNextQuiz.setBounds(576, 248, 86, 23);
		contentPane.add(btnNextQuiz);
		
		tFInputAnswer = new JTextField();
		tFInputAnswer.setBounds(510, 511, 116, 21);
		contentPane.add(tFInputAnswer);
		tFInputAnswer.setColumns(10);
		
		tFSubmit = new JButton("New button");
		tFSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputAnswer = tFInputAnswer.getText();
				if(inputAnswer.equals(quizs.get(currentQuizCnt).getAnswer())) {
					JOptionPane.showMessageDialog(contentPane, "정답입니다!");
					currentQuizCnt++;
					if(currentQuizCnt >= quizs.size()) {
						JOptionPane.showMessageDialog(contentPane, "모든 퀴즈를 완료했습니다!");
						currentQuizCnt = 0; // 리셋
					} else {
						byte[] imgData = HttpConnecter.instance.loadImage(Integer.toString(quizs.get(currentQuizCnt).getId()));
						if(imgData != null) {
							ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
							try {
								BufferedImage img = ImageIO.read(bais);
								quizImg.setIcon(new ImageIcon(img));
								tFInputAnswer.setText(quizs.get(currentQuizCnt).getAnswer());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(contentPane, "이미지 로드 실패");
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "틀렸습니다. 다시 시도하세요.");
				}
			}
		});
		tFSubmit.setBounds(642, 510, 97, 23);
		contentPane.add(tFSubmit);
		
		tFInputUserId = new JTextField();
		tFInputUserId.setBounds(642, 50, 116, 21);
		contentPane.add(tFInputUserId);
		tFInputUserId.setColumns(10);
		
		tFOutputUserRecord = new JTextField();
		tFOutputUserRecord.setBounds(642, 79, 116, 108);
		contentPane.add(tFOutputUserRecord);
		tFOutputUserRecord.setColumns(10);
		
		JButton btnUserRecordSearch = new JButton("user record search");
		btnUserRecordSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = tFInputUserId.getText();
				
				ApiResponse apiRes = HttpConnecter.instance.getUserRecord(userId);
				if(apiRes.isSuccess()) {
					String content = apiRes.getContent();
					ArrayList<Record> records = JSONManager.getJsonDataList(content, Record.class);
					
					tFOutputUserRecord.setText("");
					if(records != null && !records.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						for(Record record : records) {
							sb.append("Title: ").append(record.getTitle())
							  .append(", User ID: ").append(record.getUser_id())
							  .append(", Total Quiz: ").append(record.getTotal_quiz())
							  .append(", Answer Quiz: ").append(record.getAnswer_quiz())
							  .append(", Date: ").append(record.getPlay_date())
							  .append("\n");
						}
						tFOutputUserRecord.setText(sb.toString());
					} else {
						tFOutputUserRecord.setText("해당 사용자의 기록이 없습니다.");
					}
				} else {
					switch(apiRes.getError().getCode()) {
						case "NOT_FOUND_RECORDS":
							JOptionPane.showMessageDialog(contentPane, "사용자를 찾을 수 없습니다.");
							break;
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
							break;
					}
				}
			}
		});
		btnUserRecordSearch.setBounds(652, 197, 97, 23);
		contentPane.add(btnUserRecordSearch);
		
		tFInputRecordToUserId = new JTextField();
		tFInputRecordToUserId.setColumns(10);
		tFInputRecordToUserId.setBounds(791, 50, 116, 21);
		contentPane.add(tFInputRecordToUserId);
		
		btnSaveRecord = new JButton("save record");
		btnSaveRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = tFInputRecordToUserId.getText();
				String title = tFInputRecordToTitle.getText();
				int answerQuiz = Integer.parseInt(tFInputRecordToAnswerQuiz.getText());
				String playDate = tFInputRecordToPlayDate.getText();
				
				
				ApiResponse apiRes = HttpConnecter.instance.recordGameHistory(userId, title, answerQuiz, playDate);
				
				if(apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "기록 저장 성공!");
				} else {
					switch(apiRes.getError().getCode()) {
						case "NOT_FOUND_USER":
							JOptionPane.showMessageDialog(contentPane, "사용자를 찾을 수 없습니다.");
							break;
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
							break;
					}
				}
			}
		});
		btnSaveRecord.setBounds(801, 197, 97, 23);
		contentPane.add(btnSaveRecord);
		
		tFInputRecordToTitle = new JTextField();
		tFInputRecordToTitle.setColumns(10);
		tFInputRecordToTitle.setBounds(791, 79, 116, 21);
		contentPane.add(tFInputRecordToTitle);
		
		tFInputRecordToAnswerQuiz = new JTextField();
		tFInputRecordToAnswerQuiz.setColumns(10);
		tFInputRecordToAnswerQuiz.setBounds(791, 109, 116, 21);
		contentPane.add(tFInputRecordToAnswerQuiz);
		
		tFInputRecordToPlayDate = new JTextField();
		tFInputRecordToPlayDate.setColumns(10);
		tFInputRecordToPlayDate.setBounds(791, 137, 116, 21);
		contentPane.add(tFInputRecordToPlayDate);
		
		btnUpdateAllUserTier = new JButton("save record");
		btnUpdateAllUserTier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApiResponse apiRes = HttpConnecter.instance.updateAllUserTier();
				if(apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "모든 사용자 티어 업데이트 성공!");
				} else {
					switch(apiRes.getError().getCode()) {
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
							break;
						default:
							JOptionPane.showMessageDialog(contentPane, "알 수 없는 오류 발생");
							break;
					}
				}
			}
		});
		btnUpdateAllUserTier.setBounds(917, 49, 97, 23);
		contentPane.add(btnUpdateAllUserTier);
		
		tFInputUserIdForRank = new JTextField();
		tFInputUserIdForRank.setColumns(10);
		tFInputUserIdForRank.setBounds(791, 249, 116, 21);
		contentPane.add(tFInputUserIdForRank);
		
		tFOutputUserRank = new JTextField();
		tFOutputUserRank.setColumns(10);
		tFOutputUserRank.setBounds(791, 281, 116, 21);
		contentPane.add(tFOutputUserRank);
		
		getUserRank = new JButton("user rank search");
		getUserRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = tFInputUserIdForRank.getText();
				
				ApiResponse apiRes = HttpConnecter.instance.getUserRanking(userId);
				if(apiRes.isSuccess()) {
					String content = apiRes.getContent();
					try {
						int rank = Integer.parseInt(content);
						tFOutputUserRank.setText("사용자 랭킹: " + rank);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(contentPane, "랭킹 정보가 올바르지 않습니다.");
					}
				} else {
					switch(apiRes.getError().getCode()) {
						case "NOT_FOUND_USER":
							JOptionPane.showMessageDialog(contentPane, "사용자를 찾을 수 없습니다.");
							break;
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
							break;
					}
				}
			}
		});
		getUserRank.setBounds(801, 312, 97, 23);
		contentPane.add(getUserRank);
		
		tFRanking = new JTextField();
		tFRanking.setColumns(10);
		tFRanking.setBounds(791, 367, 116, 108);
		contentPane.add(tFRanking);
		
		getRank = new JButton("print rank");
		getRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApiResponse apiRes = HttpConnecter.instance.getRanking();
				if(apiRes.isSuccess()) {
					String content = apiRes.getContent();
					ArrayList<Record> records = JSONManager.getJsonDataList(content, Record.class);
					
					tFRanking.setText("");
					if(records != null && !records.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						for(Record record : records) {
							sb.append("ID: ").append(record.getId())
							  .append(", Nickname: ").append(record.getNickname())
							  .append(", Answer Quiz: ").append(record.getAnswer_quiz())
							  .append("\n");
						}
						tFRanking.setText(sb.toString());
					} else {
						tFRanking.setText("랭킹 정보가 없습니다.");
					}
				} else {
					switch(apiRes.getError().getCode()) {
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
							break;
						default:
							JOptionPane.showMessageDialog(contentPane, "알 수 없는 오류 발생");
							break;
					}
				}
			}
		});
		getRank.setBounds(801, 510, 97, 23);
		contentPane.add(getRank);
		
		getUserHighScore = new JButton("save record");
		getUserHighScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				
				ApiResponse apiRes = HttpConnecter.instance.getHighAnswerQuiz(id);
				if(apiRes.isSuccess()) {
					String content = apiRes.getContent();
					try {
						int highScore = Integer.parseInt(content);
						textField_1.setText("사용자의 최고 점수: " + highScore);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(contentPane, "점수 정보가 올바르지 않습니다.");
					}
				} else {
					switch(apiRes.getError().getCode()) {
						case "NOT_FOUND_USER":
							JOptionPane.showMessageDialog(contentPane, "사용자를 찾을 수 없습니다.");
							break;
						case "SERVER_ERROR":
							JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
							break;
					}
				}
				
			}
		});
		getUserHighScore.setBounds(917, 197, 97, 23);
		contentPane.add(getUserHighScore);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(928, 137, 86, 21);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(928, 166, 86, 21);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(910, 245, 116, 108);
		contentPane.add(textField_2);
		
		getUserHighScore_1 = new JButton("save record");
		getUserHighScore_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] imgData = HttpConnecter.instance.loadImage("다이아");
				if(imgData != null) {
					ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
					try {
						BufferedImage img = ImageIO.read(bais);
						quizImg.setIcon(new ImageIcon(img));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "이미지 로드 실패");
				}
			}
		});
		getUserHighScore_1.setBounds(917, 362, 97, 23);
		contentPane.add(getUserHighScore_1);
	
		
		
		
//		try {
//			URI uri = new URI("https://lol-quiz.s3.us-east-2.amazonaws.com/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7+2025-05-25+235312.png");
//			ImageIcon icon = new ImageIcon(uri.toURL());
//		} catch (URISyntaxException | MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}