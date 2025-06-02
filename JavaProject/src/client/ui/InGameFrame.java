package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.uiTool.RoundJButton;
import client.uiTool.RoundJPanel;
import client.uiTool.RoundJTextField;
import database.DatabaseManager;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.FlowLayout; // CardLayout 임포트

public class InGameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel StartPanel; // 시작 화면
	private JPanel InGamePanel; // 게임 진행 화면
	private Panel ResultsPanel; //  게임 결과 화면
	private CardLayout cardLayout; // CardLayout 변수
	private static DatabaseManager dbm;
	private HomeFrame homeframe;
	private RankingFrame rankingframe;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InGameFrame frame = new InGameFrame(dbm);
					frame.setResizable(false); // 창 크기 조절 방지
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
	public InGameFrame(DatabaseManager dbManger) {
		this.dbm = dbManger;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		cardLayout = new CardLayout(); // CardLayout 인스턴스 생성
		contentPane = new JPanel(cardLayout); // contentPane에 CardLayout 설정
		contentPane.setBackground(new Color(235, 240, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane); // JFrame의 contentPane으로 설정
		
		// 1. 시작 화면 패널 생성 및 컴포넌트 추가
		StartPanel = new JPanel();
		StartPanel.setBackground(new Color(235, 240, 250)); // 배경색 유지
		StartPanel.setLayout(null); // startPanel은 여전히 null 레이아웃 사용 가능

		JButton btnStart = new RoundJButton("START !!");
		btnStart.setBackground(new Color(185, 215, 234));
		btnStart.setFont(new Font("CookieRun Regular", Font.BOLD, 30));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 버튼 클릭 시 inGamePanel을 보이도록 전환
				cardLayout.show(contentPane, "InGame"); 
			}
		});
		btnStart.setBounds(540, 260, 200, 100);
		StartPanel.add(btnStart); // startPanel에 버튼 추가

		// 2. 게임 진행 화면 패널 생성 및 컴포넌트 추가
		InGamePanel = new JPanel();
		InGamePanel.setLayout(null);
		InGamePanel.setBackground(new Color(235, 240, 250));
		InGamePanel.setBounds(0, 0, 1280, 720);

		
		RoundJPanel GamePanel = new RoundJPanel(5);
		GamePanel.setBackground(new Color(255, 255, 255));
		GamePanel.setBounds(80, 40, 1080, 600);
		InGamePanel.add(GamePanel);
		GamePanel.setLayout(null);
		
		JPanel IMGPanel = new JPanel();
		IMGPanel.setForeground(Color.DARK_GRAY);
		IMGPanel.setBounds(240, 80, 600, 400);
		GamePanel.add(IMGPanel);
		
		RoundJTextField tfAnswer = new RoundJTextField(10);
		tfAnswer.setForeground(Color.DARK_GRAY);
		tfAnswer.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		tfAnswer.setColumns(10);
		tfAnswer.setBackground(new Color(202, 206, 213));
		tfAnswer.setBounds(240, 510, 450, 50);
		GamePanel.add(tfAnswer);
		
		RoundJButton btnAnswer = new RoundJButton();
		btnAnswer.setText("정 답");
		btnAnswer.setForeground(Color.BLACK);
		btnAnswer.setFont(new Font("CookieRun Regular", Font.BOLD, 18));
		btnAnswer.setBackground(new Color(185, 215, 234));
		btnAnswer.setBounds(740, 510, 100, 50);
		GamePanel.add(btnAnswer);
		
		JProgressBar TimeBar = new JProgressBar();
		TimeBar.setForeground(Color.RED);
		TimeBar.setFont(new Font("굴림", Font.PLAIN, 16));
		TimeBar.setBackground(new Color(217, 217, 217));
		TimeBar.setBounds(240, 30, 600, 25);
		GamePanel.add(TimeBar);
		
		JButton btnNewButton = new JButton("결과 창 버튼");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "Results");
			}
		});
		btnNewButton.setBounds(896, 459, 124, 101);
		GamePanel.add(btnNewButton);
		
		ResultsPanel = new Panel();
		
		// contentPane에 패널들을 추가 (각 패널에 이름을 부여)
		contentPane.add(StartPanel, "Start");
		contentPane.add(InGamePanel, "InGame");
		contentPane.add(ResultsPanel, "Result");
		
		RoundJPanel outLine1_1 = new RoundJPanel(5);
		outLine1_1.setBackground(new Color(100, 100, 100));
		outLine1_1.setBounds(85, 635, 1080, 11);
		InGamePanel.add(outLine1_1);
		outLine1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RoundJPanel outLine2_1 = new RoundJPanel(5);
		outLine2_1.setBackground(new Color(100, 100, 100));
		outLine2_1.setBounds(1155, 45, 11, 600);
		InGamePanel.add(outLine2_1);
		outLine2_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.add(ResultsPanel, "Results");
		ResultsPanel.setLayout(null);
		
		RoundJPanel GamePanel2 = new RoundJPanel(5);
		GamePanel2.setLayout(null);
		GamePanel2.setBackground(Color.WHITE);
		GamePanel2.setBounds(80, 40, 1080, 600);
		ResultsPanel.add(GamePanel2);
		
		RoundJPanel ResultPanel = new RoundJPanel(5);
		ResultPanel.setBackground(new Color(185, 215, 234));
		ResultPanel.setBounds(340, 50, 400, 400);
		GamePanel2.add(ResultPanel);
		ResultPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("결 과");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("CookieRun Regular", Font.PLAIN, 30));
		lblNewLabel.setBounds(160, 20, 80, 40);
		ResultPanel.add(lblNewLabel);
		
		RoundJPanel panel = new RoundJPanel(5);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(50, 70, 300, 300);
		ResultPanel.add(panel);
		
		RoundJPanel outLine1 = new RoundJPanel(5);
		outLine1.setBackground(UIManager.getColor("windowBorder"));
		outLine1.setBounds(-530, 534, 1080, 11);
		ResultPanel.add(outLine1);
		outLine1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RoundJPanel outLine2 = new RoundJPanel(5);
		outLine2.setBackground(UIManager.getColor("windowBorder"));
		outLine2.setBounds(540, -56, 11, 600);
		ResultPanel.add(outLine2);
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RoundJButton btnRanking = new RoundJButton();
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rankingframe == null) {
					rankingframe = new RankingFrame(dbm);
					rankingframe.setVisible(true);
				} else {
					rankingframe.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnRanking.setText("랭킹보기");
		btnRanking.setForeground(Color.BLACK);
		btnRanking.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnRanking.setBackground(new Color(185, 215, 234));
		btnRanking.setBounds(340, 480, 150, 50);
		GamePanel2.add(btnRanking);
		
		RoundJButton btnHome = new RoundJButton();
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(homeframe == null) {
					homeframe = new HomeFrame(dbm);
					homeframe.setVisible(true);
				} else {
					homeframe.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnHome.setText("홈");
		btnHome.setForeground(Color.BLACK);
		btnHome.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnHome.setBackground(new Color(176, 180, 186));
		btnHome.setBounds(590, 480, 150, 50);
		GamePanel2.add(btnHome);
		
		RoundJPanel outLine1_2 = new RoundJPanel(5);
		outLine1_2.setBackground(new Color(100, 100, 100));
		outLine1_2.setBounds(85, 635, 1080, 11);
		ResultsPanel.add(outLine1_2);
		outLine1_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RoundJPanel outLine2_2 = new RoundJPanel(5);
		outLine2_2.setBackground(new Color(100, 100, 100));
		outLine2_2.setBounds(1155, 45, 11, 600);
		ResultsPanel.add(outLine2_2);
		outLine2_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// 초기에는 시작 패널이 보이도록 설정
		cardLayout.show(contentPane, "Start");

		setLocationRelativeTo(null);
	}
}