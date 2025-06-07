package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import client.uiTool.RoundJPanel;
import client.uiTool.RoundJPasswordField;
import client.uiTool.RoundJTextField;
import dataSet.user.User;
import dataSet.*;
import dataSet.Tier.Tier;
import dataSet.record.Record;
import database.ApiResponse;
import database.DatabaseManager; // Unused, consider removing if not needed
import database.HttpConnecter;
import database.JSONManager;
import client.CtManager.Player;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJLabel;

import java.awt.Font;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Component; // Unused, consider removing if not needed
import java.awt.Dimension; // Unused, consider removing if not needed
import javax.swing.UIManager;
import java.awt.FlowLayout; // Unused, consider removing if not needed
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class MyInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel CardPanel; // CardLayout을 적용할 패널
	private CardLayout cl_cardPanel; // CardLayout 변수 추가
	private LoginFrame loginframe;
	private HomeFrame homeframe;
	private RankingFrame rankingframe;
	private Player player;
	private JTextField tfCheckNickName;
	private JTextField tfCheckId;
	private JPanel myInfoPanel;
	private JPanel changeNickNamepanel;
	private JPanel chagePWPanel;
	private RoundJPasswordField tfCheckPW;
	private RoundJPasswordField tfChangePW;
	private JPanel myRecordsPanel;
	private DefaultTableModel tableModel;
	private JTable statsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//
//					MyInfoFrame frame = new MyInfoFrame();
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
	public MyInfoFrame(Player player) {
		this.player = player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(228, 235, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		RoundJPanel MainPanel = new RoundJPanel(5);
		MainPanel.setLayout(null);
		MainPanel.setBackground(new Color(255, 255, 255));
		MainPanel.setBounds(80, 40, 1080, 600);
		contentPane.add(MainPanel);

		RoundJPanel RecordPanel = new RoundJPanel(5);
		RecordPanel.setBackground(new Color(255, 255, 255));
		RecordPanel.setBounds(190, 90, 860, 480);
		MainPanel.add(RecordPanel);
		RecordPanel.setLayout(null);

		// CardPane에 CardLayout을 적용하여 여러 패널을 전환할 수 있도록 설정
		CardPanel = new JPanel();
		CardPanel.setBounds(10, 10, 840, 460);
		RecordPanel.add(CardPanel);
		cl_cardPanel = new CardLayout(0, 0);
		CardPanel.setLayout(cl_cardPanel);

		// 1. 전적 패널 (JScrollPane 포함)
		myRecordsPanel = new JPanel();
		myRecordsPanel.setBackground(new Color(255, 255, 255));
		myRecordsPanel.setLayout(new BorderLayout());
		myRecordsPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); // 패널에 여백 추가

		// 테이블 데이터 및 컬럼 정의
		String[] columnNames = {"날짜", "퀴즈 종류", "2분 동안 맞춘 문제", "티어"};
		
		// 디폴트 테이블 모델 생성, 모든 셀 수정 불가능하게 설정
		tableModel = new DefaultTableModel(new Object[][]{}, columnNames) {
		    @Override
		    
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JTable recordsTable = new JTable(tableModel);
		recordsTable.setFillsViewportHeight(true); 
		recordsTable.setFont(new Font("CookieRun Regular", Font.PLAIN, 14));
		recordsTable.setRowHeight(25);
		recordsTable.getTableHeader().setFont(new Font("CookieRun Regular", Font.BOLD, 14));

		// JTable 꾸미기 (배경색, 그리드 색상, 선택 색상, 폰트 등)
		recordsTable.setGridColor(new Color(0, 0, 0)); // 그리드 색상 일치
		recordsTable.setBackground(new Color(255, 255, 255)); // 배경 흰색
		recordsTable.setForeground(Color.black); // 텍스트 색상
		recordsTable.setSelectionBackground(new Color(185, 215, 234)); // 선택된 행의 배경색
		recordsTable.setSelectionForeground(Color.BLACK); // 선택된 행의 글자색

		// 테이블 헤더 꾸미기
		recordsTable.getTableHeader().setBackground(new Color(185, 215, 234)); 
		recordsTable.getTableHeader().setForeground(Color.BLACK); 
		recordsTable.getTableHeader().setReorderingAllowed(false); // 열 순서 변경 막기
		recordsTable.getTableHeader().setResizingAllowed(false); // 열 크기 조절 막기
		recordsTable.getTableHeader().setOpaque(false); // 헤더 배경 투명하게 설정

		// JTable 테두리 제거
		recordsTable.setBorder(null);

		// 테이블 가운데 정렬
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬 적용
		for (int i = 0; i < recordsTable.getColumnCount(); i++) {
			recordsTable.getColumnModel().getColumn(i).setCellRenderer(CenterRenderer);
		}

		JScrollPane recordsScrollPane = new JScrollPane(recordsTable);

		myRecordsPanel.add(recordsScrollPane, BorderLayout.CENTER);

		CardPanel.add(myRecordsPanel, "records");


		// 2. 내정보 패널
		myInfoPanel = new JPanel(); // myInfoPanel 초기화
		myInfoPanel.setBackground(new Color(255, 255, 255));
		myInfoPanel.setLayout(null);

		// 닉네임 변경 버튼
		RoundJButton btnChangeNickname = new RoundJButton("닉네임 변경");
		btnChangeNickname.setBackground(new Color(185, 215, 234));
		btnChangeNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		btnChangeNickname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 닉네임 변경 패널로 전환
				cl_cardPanel.show(CardPanel, "changeNickName");
			}
		});
		btnChangeNickname.setBounds(40, 115, 180, 80);
		myInfoPanel.add(btnChangeNickname); 

		CardPanel.add(myInfoPanel, "myInfo");
		
		// 비밀번호 변경 버튼
		RoundJButton btnChangePW = new RoundJButton("비밀번호 변경");
		btnChangePW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 비밀번호 변경 패널로 전환
				cl_cardPanel.show(CardPanel, "changePW");
			}
		});
		btnChangePW.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		btnChangePW.setBackground(new Color(185, 215, 234));
		btnChangePW.setBounds(40, 250, 180, 80);
		myInfoPanel.add(btnChangePW);
		
		// 퀴즈 종류 선택 (퀴즈 종류 추가 시 확장 기능)
		JComboBox cbBoxQuiz = new JComboBox();
		cbBoxQuiz.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		cbBoxQuiz.setModel(new DefaultComboBoxModel(new String[] {"lol 챔피언 퀴즈"}));
		cbBoxQuiz.setBounds(300, 115, 160, 40);
		myInfoPanel.add(cbBoxQuiz);
		
		// 통계 테이블
		statsTable = new JTable();
		statsTable.setEnabled(false);
		statsTable.setBounds(300, 200, 500, 60);
		statsTable.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		statsTable.setRowHeight(30);
		statsTable.getTableHeader().setFont(new Font("CookieRun Regular", Font.BOLD, 16));
		statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 열 너비 자동 조정 비활성화
		statsTable.setModel(new DefaultTableModel( // 테이블 모델 생성
			
			new Object[][] { 
				{"퀴즈이름", "판수", "평균 맞춘 문제", "평균 티어"}, // 첫 번째 행 헤더 (스크롤에 안붙이면 헤더가 안보이는 경우가 발생함)
				{"lol 챔피언 퀴즈", 0, 0.0, ""}, // 초기 데이터 설정
			}, 
			new String[] {"", "", "", ""} // 첫 번째 행을 헤더로 설정해서 빈 문자열로 설정
			
		));
		statsTable.getTableHeader().setReorderingAllowed(false); // 열 순서 변경 막기
		statsTable.getTableHeader().setResizingAllowed(false); // 열 크기 조절 막기
		statsTable.getTableHeader().setOpaque(false); // 헤더 배경 투명하게 설정
		
		
		myInfoPanel.add(statsTable);

		// 3. 닉네임 변경 패널
		changeNickNamepanel = new JPanel();
		changeNickNamepanel.setBackground(new Color(255, 255, 255));
		changeNickNamepanel.setLayout(null);

		JLabel lblCheckId = new JLabel("아이디");
		lblCheckId.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblCheckId.setHorizontalAlignment(SwingConstants.LEFT);
		lblCheckId.setBounds(270, 110, 70, 20);
		changeNickNamepanel.add(lblCheckId);

		tfCheckId = new RoundJTextField(5);
		tfCheckId.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfCheckId.setColumns(10);
		tfCheckId.setBackground(new Color(202, 206, 213));
		tfCheckId.setBounds(270, 140, 300, 40);
		changeNickNamepanel.add(tfCheckId);

		JLabel lblChangeNickName = new JLabel("변경할 닉네임");
		lblChangeNickName.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblChangeNickName.setHorizontalAlignment(SwingConstants.LEFT);
		lblChangeNickName.setBounds(270, 190, 100, 25);
		changeNickNamepanel.add(lblChangeNickName);

		tfCheckNickName = new RoundJTextField(5);
		tfCheckNickName.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfCheckNickName.setColumns(10);
		tfCheckNickName.setBackground(new Color(202, 206, 213));
		tfCheckNickName.setBounds(270, 220, 300, 40);
		changeNickNamepanel.add(tfCheckNickName);

		RoundJButton btnCheck = new RoundJButton("확 인");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = tfCheckId.getText().trim();
				String newNickname = tfCheckNickName.getText().trim();
				
				
				if (id.isEmpty() || newNickname.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "아이디와 닉네임을 모두 입력해주세요.");
					return;
				}
				if (player.getId() == null || !player.getId().equals(id)) {
					JOptionPane.showMessageDialog(contentPane, "아이디가 일치하지 않습니다.");
					return;
				}
				int newNick = JOptionPane.showConfirmDialog(contentPane, "닉네임을 '" + newNickname + "'(으)로 변경하시겠습니까?", "닉네임 변경 확인", JOptionPane.YES_NO_OPTION);

				if (newNick == JOptionPane.YES_OPTION) {
					
					ApiResponse newNickNameResponse = HttpConnecter.instance.changeNickname(id, newNickname);

					if (newNickNameResponse != null && newNickNameResponse.isSuccess()) {
						// 닉네임 변경 성공
						JOptionPane.showMessageDialog(contentPane, "닉네임이 '" + newNickname + "'(으)로 변경되었습니다.");
						player.setNickname(newNickname); // Player 객체의 닉네임 업데이트
						tfCheckId.setText(""); // 입력 필드 초기화
						tfCheckNickName.setText(""); // 입력 필드 초기화
						if(homeframe == null) {
							homeframe = new HomeFrame(player);
							homeframe.setVisible(true);
							homeframe.setResizable(false);
						} else {
							homeframe.setVisible(true);
							homeframe.setResizable(false);
						}
						setVisible(false);
					} else {
						// 닉네임 변경 실패
						String errorMessage = "알 수 없는 오류가 발생했습니다.";
						if (newNickNameResponse != null && newNickNameResponse.getError() != null) {
							errorMessage = newNickNameResponse.getError().getMessage();
						}
						JOptionPane.showMessageDialog(contentPane, "닉네임 변경에 실패했습니다: " + errorMessage);
					}
				}
			}
		});
		btnCheck.setBackground(new Color(185, 215, 234));
		btnCheck.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnCheck.setBounds(270, 300, 120, 40);
		changeNickNamepanel.add(btnCheck);

		RoundJButton btnBack = new RoundJButton("돌아가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_cardPanel.show(CardPanel, "myInfo"); // "내정보" 패널로 돌아가기
				tfCheckId.setText("");
				tfCheckNickName.setText("");
			}
		});
		btnBack.setText("돌아가기");
		btnBack.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnBack.setBackground(new Color(176, 180, 186));
		btnBack.setBounds(450, 300, 120, 40);
		changeNickNamepanel.add(btnBack);

		CardPanel.add(changeNickNamepanel, "changeNickName");
		
		// 4. 비밀번호 변경 패널
		chagePWPanel = new JPanel();
		chagePWPanel.setBackground(new Color(255, 255, 255));
		chagePWPanel.setLayout(null);
		
		CardPanel.add(chagePWPanel, "changePW");
		
		JLabel lblCheckId2 = new JLabel("아이디");
		lblCheckId2.setHorizontalAlignment(SwingConstants.LEFT);
		lblCheckId2.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblCheckId2.setBounds(270, 80, 70, 20);
		chagePWPanel.add(lblCheckId2);
		
		RoundJTextField tfCheckId2 = new RoundJTextField(5);
		tfCheckId2.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfCheckId2.setColumns(10);
		tfCheckId2.setBackground(new Color(202, 206, 213));
		tfCheckId2.setBounds(270, 110, 300, 40);
		chagePWPanel.add(tfCheckId2);
		
		JLabel lblCheckPW = new JLabel("현재 비밀번호");
		lblCheckPW.setHorizontalAlignment(SwingConstants.LEFT);
		lblCheckPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblCheckPW.setBounds(270, 160, 100, 25);
		chagePWPanel.add(lblCheckPW);
		
		tfCheckPW = new RoundJPasswordField(10);
		tfCheckPW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfCheckPW.setColumns(10);
		tfCheckPW.setBackground(new Color(202, 206, 213));
		tfCheckPW.setBounds(270, 190, 300, 40);
		chagePWPanel.add(tfCheckPW);
		
		JLabel lblChangePW = new JLabel("변경할 비밀번호");
		lblChangePW.setHorizontalAlignment(SwingConstants.LEFT);
		lblChangePW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblChangePW.setBounds(270, 240, 120, 25);
		chagePWPanel.add(lblChangePW);
		
		tfChangePW = new RoundJPasswordField(10);
		tfChangePW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfChangePW.setColumns(10);
		tfChangePW.setBackground(new Color(202, 206, 213));
		tfChangePW.setBounds(270, 270, 300, 40);
		chagePWPanel.add(tfChangePW);
		
		
		RoundJButton btnCheck2 = new RoundJButton("확 인");
		btnCheck2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 비밀번호 변경
				String id = tfCheckId2.getText().trim();
				String currentPassword = new String(tfCheckPW.getPassword()).trim();
				String newPassword = new String(tfChangePW.getPassword()).trim();

				if (id.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "아이디, 현재 비밀번호, 새 비밀번호를 모두 입력해주세요.");
					return;
				}
				if (player.getId() == null || !player.getId().equals(id)) {
					JOptionPane.showMessageDialog(contentPane, "아이디가 일치하지 않습니다.");
					return;
				}
				int changePWConfirm = JOptionPane.showConfirmDialog(contentPane, "비밀번호를 '" + newPassword + "'(으)로 변경하시겠습니까?", "비밀번호 변경 확인", JOptionPane.YES_NO_OPTION);

				if (changePWConfirm == JOptionPane.YES_OPTION) {
					ApiResponse changePWResponse = HttpConnecter.instance.changePassword(id, currentPassword, newPassword);

					if (changePWResponse != null && changePWResponse.isSuccess()) {
						// 비밀번호 변경 성공
						JOptionPane.showMessageDialog(contentPane, "비밀번호가 변경되었습니다.");
						player.setPassword(newPassword);
						tfCheckId2.setText("");
						tfCheckPW.setText("");
						tfChangePW.setText("");
						if(homeframe == null) {
							homeframe = new HomeFrame(player);
							homeframe.setVisible(true);
							homeframe.setResizable(false);
						} else {
							homeframe.setVisible(true);
							homeframe.setResizable(false);
						}
						setVisible(false);
					} else {
						// 비밀번호 변경 실패
						String errorMessage = "알 수 없는 오류가 발생했습니다.";
						if (changePWResponse != null && changePWResponse.getError() != null) {
							errorMessage = changePWResponse.getError().getMessage();
						}
						JOptionPane.showMessageDialog(contentPane, "비밀번호 변경에 실패했습니다: " + errorMessage);
					}
				}
			}
		});
		btnCheck2.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnCheck2.setBackground(new Color(185, 215, 234));
		btnCheck2.setBounds(270, 350, 120, 40);
		chagePWPanel.add(btnCheck2);
		
		RoundJButton btnBack2 = new RoundJButton("돌아가기");
		btnBack2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_cardPanel.show(CardPanel, "myInfo"); // "내정보" 패널로 돌아가기
				tfCheckId2.setText(""); 
				tfCheckPW.setText(""); 
				tfChangePW.setText(""); 
			}
		});
		btnBack2.setText("돌아가기");
		btnBack2.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnBack2.setBackground(new Color(176, 180, 186));
		btnBack2.setBounds(450, 350, 120, 40);
		chagePWPanel.add(btnBack2);
		
		// 처음에는 전적 패널이 보이도록 설정
		cl_cardPanel.show(CardPanel, "records");

		RoundJButton btnRecords = new RoundJButton("전적");
		btnRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_cardPanel.show(CardPanel, "records");
			}
		});
		btnRecords.setBounds(70, 90, 100, 60);
		MainPanel.add(btnRecords);
		btnRecords.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnRecords.setBackground(new Color(240, 240, 240));

		JSeparator spt2 = new JSeparator();
		spt2.setBounds(60, 160, 120, 3);
		MainPanel.add(spt2);
		spt2.setForeground(new Color(150, 150, 150));
		spt2.setBackground(new Color(150, 150, 150));

		RoundJButton btnMyInformation = new RoundJButton("내정보");
		btnMyInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_cardPanel.show(CardPanel, "myInfo"); // "myInfo" 패널 표시
			}
		});
		btnMyInformation.setBounds(70, 170, 100, 60);
		MainPanel.add(btnMyInformation);
		btnMyInformation.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnMyInformation.setBackground(new Color(240, 240, 240));

		JSeparator spt1 = new JSeparator();
		spt1.setBounds(60, 320, 120, 3);
		MainPanel.add(spt1);
		spt1.setForeground(new Color(150, 150, 150));
		spt1.setBackground(new Color(150, 150, 150));

		RoundJButton btnRanking = new RoundJButton("랭킹보기");
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rankingframe == null) {
					rankingframe = new RankingFrame(player);
					rankingframe.setVisible(true);
					rankingframe.setResizable(false);
				} else {
					rankingframe.setVisible(true);
					rankingframe.setResizable(false);
				}
				setVisible(false);
			}
		});
		btnRanking.setText("랭킹보기");
		btnRanking.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnRanking.setBackground(UIManager.getColor("Button.background"));
		btnRanking.setBounds(70, 250, 100, 60);
		MainPanel.add(btnRanking);

		JSeparator spt2_1 = new JSeparator();
		spt2_1.setForeground(new Color(150, 150, 150));
		spt2_1.setBackground(new Color(150, 150, 150));
		spt2_1.setBounds(60, 240, 120, 3);
		MainPanel.add(spt2_1);

		RoundJButton btnDelete = new RoundJButton("회원탈퇴"); // 버튼 이름 변경
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int Delete = JOptionPane.showConfirmDialog(contentPane, "정말로 회원탈퇴를 하시겠습니까?", "회원탈퇴", JOptionPane.YES_NO_OPTION);
				if(Delete == JOptionPane.YES_OPTION) { // Outer if-block for confirmation
					ApiResponse userDeleteRespone = HttpConnecter.instance.userDelete(player.getId());
					if(userDeleteRespone != null && userDeleteRespone.isSuccess()) {
						// 회원탈퇴 성공
						JOptionPane.showMessageDialog(contentPane, "회원탈퇴가 완료되었습니다.");
						// 로그인 프레임으로 돌아가기
						if(loginframe == null) {
							loginframe = new LoginFrame();
							loginframe.setResizable(false);
							loginframe.setVisible(true);
						} else {
							loginframe.setVisible(true);
						}
						setVisible(false);
					} else {
						// 회원탈퇴 실패
						String errorMessage = "알 수 없는 오류가 발생했습니다.";
						if (userDeleteRespone != null && userDeleteRespone.getError() != null) {
							errorMessage = userDeleteRespone.getError().getMessage();
						}
						JOptionPane.showMessageDialog(contentPane, "회원탈퇴에 실패했습니다: " + errorMessage);
					}
				}
			}
		});
		btnDelete.setBounds(70, 330, 100, 60);
		MainPanel.add(btnDelete);
		btnDelete.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnDelete.setBackground(new Color(240, 240, 240));

		RoundJLabel lblNickname = new RoundJLabel(player != null && player.getNickname() != null ? player.getNickname() : "");
		lblNickname.setBackground(new Color(185, 215, 234));
		lblNickname.setHorizontalAlignment(SwingConstants.CENTER);
		lblNickname.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		lblNickname.setBounds(550, 15, 200, 40);
		MainPanel.add(lblNickname);

		RoundJButton btnHome = new RoundJButton();
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(homeframe == null) {
					homeframe = new HomeFrame(player);
					homeframe.setVisible(true);
					homeframe.setResizable(false);
				} else {
					homeframe.setVisible(true);
					homeframe.setResizable(false);
				}
				setVisible(false);
			}
		});
		btnHome.setText("홈");
		btnHome.setForeground(Color.BLACK);
		btnHome.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnHome.setBackground(new Color(118, 159, 205));
		btnHome.setBounds(780, 15, 120, 40);
		MainPanel.add(btnHome);


		RoundJButton btnLogOut = new RoundJButton();
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginframe == null) {
					loginframe = new LoginFrame();
					loginframe.setResizable(false);
					loginframe.setVisible(true);
				} else {
					loginframe.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnLogOut.setText("로그 아웃");
		btnLogOut.setForeground(Color.BLACK);
		btnLogOut.setFont(new Font("CookieRun Regular", Font.PLAIN, 18));
		btnLogOut.setBackground(new Color(176, 180, 186));
		btnLogOut.setBounds(930, 15, 120, 40);
		MainPanel.add(btnLogOut);

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
		loadMyRecords();
		loadMyStats();
	}
	private void loadMyRecords() {
		// 백그라운드 스레드로 전적 데이터 호출
		new Thread(() -> {
			// API를 통해 사용자 전적 데이터 가져옴
			ApiResponse MyRecordsApiRes = HttpConnecter.instance.getUserRecord(player.getId());
			SwingUtilities.invokeLater(() -> { // UI 업데이트는 EDT에서 수행
				if (MyRecordsApiRes != null && MyRecordsApiRes.isSuccess()) {
					// 기존 테이블 데이터 모두 삭제
					tableModel.setRowCount(0);
					// JSON 문자열을 Record 객체 리스트로 변환
					List<Record> records = JSONManager.getJsonDataList(MyRecordsApiRes.getContent(),Record.class);

					// records가 null이 아니고 비어있지 않은 경우에만 테이블에 추가
					if (records != null && !records.isEmpty()) {
						for (Record record : records) {
							// 티어 정보를 가져오기 위해 API 호출
							ApiResponse recordApiRes = HttpConnecter.instance.getTierByScore(record.getAnswer_quiz());
							String tier = "정보 없음";
							
							// 티어 정보가 있다면 가져오기
							if (recordApiRes != null && recordApiRes.isSuccess()) {
								String content = recordApiRes.getContent(); // 티어 정보가 담긴 문자열
								// JSON 문자열을 Tier 객체로 변환
								Tier tierData = JSONManager.getJsonData(content, Tier.class);
								if (tierData != null) {
									tier = tierData.getName(); // 티어 이름 가져오기
								}
							}

							Object[] rowData = { 
									record.getPlay_date(), 
									record.getTitle(), 
									record.getAnswer_quiz(),
									tier 
									};
							// 테이블 모델에 행 추가
							tableModel.addRow(rowData);
						}
					} else {
						// JOptionPane.showMessageDialog(this, "아직 전적이 없습니다.");
					}
				} else {
					// 전적 로드 실패
					String errorMessage = "전적을 불러오는 데 실패했습니다.";
					if (MyRecordsApiRes != null && MyRecordsApiRes.getError() != null
							&& MyRecordsApiRes.getError().getMessage() != null) {
						errorMessage = MyRecordsApiRes.getError().getMessage();
					}
					// JOptionPane.showMessageDialog(this, errorMessage, "오류", JOptionPane.ERROR_MESSAGE);
				}
			});
		}).start();
	}

	private void loadMyStats() {
		// 백그라운드 스레드로 통계 데이터 호출
		new Thread(() -> {
			ApiResponse myStateApiRes = HttpConnecter.instance.getRecordStats(player.getId());
			// UI 업데이트는 EDT에서 수행
			SwingUtilities.invokeLater(() -> {
				if (myStateApiRes != null && myStateApiRes.isSuccess()) {
					try {
						String content = myStateApiRes.getContent();
						// JSON 문자열을 Record 객체로 변환
						Record statsRecord = JSONManager.getJsonData(content, Record.class);
						// statsRecord가 null이 아니면 통계 데이터를 테이블에 업데이트
						if (statsRecord != null) {
							DefaultTableModel statsTableModel = (DefaultTableModel) statsTable.getModel();
							// 통계 테이블의 두 번째 행부터 업데이트
							// 레코드 클래스의 필드를 "판수", "평균 맞춘 문제", "평균 티어"로 매핑

							// 판수 total_quiz
							statsTableModel.setValueAt(statsRecord.getTotal_quiz(), 1, 1);
							// 평균 맞춘 문제 avg_answer_quiz
							int highTier = (int) statsRecord.getAvg_answer_quiz();
							statsTableModel.setValueAt(statsRecord.getAvg_answer_quiz(), 1, 2);

							// 평균 맞춘 문제 highTier를 getTierByScore API로 가져옴
							ApiResponse tierApiRes = HttpConnecter.instance.getTierByScore(highTier);
							if (tierApiRes.isSuccess()) {
								// 티어 정보가 있다면 Tier 객체로 변환
								Tier tier = JSONManager.getJsonData(tierApiRes.getContent(), Tier.class);
								// 티어 이름을 테이블에 설정
								statsTableModel.setValueAt(tier.getName(), 1, 3);
							}
							// 테이블 갱신
							statsTableModel.fireTableDataChanged();

						} else {
							JOptionPane.showMessageDialog(null, "통계 데이터를 파싱할 수 없습니다. 서버 응답 형식을 확인하세요.", "오류",
									JOptionPane.ERROR_MESSAGE);
							System.err.println("Failed to parse stats JSON with Record class: " + content);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "통계 데이터를 처리하는 중 오류가 발생했습니다: " + e.getMessage(), "오류",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					String errorMessage = "통계 데이터를 불러오는 데 실패했습니다.";
					if (myStateApiRes != null && myStateApiRes.getError() != null
							&& myStateApiRes.getError().getMessage() != null) {
						errorMessage = myStateApiRes.getError().getMessage();
					}
					JOptionPane.showMessageDialog(null, errorMessage, "오류", JOptionPane.ERROR_MESSAGE);
					System.err.println("API Response error: "
							+ (myStateApiRes != null ? myStateApiRes.getError() : "null response"));
				}
			});
		}).start();
	}
}
	