package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.FlowLayout;

import client.CtManager.Player;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJPanel;
import dataSet.user.User;
import database.DatabaseManager;

import java.awt.Font;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private InGameFrame ingameframe;
	private LoginFrame loginframe;
	private MyInfoFrame myinfoFrame;
	private Player player;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HomeFrame frame = new HomeFrame(player);
//					frame.setResizable(false);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public HomeFrame(Player player) {
		this.player = player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(228, 235, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RoundJPanel MainPanel = new RoundJPanel(5);
		MainPanel.setBackground(new Color(255, 255, 255));
		MainPanel.setBounds(80, 40, 1080, 600);
		contentPane.add(MainPanel);
		MainPanel.setLayout(null);
		
		RoundJButton btnMyInfo = new RoundJButton();
		btnMyInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(myinfoFrame == null) {
					myinfoFrame = new MyInfoFrame(player);
					myinfoFrame.setVisible(true);
					myinfoFrame.setResizable(false);
				} else {
					myinfoFrame.setVisible(true);
					myinfoFrame.setResizable(false);
				}
				setVisible(false);
			}
		});
		btnMyInfo.setBounds(700, 15, 200, 40);
		btnMyInfo.setText(player != null && player.getNickname() != null ? player.getNickname() : "(내 정보)");
		btnMyInfo.setForeground(Color.BLACK);
		btnMyInfo.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnMyInfo.setBackground(new Color(185, 215, 234));
		MainPanel.add(btnMyInfo);
		
		RoundJButton btnLogOut = new RoundJButton();
		btnLogOut.addActionListener(new ActionListener() {

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
		btnLogOut.setBounds(930, 15, 120, 40);
		btnLogOut.setText("로그 아웃");
		btnLogOut.setForeground(Color.BLACK);
		btnLogOut.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnLogOut.setBackground(new Color(176, 180, 186));
		MainPanel.add(btnLogOut);
		
		
		
		JPanel ThemaPanel = new JPanel();
		ThemaPanel.setBackground(new Color(244, 244, 244));
		ThemaPanel.setBounds(140, 90, 800, 460);
		MainPanel.add(ThemaPanel);
		ThemaPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel SPanel = new JPanel();
		SPanel.setLayout(null);
		
		RoundJPanel LOLPanel = new RoundJPanel(5);
		LOLPanel.setLayout(null);
		LOLPanel.setBackground(new Color(185, 215, 234));
		LOLPanel.setBounds(30, 25, 180, 200);
		SPanel.add(LOLPanel);
		
		JButton btnLOLQuiz = new JButton("");
		btnLOLQuiz.setIcon(new ImageIcon("C:\\자바팀플\\lol_quiz\\JavaProject\\src\\client\\Icon\\롤테마_이미지.png"));
		btnLOLQuiz.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(ingameframe == null) {
					ingameframe = new InGameFrame(player);
					ingameframe.setVisible(true);
					ingameframe.setResizable(false);
				} else {
					ingameframe.setVisible(true);
					ingameframe.setResizable(false);
				}
				setVisible(false);
			}
		});
		btnLOLQuiz.setBounds(15, 15, 150, 150);
		LOLPanel.add(btnLOLQuiz);
		
		JLabel lblLOLQuiz = new JLabel("LOL 챔피언 퀴즈");
		lblLOLQuiz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ingameframe == null) {
					ingameframe = new InGameFrame(player);
					ingameframe.setVisible(true);
					ingameframe.setResizable(false);
				} else {
					ingameframe.setVisible(true);
					ingameframe.setResizable(false);
				}
				setVisible(false);
			}
		});
		lblLOLQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblLOLQuiz.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblLOLQuiz.setBounds(15, 160, 150, 40);
		LOLPanel.add(lblLOLQuiz);
		
		SPanel.setPreferredSize(new java.awt.Dimension(780, 600));
		
		JScrollPane scrollPane = new JScrollPane(SPanel); // 스크롤할 컴포넌트를 JScrollPane 생성자에 전달
		ThemaPanel.add(scrollPane, BorderLayout.CENTER);
		
		
		RoundJPanel outLine1 = new RoundJPanel(5);
		outLine1.setBackground(new Color(100, 100, 100));
		outLine1.setBounds(85, 635, 1080, 11);
		contentPane.add(outLine1);
		outLine1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RoundJPanel outLine2 = new RoundJPanel(5);
		outLine2.setBackground(new Color(100, 100, 100));
		outLine2.setBounds(1155, 45, 11, 600);
		contentPane.add(outLine2);
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		setLocationRelativeTo(null);
	}
}
