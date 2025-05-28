package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.uiTool.RoundButton;
import client.uiTool.RoundJTextField;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

public class RegisterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
	 */
	public RegisterFrame() {
		setTitle("회원가입");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 660);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 240, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel LoginPanel = new JPanel();
		LoginPanel.setLayout(null);
		LoginPanel.setBackground(Color.WHITE);
		LoginPanel.setBounds(10, 10, 800, 600);
		contentPane.add(LoginPanel);
		
		JLabel lblTitle = new JLabel("회원가입");
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("CookieRun Bold", Font.BOLD, 28));
		lblTitle.setBounds(300, 60, 200, 40);
		LoginPanel.add(lblTitle);
		
		JLabel lblID = new JLabel("아이디");
		lblID.setHorizontalAlignment(SwingConstants.LEFT);
		lblID.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblID.setBounds(250, 120, 70, 20);
		LoginPanel.add(lblID);
		
		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setHorizontalAlignment(SwingConstants.LEFT);
		lblPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblPW.setBounds(250, 200, 70, 20);
		LoginPanel.add(lblPW);
		
		RoundJTextField tfInputID = new RoundJTextField(10);
		tfInputID.setForeground(Color.DARK_GRAY);
		tfInputID.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputID.setBackground(new Color(202, 206, 213));
		tfInputID.setBounds(250, 150, 300, 40);
		LoginPanel.add(tfInputID);
		
		client.ui.RoundJPasswordField tfInputPW = new client.ui.RoundJPasswordField(10);
		tfInputPW.setForeground(Color.DARK_GRAY);
		tfInputPW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfInputPW.setEchoChar('*');
		tfInputPW.setBackground(new Color(202, 206, 213));
		tfInputPW.setBounds(250, 230, 300, 40);
		LoginPanel.add(tfInputPW);
		
		RoundButton btnSignUp = new RoundButton();
		btnSignUp.setText("회원가입");
		btnSignUp.setForeground(Color.BLACK);
		btnSignUp.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnSignUp.setBackground(new Color(185, 215, 234));
		btnSignUp.setBounds(250, 470, 300, 40);
		LoginPanel.add(btnSignUp);
		
		JLabel lblPwCheck = new JLabel("비밀번호 확인");
		lblPwCheck.setHorizontalAlignment(SwingConstants.LEFT);
		lblPwCheck.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblPwCheck.setBounds(250, 280, 100, 20);
		LoginPanel.add(lblPwCheck);
		
		client.ui.RoundJPasswordField tfInputPwCheck = new client.ui.RoundJPasswordField(10);
		tfInputPwCheck.setForeground(Color.DARK_GRAY);
		tfInputPwCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfInputPwCheck.setEchoChar('*');
		tfInputPwCheck.setBackground(new Color(202, 206, 213));
		tfInputPwCheck.setBounds(250, 310, 300, 40);
		LoginPanel.add(tfInputPwCheck);
		
		JLabel lblNickname = new JLabel("닉네임");
		lblNickname.setHorizontalAlignment(SwingConstants.LEFT);
		lblNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblNickname.setBounds(250, 360, 100, 20);
		LoginPanel.add(lblNickname);
		
		RoundJTextField tfInputNickname = new RoundJTextField(10);
		tfInputNickname.setForeground(Color.DARK_GRAY);
		tfInputNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputNickname.setBackground(new Color(202, 206, 213));
		tfInputNickname.setBounds(250, 390, 300, 40);
		LoginPanel.add(tfInputNickname);
		
		JPanel outLine1 = new JPanel();
		outLine1.setBackground(new Color(100, 100, 100));
		outLine1.setBounds(15, 605, 800, 11);
		contentPane.add(outLine1);
		outLine1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel outLine2 = new JPanel();
		outLine2.setBackground(new Color(100, 100, 100));
		outLine2.setBounds(805, 15, 11, 600);
		contentPane.add(outLine2);
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		setLocationRelativeTo(null);
	}
}
