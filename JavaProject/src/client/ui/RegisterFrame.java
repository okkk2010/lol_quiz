package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.ui.LoginFrame;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJPanel;
import client.uiTool.RoundJTextField;
import database.ApiResponse;
import database.DatabaseManager;
import database.HttpConnecter;
import lombok.Getter;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RoundJTextField tfInputID;
	private RoundJPasswordField tfInputPW;
	private RoundJPasswordField tfInputPwCheck;
	private RoundJTextField tfInputNickname;
	private LoginFrame loginframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param dbm2 
	 */
	public RegisterFrame() {
		setTitle("회원가입");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 660);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(228, 235, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RoundJPanel SignUpPanel = new RoundJPanel(5);
		SignUpPanel.setLayout(null);
		SignUpPanel.setBackground(Color.WHITE);
		SignUpPanel.setBounds(10, 10, 800, 600);
		contentPane.add(SignUpPanel);
		
		JLabel lblTitle = new JLabel("회원가입");
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("CookieRun Bold", Font.BOLD, 28));
		lblTitle.setBounds(300, 60, 200, 40);
		SignUpPanel.add(lblTitle);
		
		JLabel lblID = new JLabel("아이디");
		lblID.setHorizontalAlignment(SwingConstants.LEFT);
		lblID.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblID.setBounds(250, 160, 70, 20);
		SignUpPanel.add(lblID);
		
		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setHorizontalAlignment(SwingConstants.LEFT);
		lblPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblPW.setBounds(250, 320, 70, 20);
		SignUpPanel.add(lblPW);
		
		tfInputID = new RoundJTextField(10);
		tfInputID.setForeground(Color.DARK_GRAY);
		tfInputID.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputID.setBackground(new Color(202, 206, 213));
		tfInputID.setBounds(250, 190, 300, 40);
		SignUpPanel.add(tfInputID);
		
		tfInputPW = new RoundJPasswordField(10);
		tfInputPW.setForeground(Color.DARK_GRAY);
		tfInputPW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfInputPW.setEchoChar('*');
		tfInputPW.setBackground(new Color(202, 206, 213));
		tfInputPW.setBounds(250, 350, 300, 40);
		SignUpPanel.add(tfInputPW);
		
		RoundJButton btnSignUp = new RoundJButton();
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tfInputID.getText().trim();
				String nickName = tfInputNickname.getText().trim();
				String password = tfInputPW.getText().trim();
				
				// 제대로 입력했는지 확인
				if (id.isEmpty()) {
					JOptionPane.showMessageDialog(SignUpPanel, "아이디를 입력해주세요.", "회원가입 오류", JOptionPane.WARNING_MESSAGE);
					tfInputID.requestFocusInWindow(); // 아이디 입력 포커스
					return; // 로그인 프로세스 중단
				}
				if (nickName.isEmpty()) {
					JOptionPane.showMessageDialog(SignUpPanel, "닉네임을 입력해주세요.", "회원가입 오류", JOptionPane.WARNING_MESSAGE);
					tfInputNickname.requestFocusInWindow(); // 닉네임 입력 포커스
					return; // 로그인 프로세스 중단
				}	
				if (password.isEmpty()) {
					JOptionPane.showMessageDialog(SignUpPanel, "비밀번호를 입력해주세요.", "회원가입 오류", JOptionPane.WARNING_MESSAGE);
					tfInputPW.requestFocusInWindow(); // 비밀번호 입력 포커스
					return; // 로그인 프로세스 중단
				}
				ApiResponse apiRes = HttpConnecter.instance.signUpUser(id, nickName, password);
				if(apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "회원가입 성공!!");
					// 로그인 프레임 전환
					if(loginframe == null) {
						loginframe = new LoginFrame();			
						loginframe.setVisible(true);
					} else {
						loginframe.setVisible(true);
					}
					setVisible(false);
					
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
		
//		JLabel lblPwCheck = new JLabel("비밀번호 확인");
//		lblPwCheck.setHorizontalAlignment(SwingConstants.LEFT);
//		lblPwCheck.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
//		lblPwCheck.setBounds(250, 360, 100, 20);
//		LoginPanel.add(lblPwCheck);
//		
//		tfInputPwCheck = new RoundJPasswordField(10);
//		tfInputPwCheck.setForeground(Color.DARK_GRAY);
//		tfInputPwCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
//		tfInputPwCheck.setEchoChar('*');
//		tfInputPwCheck.setBackground(new Color(202, 206, 213));
//		tfInputPwCheck.setBounds(250, 390, 300, 40);
//		LoginPanel.add(tfInputPwCheck);
		
		JLabel lblNickname = new JLabel("닉네임");
		lblNickname.setHorizontalAlignment(SwingConstants.LEFT);
		lblNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblNickname.setBounds(250, 240, 100, 20);
		SignUpPanel.add(lblNickname);
		
		tfInputNickname = new RoundJTextField(10);
		tfInputNickname.setForeground(Color.DARK_GRAY);
		tfInputNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputNickname.setBackground(new Color(202, 206, 213));
		tfInputNickname.setBounds(250, 270, 300, 40);
		SignUpPanel.add(tfInputNickname);
		
		btnSignUp.setText("회원가입");
		btnSignUp.setForeground(Color.BLACK);
		btnSignUp.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnSignUp.setBackground(new Color(185, 215, 234));
		btnSignUp.setBounds(250, 440, 170, 40);
		SignUpPanel.add(btnSignUp);
		
		RoundJButton btnBack = new RoundJButton();
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginframe == null) {
					loginframe = new LoginFrame();			
					loginframe.setVisible(true);
				} else {
					loginframe.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnBack.setText("돌아가기");
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnBack.setBackground(new Color(176, 180, 186));
		btnBack.setBounds(450, 440, 100, 40);
		SignUpPanel.add(btnBack);
		
		RoundJPanel outLine1 = new RoundJPanel(5);
		outLine1.setBackground(new Color(100, 100, 100));
		outLine1.setBounds(15, 605, 800, 11);
		contentPane.add(outLine1);
		outLine1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RoundJPanel outLine2 = new RoundJPanel(5);
		outLine2.setBackground(new Color(100, 100, 100));
		outLine2.setBounds(805, 15, 11, 600);
		contentPane.add(outLine2);
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		setLocationRelativeTo(null);
	}
}
