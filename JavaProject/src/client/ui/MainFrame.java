package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import client.uiTool.RoundButton;
import java.awt.Font;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 240, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel MainPanel = new JPanel();
		MainPanel.setBackground(new Color(255, 255, 255));
		MainPanel.setBounds(80, 40, 1080, 600);
		contentPane.add(MainPanel);
		MainPanel.setLayout(null);
		
		RoundButton btnLogin = new RoundButton();
		btnLogin.setBounds(700, 15, 200, 40);
		btnLogin.setText("닉네임(내 정보)");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnLogin.setBackground(new Color(185, 215, 234));
		MainPanel.add(btnLogin);
		
		RoundButton btnSignUp = new RoundButton();
		btnSignUp.setBounds(932, 15, 120, 40);
		btnSignUp.setText("로그 아웃");
		btnSignUp.setForeground(Color.BLACK);
		btnSignUp.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnSignUp.setBackground(new Color(176, 180, 186));
		MainPanel.add(btnSignUp);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(244, 244, 244));
		panel_1.setBounds(140, 90, 800, 460);
		MainPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel outLine1 = new JPanel();
		outLine1.setBackground(new Color(100, 100, 100));
		outLine1.setBounds(85, 635, 1080, 11);
		contentPane.add(outLine1);
		outLine1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel outLine2 = new JPanel();
		outLine2.setBackground(new Color(100, 100, 100));
		outLine2.setBounds(1155, 45, 11, 600);
		contentPane.add(outLine2);
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		setLocationRelativeTo(null);
	}
}
