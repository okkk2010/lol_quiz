package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.uiTool.RoundButton;
import client.uiTool.RoundJTextField;
import database.DatabaseManager;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout; // CardLayout 임포트

public class InGameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel StartPanel; // 시작 버튼이 있는 패널
	private JPanel InGamePanel; // 게임 진행 패널
	private CardLayout cardLayout; // CardLayout 변수
	private static DatabaseManager dbm;
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

		JButton btnStart = new RoundButton("START !!");
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

		// contentPane에 패널들을 추가 (각 패널에 이름을 부여)
		contentPane.add(StartPanel, "Start");
		contentPane.add(InGamePanel, "InGame");
		
		JPanel GamePanel = new JPanel();
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
		
		RoundButton btnAnswer = new RoundButton();
		btnAnswer.setText("정 답");
		btnAnswer.setForeground(Color.BLACK);
		btnAnswer.setFont(new Font("CookieRun Regular", Font.BOLD, 18));
		btnAnswer.setBackground(new Color(185, 215, 234));
		btnAnswer.setBounds(740, 510, 100, 50);
		GamePanel.add(btnAnswer);
		
		JProgressBar TimeBar = new JProgressBar();
		TimeBar.setBackground(new Color(217, 217, 217));
		TimeBar.setBounds(240, 30, 600, 25);
		GamePanel.add(TimeBar);
		
		// 초기에는 시작 패널이 보이도록 설정
		cardLayout.show(contentPane, "Start");

		setLocationRelativeTo(null);
	}
}