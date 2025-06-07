package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.Color;
import client.uiTool.RoundJPanel;
import dataSet.Tier.Tier;
import dataSet.record.Record;
import dataSet.user.User;
import dataSet.user.UserNTier;
import database.ApiResponse;
import database.DatabaseManager;
import database.HttpConnecter;
import database.JSONManager;

import javax.swing.UIManager;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;

import client.CtManager.Player;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJLabel;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class RankingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNickName;
	private JTextField tfRanking;
	private JTextField tfMaxScore;
	private HomeFrame homeframe;
	private LoginFrame loginframe;
	private Player player;
	private JLabel lblMyHighTier;
	private DefaultTableModel tableModel;
	private JTable rankingTable;
	private RoundJPanel TierPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RankingFrame frame = new RankingFrame();
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
	public RankingFrame(Player player) {
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
		MainPanel.setBackground(Color.WHITE);
		MainPanel.setBounds(80, 40, 1080, 600);
		contentPane.add(MainPanel);

		RoundJPanel ResultPanel = new RoundJPanel(5);
		ResultPanel.setLayout(null);
		ResultPanel.setBackground(new Color(228, 235, 250));
		ResultPanel.setBounds(70, 70, 940, 460);
		MainPanel.add(ResultPanel);

		TierPanel = new RoundJPanel(5);
		TierPanel.setBackground(new Color(255, 255, 255));
		TierPanel.setBounds(60, 60, 200, 200);
		ResultPanel.add(TierPanel);
		TierPanel.setLayout(new BorderLayout(0, 0));

		lblMyHighTier = new RoundJLabel("");
		TierPanel.add(lblMyHighTier, BorderLayout.CENTER);
		myHighTier();

		RoundJPanel TopTierPanel = new RoundJPanel(5);
		TopTierPanel.setBackground(new Color(255, 255, 255));
		TopTierPanel.setBounds(350, 60, 500, 360);
		ResultPanel.add(TopTierPanel);
		TopTierPanel.setLayout(null);

		JLabel lblTop = new JLabel("TOP 30");
		lblTop.setHorizontalAlignment(SwingConstants.CENTER);
		lblTop.setForeground(Color.BLACK);
		lblTop.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblTop.setBackground(new Color(185, 215, 234));
		lblTop.setBounds(200, 0, 100, 40);
		TopTierPanel.add(lblTop);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 38, 480, 4);
		TopTierPanel.add(separator);

		// 스크롤 가능한 랭킹 목록 (JTable) 추가
		// 1. JTable의 헤더 정의
		String[] columnNames = { "순위", "닉네임", "최고점수" };

		tableModel = new DefaultTableModel(columnNames, 0) { // 0은 초기 행 수, 나중에 데이터 추가 시 자동으로 행이 늘어남
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 테이블 셀 편집 불가능하게 설정
			}
		};
		rankingTable = new JTable(tableModel);
		rankingTable.setEnabled(false);
		rankingTable.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		rankingTable.setRowHeight(30); // 행 높이 설정
		rankingTable.getTableHeader().setFont(new Font("CookieRun Regular", Font.BOLD, 16)); // 헤더 폰트 설정
		rankingTable.setFillsViewportHeight(true); // JTable이 뷰포트 높이를 채우도록 설정

		// 순위, 닉네임, 점수 가운데 정렬
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬 적용
		for (int i = 0; i < rankingTable.getColumnCount(); i++) {
			rankingTable.getColumnModel().getColumn(i).setCellRenderer(CenterRenderer);
		}

		// JTable 꾸미기 (배경색, 그리드 색상, 선택 색상, 폰트 등)
		rankingTable.setGridColor(new Color(228, 235, 250)); // 그리드 색상 일치
		rankingTable.setBackground(new Color(255, 255, 255)); // 배경 흰색
		rankingTable.setForeground(Color.DARK_GRAY); // 텍스트 색상
		rankingTable.setSelectionBackground(new Color(185, 215, 234)); // 선택된 행의 배경색
		rankingTable.setSelectionForeground(Color.BLACK); // 선택된 행의 글자색

		// 테이블 헤더 꾸미기
		rankingTable.getTableHeader().setBackground(new Color(185, 215, 234));
		rankingTable.getTableHeader().setForeground(Color.BLACK);
		rankingTable.getTableHeader().setReorderingAllowed(false); // 열 순서 변경 막기
		rankingTable.getTableHeader().setResizingAllowed(false); // 열 크기 조절 막기
		rankingTable.getTableHeader().setOpaque(false);

		// JTable 테두리 제거
		rankingTable.setBorder(null);

		// 3. JTable을 JScrollPane으로 감싸기
		JScrollPane scrollPane = new JScrollPane(rankingTable);
		scrollPane.setBounds(10, 45, 480, 305); // Y좌표를 separator 바로 아래로, 높이를 남은 공간에 맞춰 조정
		TopTierPanel.add(scrollPane); // TopTierPanel에 JScrollPane 추가

		JLabel lblNickName = new JLabel("닉네임");
		lblNickName.setForeground(new Color(0, 0, 0));
		lblNickName.setBackground(new Color(185, 215, 234));
		lblNickName.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblNickName.setHorizontalAlignment(SwingConstants.CENTER);
		lblNickName.setBounds(60, 280, 60, 40);
		ResultPanel.add(lblNickName);

		JLabel lblRanking = new JLabel("순위");
		lblRanking.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblRanking.setHorizontalAlignment(SwingConstants.CENTER);
		lblRanking.setBounds(60, 330, 60, 40);
		ResultPanel.add(lblRanking);

		JLabel lblMaxScore = new JLabel("최고점수");
		lblMaxScore.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblMaxScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxScore.setBounds(60, 380, 60, 40);
		ResultPanel.add(lblMaxScore);

		tfNickName = new JTextField();
		tfNickName.setHorizontalAlignment(SwingConstants.CENTER);
		tfNickName.setForeground(new Color(0, 0, 0));
		tfNickName.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		tfNickName.setBackground(new Color(255, 255, 255));
		tfNickName.setBounds(140, 280, 120, 40);
		tfNickName.setText(player.getNickname());
		tfNickName.setFocusable(false);
		tfNickName.setColumns(10);
		ResultPanel.add(tfNickName);

		tfRanking = new JTextField("0");
		tfRanking.setHorizontalAlignment(SwingConstants.CENTER);
		tfRanking.setBackground(new Color(255, 255, 255));
		tfRanking.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		tfRanking.setColumns(10);
		tfRanking.setBounds(140, 330, 120, 40);
		tfRanking.setFocusable(false);
		
		String rkUserId = player.getId();
		
		ApiResponse apiRes = HttpConnecter.instance.getUserRanking(rkUserId);
		if(apiRes.isSuccess()) {
			String content = apiRes.getContent();
			try {
				int rank = Integer.parseInt(content);
				tfRanking.setText(rank + "위");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(contentPane, "랭킹 정보가 올바르지 않습니다.");
			}
		} else {
			tfRanking.setText("-");
//			switch(apiRes.getError().getCode()) {
//				case "NOT_FOUND_USER":
//					JOptionPane.showMessageDialog(contentPane, "사용자를 찾을 수 없습니다.");
//					break;
//				case "SERVER_ERROR":
//					JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
//					break;
//			}
		}
		
		ResultPanel.add(tfRanking);

		tfMaxScore = new JTextField("0");
		tfMaxScore.setHorizontalAlignment(SwingConstants.CENTER);
		tfMaxScore.setBackground(new Color(255, 255, 255));
		tfMaxScore.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		tfMaxScore.setColumns(10);
		tfMaxScore.setBounds(140, 380, 120, 40);
		tfMaxScore.setFocusable(false);
		
		String mSUserId = player.getId();

		ApiResponse MaxScoreApiRes = HttpConnecter.instance.getHighAnswerQuiz(mSUserId);
		if (MaxScoreApiRes != null && MaxScoreApiRes.isSuccess()) {
		    String content = MaxScoreApiRes.getContent();
		    try {
		        int highScore = Integer.parseInt(content);
		        tfMaxScore.setText(String.valueOf(highScore));
		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(contentPane, "최고 점수 정보가 올바르지 않습니다: " + content);
		    }
		} else {
		    // 에러 처리 로직은 그대로 유지
			tfMaxScore.setText("-");
//		    switch (MaxScoreApiRes.getError().getCode()) {
//		        case "NOT_FOUND_USER":
//		            JOptionPane.showMessageDialog(contentPane, "사용자를 찾을 수 없습니다.");
//		            break;
//		        case "SERVER_ERROR":
//		            JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
//		            break;
//		        default: // 예상치 못한 에러 코드 처리
//		            JOptionPane.showMessageDialog(contentPane, "알 수 없는 오류 발생: " + MaxScoreApiRes.getError().getCode());
//		            break;
//		    }
		}
		ResultPanel.add(tfMaxScore);

		RoundJButton btnHome = new RoundJButton();
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (homeframe == null) {
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
				if (loginframe == null) {
					loginframe = new LoginFrame();
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
		top30Ranking();
	}
	
	public void top30Ranking() {
		// 랭킹 정보를 가져오는 API 호출
	    ApiResponse top30ApiRes = HttpConnecter.instance.getRanking();
	    
	    if(top30ApiRes != null && top30ApiRes.isSuccess()) {
	        String content = top30ApiRes.getContent();
	        // JSONManager를 통해 Record 객체 리스트로 변환
	        ArrayList<Record> records = JSONManager.getJsonDataList(content, Record.class);
	        
	        tableModel.setRowCount(0); // 기존 테이블 데이터 초기화

	        if(records != null && !records.isEmpty()) {
	            for(Record record : records) {
	                Object[] rowData = {
	                    record.getRank_num(), // 순위
	                    record.getNickname(), // 닉네임
	                    record.getAnswer_quiz() // 최고 점수
	                };
	                tableModel.addRow(rowData); // 테이블 모델에 행 추가
	            }
	          
	        } else {
	            // 랭킹 정보가 없을 경우
	            JOptionPane.showMessageDialog(contentPane, "랭킹 정보가 없습니다.");
	        }
	    } else {
	        // API 호출 실패 시 에러 처리
	        switch(top30ApiRes.getError().getCode()) {
//	            case "SERVER_ERROR":
//	                JOptionPane.showMessageDialog(contentPane, "서버 오류 발생");
//	                break;
//	            default:
//	                JOptionPane.showMessageDialog(contentPane, "알 수 없는 오류 발생: " + top30ApiRes.getError().getCode());
//	                break;
	        }
	    }
	}
	public void myHighTier () {
		 // 플레이어의 최고 티어를 가져오는 API 호출
		 ApiResponse userInfoapiRes = HttpConnecter.instance.userInfo(player.getId());
		 if (userInfoapiRes != null && userInfoapiRes.isSuccess()) {
			User user = JSONManager.getJsonData(userInfoapiRes.getContent(), User.class);
			
			byte[] tierImgData = HttpConnecter.instance.loadImage(user.getTier());
			if (tierImgData != null) {
               ByteArrayInputStream tierBais = new ByteArrayInputStream(tierImgData);
               try {
                   BufferedImage tierImg = ImageIO.read(tierBais);
                   lblMyHighTier.setIcon(new ImageIcon(new ImageIcon(tierImg).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
               } catch (IOException e1) {
                   e1.printStackTrace();
                   JOptionPane.showMessageDialog(contentPane, "티어 이미지 로드 실패", "오류", JOptionPane.ERROR_MESSAGE);
               }
           } else {
               JOptionPane.showMessageDialog(contentPane, "티어 이미지 로드 실패", "오류", JOptionPane.ERROR_MESSAGE);
           }
       } else {
           JOptionPane.showMessageDialog(contentPane, "티어 정보 로드 실패: " + userInfoapiRes.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
       }
		 
	}
}
