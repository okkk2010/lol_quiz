package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.ui.LoginFrame;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJPanel;
import client.uiTool.RoundJTextField;
import database.DatabaseManager;
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
	private static DatabaseManager dbm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame(dbm);
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
	public RegisterFrame(DatabaseManager dbManger) {
		this.dbm = dbManger;
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
		lblID.setBounds(250, 120, 70, 20);
		SignUpPanel.add(lblID);
		
		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setHorizontalAlignment(SwingConstants.LEFT);
		lblPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblPW.setBounds(250, 280, 70, 20);
		SignUpPanel.add(lblPW);
		
		tfInputID = new RoundJTextField(10);
		tfInputID.setForeground(Color.DARK_GRAY);
		tfInputID.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputID.setBackground(new Color(202, 206, 213));
		tfInputID.setBounds(250, 150, 300, 40);
		SignUpPanel.add(tfInputID);
		
		tfInputPW = new RoundJPasswordField(10);
		tfInputPW.setForeground(Color.DARK_GRAY);
		tfInputPW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfInputPW.setEchoChar('*');
		tfInputPW.setBackground(new Color(202, 206, 213));
		tfInputPW.setBounds(250, 310, 300, 40);
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
				DatabaseManager.SignUpState suState =  dbm.SignUp(id, nickName, password);
				switch(suState) {
					case DatabaseManager.SignUpState.SUCCESS:
						JOptionPane.showMessageDialog(SignUpPanel, "회원가입에 성공했습니다!");
						// 로그인 프레임 전환
						LoginFrame frame = new LoginFrame();
						frame.setResizable(false); // 화면 고정
						frame.setVisible(true);
						// 회원가입 화면 끔
						setVisible(false); 
						break;
					case DatabaseManager.SignUpState.ID_DUPLICATION:
						JOptionPane.showMessageDialog(SignUpPanel, "아이디가 중복 되었습니다.");
						break;
					case DatabaseManager.SignUpState.UNKOWN_ERROR:
						JOptionPane.showMessageDialog(SignUpPanel, "회원가입에 실패했습니다.");
						break;
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
		lblNickname.setBounds(250, 200, 100, 20);
		SignUpPanel.add(lblNickname);
		
		tfInputNickname = new RoundJTextField(10);
		tfInputNickname.setForeground(Color.DARK_GRAY);
		tfInputNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputNickname.setBackground(new Color(202, 206, 213));
		tfInputNickname.setBounds(250, 230, 300, 40);
		SignUpPanel.add(tfInputNickname);
		
		btnSignUp.setText("회원가입");
		btnSignUp.setForeground(Color.BLACK);
		btnSignUp.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnSignUp.setBackground(new Color(185, 215, 234));
		btnSignUp.setBounds(250, 470, 300, 40);
		SignUpPanel.add(btnSignUp);
		
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
