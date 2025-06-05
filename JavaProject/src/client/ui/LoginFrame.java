package client.ui;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.CtManager.Player;
import client.uiTool.RoundJButton;
import client.uiTool.RoundJPanel;
import client.uiTool.RoundJTextField;
import dataSet.user.User;
import database.ApiResponse;
import database.DatabaseManager;
import database.HttpConnecter;
import database.JSONManager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfInputID;
	private RoundJPasswordField tfInputPW;
	private HomeFrame homeframe;
	private RegisterFrame Reframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setResizable(false); // 화면 고정
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
	public LoginFrame() {
		setTitle("롤 퀴즈");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 660);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(228, 235, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		RoundJPanel LoginPanel = new RoundJPanel(5);
		LoginPanel.setBackground(new Color(255, 255, 255));
		LoginPanel.setBounds(10, 10, 800, 600);
		contentPane.add(LoginPanel);
		LoginPanel.setLayout(null);

		// 타이틀
		JLabel lblTitle = new JLabel("롤 챔피언 퀴즈");
		lblTitle.setBounds(300, 100, 200, 40);
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("CookieRun Bold", Font.BOLD, 28));
		LoginPanel.add(lblTitle);

		JLabel lblID = new JLabel("아이디");
		lblID.setHorizontalAlignment(SwingConstants.LEFT);
		lblID.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblID.setBounds(250, 200, 70, 20);
		LoginPanel.add(lblID);

		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setHorizontalAlignment(SwingConstants.LEFT);
		lblPW.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		lblPW.setBounds(250, 280, 70, 20);
		LoginPanel.add(lblPW);

		// 아이디 입력 (RoundJTextField)
		tfInputID = new RoundJTextField(10); // RoundJTextField 생성
		tfInputID.setBackground(new Color(202, 206, 213));
		tfInputID.setFont(new Font("CookieRun Regular", Font.PLAIN, 20));
		tfInputID.setBounds(250, 230, 300, 40);
		tfInputID.setForeground(Color.DARK_GRAY); // 텍스트와 테두리 색상
		LoginPanel.add(tfInputID);

		// 비밀번호 입력 (RoundJPasswordField)
		tfInputPW = new RoundJPasswordField(10); // RoundJPasswordField 생성
		tfInputPW.setBackground(new Color(202, 206, 213));
		tfInputPW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tfInputPW.setBounds(250, 310, 300, 40);
		tfInputPW.setForeground(Color.DARK_GRAY);

		tfInputPW.setEchoChar('*');
		LoginPanel.add(tfInputPW);

		// 로그인 버튼 (RoundButton)
		JButton btnLogin = new RoundJButton();
		btnLogin.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				String id = tfInputID.getText().trim();
				String password = tfInputPW.getText().trim();
				
				// 아이디 또는 비밀번호가 비어있는지 확인
				if (id.isEmpty()) {
					JOptionPane.showMessageDialog(LoginPanel, "아이디를 입력해주세요.", "로그인 오류", JOptionPane.WARNING_MESSAGE);
					tfInputID.requestFocusInWindow(); // 아이디 입력 포커스
					return;
				}
				if (password.isEmpty()) {
					JOptionPane.showMessageDialog(LoginPanel, "비밀번호를 입력해주세요.", "로그인 오류", JOptionPane.WARNING_MESSAGE);
					tfInputPW.requestFocusInWindow(); // 비밀번호 입력 포커스
					return;
				}
				ApiResponse apiRes = HttpConnecter.instance.signInUser(id, password);
				if(apiRes.isSuccess()) {
					JOptionPane.showMessageDialog(LoginPanel, "로그인 성공!!");
					
					// 로그인 성공 후 사용자 정보 가져오기
					ApiResponse userInfoResponse = HttpConnecter.instance.userInfo(id);
					
					if(userInfoResponse.isSuccess()) {
						User user = null;
						try {
							// 사용자 정보를 JSON으로 파싱하여 User 객체 생성
							user = JSONManager.getJsonData(userInfoResponse.getContent(), User.class);
						} catch (Exception parseException) {
							System.err.println("사용자 데이터 파싱 오류: " + parseException.getMessage());
							JOptionPane.showMessageDialog(LoginPanel, "사용자 정보를 가져오는 데 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (user != null) {
							// 사용자 정보를 성공적으로 가져왔으므로 HomeFrame 생성
							if(homeframe == null) {
								homeframe = new HomeFrame(new Player(user));			
								homeframe.setResizable(false);
								homeframe.setVisible(true);
							} else {
								// 이미 홈 프레임이 있다면 다시 보이게 합니다.
								homeframe.setVisible(true);
							}
							setVisible(false); // 로그인 프레임 숨기기
						} else {
							// content 필드가 null이거나 파싱 후 user 객체가 null인 경우
							JOptionPane.showMessageDialog(LoginPanel, "사용자 정보가 비어있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						// 사용자 정보 가져오기 실패 (userInfo API 호출 실패)
						switch(userInfoResponse.getError().getCode()) {
						case "NOT_FOUND_ID": 
							JOptionPane.showMessageDialog(LoginPanel, "사용자 정보를 찾을 수 없습니다.");
							break;
						default:
							JOptionPane.showMessageDialog(LoginPanel, "사용자 정보를 가져오는 중 오류가 발생했습니다: " + userInfoResponse.getError().getMessage());
							break;
						}
					}
				} else {
					// 로그인 실패 (signInUser API 호출 실패)
					switch(apiRes.getError().getCode()) {
						case "NOT_MATCH_PASSWORD":
							JOptionPane.showMessageDialog(LoginPanel, "비밀번호가 틀립니다.");
							break;
						case "NOT_FOUND_ID":
							JOptionPane.showMessageDialog(LoginPanel, "아이디를 찾을 수 없습니다.");
							break;
						case "DUPLICATE_ID":
							JOptionPane.showMessageDialog(LoginPanel, "해당 아이디로 가입된 계정이 없습니다.");
							break;
						default:
							JOptionPane.showMessageDialog(LoginPanel, "로그인 중 알 수 없는 오류가 발생했습니다: " + apiRes.getError().getMessage());
							break;
					}
				}
			}
		});
		
		btnLogin.setText("로그인");
		btnLogin.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnLogin.setBackground(new Color(185, 215, 234));
		btnLogin.setBounds(250, 380, 100, 40);
		btnLogin.setForeground(Color.BLACK); // 테두리 색상
		LoginPanel.add(btnLogin);
		
		// enter 키 입력 가능
		this.getRootPane().setDefaultButton(btnLogin);

		// 회원가입 버튼 (RoundButton)
		JButton btnSignUp = new RoundJButton();
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(Reframe == null) {
					Reframe = new RegisterFrame();				
					Reframe.setVisible(true);
				} else {
					Reframe.setVisible(true);
				}
				setVisible(false);
			}
		});
		btnSignUp.setText("회원가입");
		btnSignUp.setFont(new Font("CookieRun Regular", Font.PLAIN, 16));
		btnSignUp.setBackground(new Color(176, 180, 186));
		btnSignUp.setBounds(450, 380, 100, 40);
		btnSignUp.setForeground(new Color(0, 0, 0)); // 테두리 색상
		LoginPanel.add(btnSignUp);


		RoundJPanel outLine1 = new RoundJPanel(5);
		outLine1.setBounds(15, 605, 800, 11);
		contentPane.add(outLine1);
		outLine1.setBackground(new Color(100, 100, 100));
		outLine1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		RoundJPanel outLine2 = new RoundJPanel(5);
		outLine2.setBounds(805, 15, 11, 600);
		contentPane.add(outLine2);
		outLine2.setBackground(new Color(100, 100, 100));
		outLine2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setLocationRelativeTo(null);

	}
}
class RoundJPasswordField extends JPasswordField {
    private Shape shape;
    private int arcWidth = 20;
    private int arcHeight = 20;

    public RoundJPasswordField(int size) {
        super(size);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
        g2.dispose();
    }
    
}
