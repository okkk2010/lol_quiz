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
		
		RoundButton btnMyInfo = new RoundButton();
		btnMyInfo.setBounds(700, 15, 200, 40);
		btnMyInfo.setText("닉네임(내 정보)");
		btnMyInfo.setForeground(Color.BLACK);
		btnMyInfo.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnMyInfo.setBackground(new Color(185, 215, 234));
		MainPanel.add(btnMyInfo);
		
		RoundButton btnLogOut = new RoundButton();
		btnLogOut.setBounds(932, 15, 120, 40);
		btnLogOut.setText("로그 아웃");
		btnLogOut.setForeground(Color.BLACK);
		btnLogOut.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnLogOut.setBackground(new Color(176, 180, 186));
		MainPanel.add(btnLogOut);
		
		JPanel ThemaPanel = new JPanel();
		ThemaPanel.setBackground(new Color(244, 244, 244));
		ThemaPanel.setBounds(140, 90, 800, 460);
		MainPanel.add(ThemaPanel);
		ThemaPanel.setLayout(null);
		
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
