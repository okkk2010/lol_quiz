package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import client.uiTool.RoundJPanel;
import database.DatabaseManager;
import client.uiTool.RoundJButton;
import java.awt.Font;
import java.awt.BorderLayout; // BorderLayout 임포트
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTable; // JTable 임포트
import javax.swing.SwingConstants;
import javax.swing.JScrollPane; // JScrollPane 임포트
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel; // DefaultTableModel 임포트
import javax.swing.table.TableColumnModel;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.UIManager;
import java.awt.FlowLayout;

public class MyInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel CardPanel; // CardLayout을 적용할 패널
	private CardLayout cl_cardPanel; // CardLayout 변수 추가
	private LoginFrame loginframe;
	private HomeFrame homeframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MyInfoFrame frame = new MyInfoFrame(); 
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
	public MyInfoFrame() {
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

		RoundJButton btnMyInfo = new RoundJButton();
		btnMyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 닉네임 버튼 액션 (필요하다면 여기에 추가)
			}
		});
		btnMyInfo.setText("닉네임");
		btnMyInfo.setForeground(Color.BLACK);
		btnMyInfo.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnMyInfo.setBackground(new Color(185, 215, 234));
		btnMyInfo.setBounds(550, 15, 200, 40);
		MainPanel.add(btnMyInfo);

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
		btnLogOut.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnLogOut.setBackground(new Color(176, 180, 186));
		btnLogOut.setBounds(930, 15, 120, 40);
		MainPanel.add(btnLogOut);


		RoundJPanel RecordPanel = new RoundJPanel(5);
		RecordPanel.setBounds(190, 90, 860, 480);
		MainPanel.add(RecordPanel);
		RecordPanel.setLayout(null);


		CardPanel = new JPanel(); // CardPanel 초기화
		CardPanel.setBounds(10, 10, 840, 460); // RecordPanel 내에서 CardPanel의 크기
		RecordPanel.add(CardPanel);

		cl_cardPanel = new CardLayout(0, 0); // CardLayout 초기화
		CardPanel.setLayout(cl_cardPanel); // CardPanel에 CardLayout 설정

		// 1. "전적" 내용을 담을 패널 (JScrollPane으로 감싸기)
		JPanel myRecordsPanel = new JPanel();
		myRecordsPanel.setBackground(new Color(240, 240, 240));
		myRecordsPanel.setLayout(new BorderLayout()); // BorderLayout 유지
		
		// **새로 추가된 부분: myRecordsPanel에 EmptyBorder를 설정하여 여백 추가**
		// 상, 좌, 하, 우 순서로 패딩 값 지정
		myRecordsPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); // 상하좌우 20픽셀 여백

		// 테이블 데이터 및 컬럼 정의
		String[] columnNames = {"게임 종류", "총 문제", "맞춘 문제", "틀린 문제", "총 점수"};
		Object[][] data = new Object[50][5]; // 50개의 더미 전적 데이터
		for (int i = 0; i < 50; i++) {
		    data[i][0] = "챔피언 퀴즈 " + (i + 1);
		    data[i][1] = 10;
		    data[i][2] = 8 + (i % 3);
		    data[i][3] = 2 - (i % 2);
		    data[i][4] = (int)data[i][2] * 100 - (int)data[i][3] * 50;
		}

		// 테이블 내의 어떤 셀도 사용자 인터페이스를 통해 직접 편집할 수 없게 됩니다. 즉, 모든 셀은 "읽기 전용"
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
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
		recordsTable.setShowVerticalLines(false); // 세로줄 숨기기
		recordsTable.setShowHorizontalLines(false); // 가로줄 숨기기
		recordsTable.setGridColor(new Color(228, 235, 250)); // 그리드 색상 일치

		recordsTable.setBackground(new Color(255, 255, 255)); // 배경 흰색
		recordsTable.setForeground(Color.DARK_GRAY); // 텍스트 색상
		recordsTable.setSelectionBackground(new Color(185, 215, 234)); // 선택된 행의 배경색
		recordsTable.setSelectionForeground(Color.BLACK); // 선택된 행의 글자색

		// 테이블 헤더 꾸미기
		recordsTable.getTableHeader().setBackground(new Color(185, 215, 234));
		recordsTable.getTableHeader().setForeground(Color.BLACK);
		recordsTable.getTableHeader().setReorderingAllowed(false); // 열 순서 변경 막기
		recordsTable.getTableHeader().setResizingAllowed(false); // 열 크기 조절 막기
		recordsTable.getTableHeader().setOpaque(false);

		// JTable 테두리 제거
		recordsTable.setBorder(null);
		
		// 문제 종류, 총 문제, 맞춘 문제, 틀린 문제 가운데 정렬
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		// 최고 점수 오른쪽 정렬
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// 정렬 적용
		for (int i = 0; i < recordsTable.getColumnCount(); i++) {
			if (i == 3) {
				recordsTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
			} else {
				recordsTable.getColumnModel().getColumn(i).setCellRenderer(CenterRenderer);
			}
		}

		JScrollPane recordsScrollPane = new JScrollPane(recordsTable);
		
		myRecordsPanel.add(recordsScrollPane, BorderLayout.CENTER); // recordsScrollPane을 myRecordsPanel에 추가

		CardPanel.add(myRecordsPanel, "records"); // myRecordsPanel (JScrollPane 포함)을 CardPanel에 추가


		// 2. "내정보" 내용을 담을 패널
		JPanel myInfoContentPanel = new JPanel();
		myInfoContentPanel.setBackground(Color.PINK); // 구분을 위해 색상 추가
		CardPanel.add(myInfoContentPanel, "myInfo"); // "myInfo"라는 이름으로 추가
		myInfoContentPanel.setLayout(null);
		
		// 3. "회원탈퇴" 내용을 담은 패널
		JPanel DeleteInfoPanel = new JPanel();
		DeleteInfoPanel.setBackground(new Color(128, 128, 192)); // 구분을 위해 색상 추가
		CardPanel.add(DeleteInfoPanel, "DeleteInfo"); // "DeleteInfo"라는 이름으로 추가
		DeleteInfoPanel.setLayout(null);

		// 처음에는 "전적" 패널이 보이도록 설정 (선택 사항)
		cl_cardPanel.show(CardPanel, "records");

		RoundJButton btnRecords = new RoundJButton("전적"); // 버튼 이름 변경
		btnRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_cardPanel.show(CardPanel, "records"); // "records" 패널 표시
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

		RoundJButton btnMyInformation = new RoundJButton("내정보"); // 버튼 이름 변경
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
		spt1.setBounds(60, 240, 120, 3);
		MainPanel.add(spt1);
		spt1.setForeground(new Color(150, 150, 150));
		spt1.setBackground(new Color(150, 150, 150));

		RoundJButton btnDelete = new RoundJButton("회원탈퇴"); // 버튼 이름 변경
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_cardPanel.show(CardPanel,"DeleteInfo");
			}
		});
		btnDelete.setBounds(70, 250, 100, 60);
		MainPanel.add(btnDelete);
		btnDelete.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnDelete.setBackground(new Color(240, 240, 240));
		
		RoundJButton btnHome = new RoundJButton();
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(homeframe == null) {
					homeframe = new HomeFrame();
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
		btnHome.setBackground(new Color(118, 159, 205));
		btnHome.setBounds(780, 15, 120, 40);
		MainPanel.add(btnHome);
		
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