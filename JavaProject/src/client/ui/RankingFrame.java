package client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import client.uiTool.RoundJPanel;
import database.DatabaseManager;
import javax.swing.UIManager;
import java.awt.FlowLayout;

public class RankingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static DatabaseManager dbm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RankingFrame frame = new RankingFrame(dbm);
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
	public RankingFrame(DatabaseManager dbManger) {
		this.dbm = dbManger;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 240, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RoundJPanel MainPanel = new RoundJPanel(5);
		MainPanel.setLayout(null);
		MainPanel.setBackground(Color.WHITE);
		MainPanel.setBounds(80, 40, 1080, 600);
		contentPane.add(MainPanel);
		
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
