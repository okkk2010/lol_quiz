package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DatabaseManager;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfInput;
	private JTextField tfOutput;
	private static DatabaseManager dbm;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dbm = new DatabaseManager();
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSeach = new JButton("검색");
		btnSeach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(tfInput.getText());
				if(tfInput.getText().length() == 0) {
					System.out.println("찾으실 아이디를 입력해주세요.");
					return;
				}
				
				String name = dbm.GetUserIdForName(id);
				
				if(name.length() == 0) {
					System.out.println("해당 아이디는 존재하지 않습니다.");
					return;
				}
				
				
				tfOutput.setText(name);			
			}  
		});
		btnSeach.setBounds(279, 47, 97, 23);
		contentPane.add(btnSeach);
		
		tfInput = new JTextField();
		tfInput.setBounds(130, 48, 116, 21);
		contentPane.add(tfInput);
		tfInput.setColumns(10);
		
		tfOutput = new JTextField();
		tfOutput.setBounds(130, 95, 116, 21);
		contentPane.add(tfOutput);
		tfOutput.setColumns(10);
		
		textField = new JTextField();
		textField.setText("아이디");
		textField.setBounds(43, 48, 62, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("이름");
		textField_1.setColumns(10);
		textField_1.setBounds(43, 95, 62, 21);
		contentPane.add(textField_1);
	}
}
