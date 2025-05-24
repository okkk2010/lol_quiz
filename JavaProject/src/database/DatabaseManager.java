package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	
	final private String FOR_NAME_SRC = "com.mysql.cj.jdbc.Driver";
	final private String DB_URL = "jdbc:mysql://piduf46vuyw3eg9v:cm5iui8ncxt0n1zb@a07yd3a6okcidwap.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/fy96gv7kx2yidmi5";
	final private String DB_USER = "piduf46vuyw3eg9v";
	final private String DB_PASSWORD = "cm5iui8ncxt0n1zb";
	
	private Connection conn; 
	
	public DatabaseManager() {
		try {
			Class.forName(FOR_NAME_SRC);
			conn = DriverManager.getConnection(DB_URL
					, "piduf46vuyw3eg9v", "cm5iui8ncxt0n1zb");
			System.out.println("ok");
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc driver를 찾지 못했습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL 연결 실패");
			e.printStackTrace();
		}
		
	}
	
	public String getUserIdForName(int id) {
		try {
			Statement stmt = conn.createStatement();
			
			String selectSQL = "SELECT name FROM testTable where id = (?)";
			
			PreparedStatement pStmt = conn.prepareStatement(selectSQL);
			pStmt.setInt(1, id);
			
			ResultSet rs = pStmt.executeQuery();
			
			if(rs.next()) {
				System.out.println(rs.getString("name") + "을 찾았씁니다.");
				return rs.getString("name");				
			} else {
				return "";
			}
			
			
		} catch (SQLException e) {
			System.out.println("SQL 연결 실패");
			e.printStackTrace();
			return "";
		}
		
	}
}
