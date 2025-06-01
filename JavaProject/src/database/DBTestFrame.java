package database;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataSet.user.User;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
		setBounds(100, 100, 701, 300);
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
		tFInput.setBounds(510, 50, 116, 21);
		contentPane.add(tFInput);
		tFInput.setColumns(10);
		
		tFOutput = new JTextField();
		tFOutput.setBounds(510, 79, 116, 108);
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
					tFOutput.setText(user.getId() + "\n" + user.getNickname() + "\n" + user.getTier());	
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
		btnFind.setBounds(520, 197, 97, 23);
		contentPane.add(btnFind);
		
//		try {
//			URI uri = new URI("https://lol-quiz.s3.us-east-2.amazonaws.com/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7+2025-05-25+235312.png");
//			ImageIcon icon = new ImageIcon(uri.toURL());
//		} catch (URISyntaxException | MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}