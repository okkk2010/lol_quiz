package database;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DBTestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFSignUpId;
	private JTextField tFSignUpNickName;
	private JTextField tFSignUpPassword;
	private JTextField tFSignInId;
	private JTextField tFSignInPassword;
	
	private static DatabaseManager dbm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBTestFrame frame = new DBTestFrame();
					dbm = new DatabaseManager();
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
	public DBTestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tFSignUpId = new JTextField();
		tFSignUpId.setBounds(37, 50, 116, 21);
		contentPane.add(tFSignUpId);
		tFSignUpId.setColumns(10);
		
		tFSignUpNickName = new JTextField();
		tFSignUpNickName.setBounds(37, 97, 116, 21);
		contentPane.add(tFSignUpNickName);
		tFSignUpNickName.setColumns(10);
		
		tFSignUpPassword = new JTextField();
		tFSignUpPassword.setBounds(37, 137, 116, 21);
		contentPane.add(tFSignUpPassword);
		tFSignUpPassword.setColumns(10);
		
		tFSignInId = new JTextField();
		tFSignInId.setBounds(264, 50, 116, 21);
		contentPane.add(tFSignInId);
		tFSignInId.setColumns(10);
		
		tFSignInPassword = new JTextField();
		tFSignInPassword.setBounds(264, 97, 116, 21);
		contentPane.add(tFSignInPassword);
		tFSignInPassword.setColumns(10);
		
		JButton btnSignUp = new JButton("sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tFSignUpId.getText();
				String nickName = tFSignUpNickName.getText();
				String password = tFSignUpPassword.getText();
				
				DatabaseManager.SignUpState suState =  dbm.SignUp(id, nickName, password);
				switch(suState) {
					case DatabaseManager.SignUpState.SUCCESS:
						System.out.println("회원가입에 성공했습니다!");
						break;
					case DatabaseManager.SignUpState.ID_DUPLICATION:
						System.out.println("아이디가 중복 되었습니다.");
						break;
					case DatabaseManager.SignUpState.UNKOWN_ERROR:
						System.out.println("회원가입에 실패했습니다.");
						break;
				}
			}
		});
		btnSignUp.setBounds(37, 197, 116, 23);
		contentPane.add(btnSignUp);
		
		JButton btnSignIn = new JButton("sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tFSignInId.getText();
				String password = tFSignInPassword.getText();
				
				DatabaseManager.SignInState siState = dbm.SignIn(id, password);
				switch(siState) {
					case DatabaseManager.SignInState.SUCCESS:
						System.out.println("로그인에 성공했습니다!");
						break;
					case DatabaseManager.SignInState.ID_INCORRECT:
						System.out.println("해당 아이디로 가입된 계정이 없습니다.");
						break;
					case DatabaseManager.SignInState.PASSWORD_INCORRECT:
						System.out.println("비밀번호가 틀립니다!");
						break;
					case DatabaseManager.SignInState.FAIL:
					case DatabaseManager.SignInState.UNKOWN_ERROR:
						System.out.println("로그인에 실패했습니다.");
						break;
				}
			}
		});
		btnSignIn.setBounds(264, 197, 116, 23);
		contentPane.add(btnSignIn);
	}
}
