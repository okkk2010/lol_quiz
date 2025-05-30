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
	private JPanel startPanel; // 시작 버튼이 있는 패널
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
		startPanel = new JPanel();
		startPanel.setBackground(new Color(235, 240, 250)); // 배경색 유지
		startPanel.setLayout(null); // startPanel은 여전히 null 레이아웃 사용 가능

		JButton btnNewButton = new RoundButton("START !!");
		btnNewButton.setBackground(new Color(185, 215, 234));
		btnNewButton.setFont(new Font("CookieRun Regular", Font.BOLD, 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 버튼 클릭 시 inGamePanel을 보이도록 전환
				cardLayout.show(contentPane, "InGame"); 
			}
		});
		btnNewButton.setBounds(540, 260, 200, 100);
		startPanel.add(btnNewButton); // startPanel에 버튼 추가

		// 2. 게임 진행 화면 패널 생성 및 컴포넌트 추가
		InGamePanel = new JPanel();
		InGamePanel.setLayout(null);
		InGamePanel.setBackground(new Color(235, 240, 250));
		InGamePanel.setBounds(0, 0, 1280, 720);

		// contentPane에 패널들을 추가 (각 패널에 이름을 부여)
		contentPane.add(startPanel, "Start");
		contentPane.add(InGamePanel, "InGame");
		
		JPanel OutLinePanel = new JPanel();
		OutLinePanel.setBackground(new Color(255, 255, 255));
		OutLinePanel.setBounds(80, 40, 1080, 600);
		InGamePanel.add(OutLinePanel);
		OutLinePanel.setLayout(null);
		
		JPanel IMGPanel = new JPanel();
		IMGPanel.setForeground(Color.DARK_GRAY);
		IMGPanel.setBounds(240, 80, 600, 400);
		OutLinePanel.add(IMGPanel);
		
		RoundJTextField tf_Answer = new RoundJTextField(10);
		tf_Answer.setForeground(Color.DARK_GRAY);
		tf_Answer.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		tf_Answer.setColumns(10);
		tf_Answer.setBackground(new Color(202, 206, 213));
		tf_Answer.setBounds(240, 510, 450, 50);
		OutLinePanel.add(tf_Answer);
		
		RoundButton btnAnswer = new RoundButton();
		btnAnswer.setText("정 답");
		btnAnswer.setForeground(Color.BLACK);
		btnAnswer.setFont(new Font("CookieRun Regular", Font.BOLD, 18));
		btnAnswer.setBackground(new Color(185, 215, 234));
		btnAnswer.setBounds(740, 510, 100, 50);
		OutLinePanel.add(btnAnswer);
		
		JProgressBar TimeBar = new JProgressBar();
		TimeBar.setBackground(new Color(217, 217, 217));
		TimeBar.setBounds(240, 30, 600, 25);
		OutLinePanel.add(TimeBar);
		
		// 초기에는 시작 패널이 보이도록 설정
		cardLayout.show(contentPane, "Start");

		setLocationRelativeTo(null);
	}
}