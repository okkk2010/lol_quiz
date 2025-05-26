package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;



public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfInputID;
	private JTextField tfInputPW;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setTitle("롤 퀴즈");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 240, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel LoginPanel = new JPanel();
		LoginPanel.setBackground(new Color(255, 255, 255));
		LoginPanel.setBounds(140, 40, 1000, 600);
		contentPane.add(LoginPanel);
		LoginPanel.setLayout(null);
		
		// 타이틀
		JLabel lblTitle = new JLabel("롤 챔피언 퀴즈");
		lblTitle.setBounds(400, 100, 200, 40);
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("CookieRun Regular", Font.BOLD, 28));
		LoginPanel.add(lblTitle);
		
		
		JLabel lblID = new JLabel("아이디");
		lblID.setHorizontalAlignment(SwingConstants.LEFT);
		lblID.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblID.setBounds(350, 200, 70, 20);
		LoginPanel.add(lblID);
		
		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setHorizontalAlignment(SwingConstants.LEFT);
		lblPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblPW.setBounds(350, 280, 70, 20);
		LoginPanel.add(lblPW);
		
		// 아이디 입력
		tfInputID = new JTextField();
		tfInputID.setBackground(new Color(202, 206, 213));
		tfInputID.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputID.setBounds(350, 230, 300, 40);
		LoginPanel.add(tfInputID);
		tfInputID.setColumns(10);
		
		// 비밀번호 입력
		tfInputPW = new JTextField();
		tfInputPW.setBackground(new Color(202, 206, 213));
		tfInputPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputPW.setColumns(10);
		tfInputPW.setBounds(350, 310, 300, 40);
		LoginPanel.add(tfInputPW);
		
		// 로그인 버튼
		JButton btnLogin = new JButton("로그인");
		btnLogin.setBackground(new Color(185, 215, 234));
		btnLogin.setBounds(350, 380, 100, 40);
		LoginPanel.add(btnLogin);
		
		// 회원가입 버튼
		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.setBackground(new Color(156, 163, 175));
		btnSignUp.setBounds(550, 380, 100, 40);
		LoginPanel.add(btnSignUp);
		
		JPanel outLine1 = new JPanel();
		outLine1.setBackground(new Color(214, 230, 242));
		outLine1.setBounds(130, 30, 1020, 10);
		contentPane.add(outLine1);
		outLine1.setLayout(null);
		
		JPanel outLine2 = new JPanel();
		outLine2.setBackground(new Color(214, 230, 242));
		outLine2.setBounds(130, 640, 1020, 10);
		contentPane.add(outLine2);
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel outLine3 = new JPanel();
		outLine3.setBackground(new Color(214, 230, 242));
		outLine3.setBounds(130, 40, 10, 600);
		contentPane.add(outLine3);
		outLine3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel outLine4 = new JPanel();
		outLine4.setBackground(new Color(214, 230, 242));
		outLine4.setBounds(1140, 40, 10, 600);
		contentPane.add(outLine4);
		outLine4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setLocationRelativeTo(null);
		
	}
}
