package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.CtManager.*;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJPanel;
import client.uiTool.RoundJTextField;
import dataSet.quiz.Quiz;
import database.ApiResponse;
import database.DatabaseManager;
import database.HttpConnecter;
import database.JSONManager;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;

import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent; // CardLayout 임포트
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTable;
import java.awt.GridLayout;

public class InGameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel StartPanel; // 시작 화면
	private JPanel InGamePanel; // 게임 진행 화면
	private Panel ResultsbtnPanel; // 게임 결과 확인 버튼 화면
	private Panel ResultsPanel; //  게임 결과 화면
	private CardLayout cardLayout; // CardLayout 변수
	private HomeFrame homeframe;
	private RankingFrame rankingframe;
	private Player player;
	private JProgressBar progressBar;
	private RoundJTextField tfAnswer;
	private int currentQuizCnt = 0;
	private ArrayList<Quiz> quizs = new ArrayList<>();
	private JLabel lblQuizImg;
	private ArrayList<JPanel> overlayPanels;
	private JPanel IMGPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InGameFrame frame = new InGameFrame();
//					frame.setResizable(false); // 창 크기 조절 방지
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
	public InGameFrame(Player player) {
		this.player = player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		cardLayout = new CardLayout(); // CardLayout 인스턴스 생성
		contentPane = new JPanel(cardLayout); // contentPane에 CardLayout 설정
		contentPane.setBackground(new Color(235, 240, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// 1. 시작 화면 패널 생성 및 컴포넌트 추가
		StartPanel = new JPanel();
		StartPanel.setBackground(new Color(228, 235, 250));
		StartPanel.setLayout(null);
		
		RoundJButton btnStart = new RoundJButton("START !!");
		btnStart.setBackground(new Color(185, 215, 234));
		btnStart.setFont(new Font("CookieRun Regular", Font.BOLD, 30));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 버튼 클릭 시 inGamePanel을 보이도록 전환
				cardLayout.show(contentPane, "InGame");
				tfAnswer.requestFocusInWindow();
				ApiResponse apiRes = HttpConnecter.instance.getQuizs("lol");
				if(apiRes == null || !apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(contentPane, "퀴즈 로드 실패");
					return;
				}
				
				quizs = JSONManager.getJsonDataList(apiRes.getContent(), Quiz.class);
				if(quizs == null || quizs.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "퀴즈가 없습니다.");
					return;
				}
				
				byte[] imgData = HttpConnecter.instance.loadImage(quizs.get(currentQuizCnt).getImg_url());
				if(imgData == null) {
					JOptionPane.showMessageDialog(contentPane, "이미지 로드 실패");
					return;
				}
				
				ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
				
				try {
					BufferedImage img = ImageIO.read(bais);
					lblQuizImg.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
					hideRandomPanels(2); // 시작부터 2개만 랜덤하게.
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				new Thread(() -> {
					int i;
				    for (i = 119; i >= 0; i--) {
				        try {
				            Thread.sleep(1000);
				        } catch (InterruptedException ex) {
				            Thread.currentThread().interrupt(); // 스레드 인터럽트 상태 복원
				        }
				        final int currentValue = i; // 람다 표현식에서 사용하려면 final 또는 effectively final이어야 함
				        SwingUtilities.invokeLater(() -> {
				            progressBar.setValue(currentValue);
				        });
				        if (i == 0) {
				        	SwingUtilities.invokeLater(() -> {
				        		tfAnswer.setText("");
				        		cardLayout.show(contentPane, "btnResult");
				        	});
				        }
				    }
				}).start();
			}
				
		});
		btnStart.setBounds(540, 260, 200, 100);
		StartPanel.add(btnStart); // startPanel에 버튼 추가
		
		RoundJButton btnBack = new RoundJButton("돌아가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (homeframe == null) {
					homeframe = new HomeFrame(player);
					homeframe.setVisible(true);
				} else {
					homeframe.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnBack.setBackground(new Color(176, 180, 186));
		btnBack.setFont(new Font("CookieRun Regular", Font.BOLD, 25));
		btnBack.setBounds(1080, 600, 150, 50);
		StartPanel.add(btnBack);

		// 2. 게임 진행 화면 패널 생성 및 컴포넌트 추가
		InGamePanel = new JPanel();
		InGamePanel.setLayout(null);
		InGamePanel.setBackground(new Color(228, 235, 250));
		InGamePanel.setBounds(0, 0, 1280, 720);

		
		RoundJPanel GamePanel = new RoundJPanel(5);
		GamePanel.setBackground(new Color(255, 255, 255));
		GamePanel.setBounds(80, 40, 1080, 600);
		InGamePanel.add(GamePanel);
		GamePanel.setLayout(null);
		
		IMGPanel = new JPanel();
		IMGPanel.setBackground(new Color(228, 235, 250));
		IMGPanel.setForeground(Color.DARK_GRAY);
		IMGPanel.setBounds(240, 80, 600, 400);
		GamePanel.add(IMGPanel);
		IMGPanel.setLayout(null);
		
		
		// 그리드 패널 추가
        JPanel gridPanel = new JPanel();
        gridPanel.setBounds(100, 0, 400, 400);
        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setOpaque(false); // gridPanel 자체는 투명하게 만들어야 아래 이미지가 보임

        overlayPanels = new ArrayList<>(); // 리스트 초기화
        Random random = new Random(); // 랜덤 객체 생성

        // 9개의 패널 생성 및 그리드에 추가
        for (int i = 0; i < 9; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE); // 가리는 색
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 각 패널의 경계선
            gridPanel.add(panel); // 그리드 패널에 추가
            overlayPanels.add(panel); // 리스트에 추가하여 나중에 접근 가능하게 함
        }
        IMGPanel.add(gridPanel); 
		
		lblQuizImg = new JLabel("");
		lblQuizImg.setBackground(new Color(255, 255, 255));
		lblQuizImg.setBounds(100, 0, 400, 400);
		IMGPanel.add(lblQuizImg);
		
		tfAnswer = new RoundJTextField(10);
		tfAnswer.setForeground(Color.DARK_GRAY);
		tfAnswer.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		tfAnswer.setColumns(10);
		tfAnswer.setBackground(new Color(202, 206, 213));
		tfAnswer.setBounds(240, 510, 450, 50);
		GamePanel.add(tfAnswer);
		
		RoundJButton btnAnswer = new RoundJButton();
		btnAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String userAnswer = tfAnswer.getText().trim();
				String dbAnswerString = quizs.get(currentQuizCnt).getAnswer();
				
				String[] possibleAnswers = dbAnswerString.split(",");
				
				boolean isCorrect = false;
				
				// 하나라도 맞으면 break;
		        for (String dbAnswer : possibleAnswers) {
		            if (userAnswer.equalsIgnoreCase(dbAnswer.trim())) {
		                isCorrect = true;
		                break;
		            }
		        }
		        
				
		        if (isCorrect) {
		            // 정답 처리 로직
		            currentQuizCnt++;
		            // 
		            if (currentQuizCnt >= quizs.size()) {
		                JOptionPane.showMessageDialog(contentPane, "모든 퀴즈를 완료했습니다!");
		                currentQuizCnt = 0; // 퀴즈 리셋
		                // 결과화면 출력
		                cardLayout.show(contentPane, "btnResult");
		            } else {
		                // 다음 퀴즈 이미지 로드 및 정답 필드 초기화
		                byte[] imgData = HttpConnecter.instance.loadImage(quizs.get(currentQuizCnt).getImg_url());
		                if (imgData != null) {
		                    ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
		                    try {
		                        BufferedImage img = ImageIO.read(bais);
		                        lblQuizImg.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
		                        tfAnswer.setText(""); // 다음 퀴즈로 넘어갈 때 입력 필드 비우기
		                        hideRandomPanels(2); // 2개만 랜덤하게 보여줌
		                        tfAnswer.requestFocusInWindow(); // 입력 필드에 포커스 주기
		                    } catch (IOException e1) {
		                        e1.printStackTrace();
		                        JOptionPane.showMessageDialog(contentPane, "이미지 로드 실패", "오류", JOptionPane.ERROR_MESSAGE);
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(contentPane, "이미지 로드 실패", "오류", JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        } else {
		            // 오답 처리 로직
		            tfAnswer.setText(""); // 틀렸으니 입력 필드 비우기
		            tfAnswer.requestFocusInWindow(); // 입력 필드에 포커스 주기
		        }
			}
		});
		btnAnswer.setText("확 인");
		btnAnswer.setForeground(Color.BLACK);
		btnAnswer.setFont(new Font("CookieRun Regular", Font.BOLD, 18));
		btnAnswer.setBackground(new Color(185, 215, 234));
		btnAnswer.setBounds(740, 510, 100, 50);
		GamePanel.add(btnAnswer);
		
		this.getRootPane().setDefaultButton(btnAnswer);
		
		progressBar = new JProgressBar();
		progressBar.setMaximum(119);
		progressBar.setForeground(new Color(248, 167, 160));
		progressBar.setBounds(240, 30, 600, 25);
		GamePanel.add(progressBar);
		
		ResultsbtnPanel = new Panel();
		
		ResultsPanel = new Panel();
		ResultsPanel.setBackground(new Color(228, 235, 250));
		
		// contentPane에 패널들을 추가 (각 패널에 이름을 부여)
		contentPane.add(StartPanel, "Start");
		contentPane.add(InGamePanel, "InGame");
		contentPane.add(ResultsbtnPanel, "btnResult");
		contentPane.add(ResultsPanel, "Result");
		ResultsbtnPanel.setLayout(null);
		
		RoundJButton btnResultCheck = new RoundJButton();
		btnResultCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "Results");
			}
		});
		btnResultCheck.setText("결과 확인");
		btnResultCheck.setFont(new Font("CookieRun Regular", Font.BOLD, 30));
		btnResultCheck.setBackground(new Color(185, 215, 234));
		btnResultCheck.setBounds(540, 260, 200, 100);
		ResultsbtnPanel.add(btnResultCheck);
		
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
					rankingframe = new RankingFrame(player);
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
					homeframe = new HomeFrame(player);
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
	 public void hideRandomPanels(int numberOfPanelsToHide) {
	        // 이미 생성된 overlayPanels 리스트를 사용
	        // 먼저 모든 패널을 보이게 (또는 특정 색으로) 설정하여 초기화
	        for (JPanel panel : overlayPanels) {
	            panel.setVisible(true); // 기본적으로는 모든 패널이 이미지를 가리도록 설정
	        }

	        // 이제 랜덤으로 가릴 패널을 선택
	        Random random = new Random();
	        ArrayList<Integer> hiddenPanelIndices = new ArrayList<>(); // 이미 선택된 인덱스 저장

	        while (hiddenPanelIndices.size() < numberOfPanelsToHide && hiddenPanelIndices.size() < overlayPanels.size()) {
	            int randomIndex = random.nextInt(overlayPanels.size()); // 0부터 8까지의 랜덤 인덱스
	            if (!hiddenPanelIndices.contains(randomIndex)) { // 중복 선택 방지
	                overlayPanels.get(randomIndex).setVisible(false); // 해당 패널을 숨김 (투명하게)
	                hiddenPanelIndices.add(randomIndex);
	            }
	        }
	        IMGPanel.revalidate(); // 레이아웃 재계산
	        IMGPanel.repaint(); // 다시 그리기
	    }
}