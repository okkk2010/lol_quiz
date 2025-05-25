package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class DatabaseManager {
	
	public enum SignUpState {
		SUCCESS,
		ID_DUPLICATION,
		UNKOWN_ERROR
	};
	
	public enum SignInState {
		SUCCESS,
		ID_INCORRECT,
		PASSWORD_INCORRECT,
		FAIL,
		UNKOWN_ERROR
	}
	
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
	
	public String GetUserIdForName(int id) {
		try {
			String selectSQL = "SELECT name FROM testTable where id = (?)";
			ResultSet rs = SelectSQLMapping(selectSQL, id);
			
			if(rs.next()) {
				System.out.println(rs.getString("name") + "을 찾았습 `니다.");
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
	
	public SignInState SignIn(String id, String password) {
		String selectSQL = "SELECT password FROM user WHERE id = (?)";
		
		try(PreparedStatement pStmt = conn.prepareStatement(selectSQL)) {
			pStmt.setString(1, id);
			ResultSet rs = pStmt.executeQuery();
			
			if(rs.next()) 
			{
				if(rs.getString("password").equals(password) ) 
				{
					System.out.println(id + "의 로그인에 성공했습니다!");
					return SignInState.SUCCESS;
				} 
				else
				{
					System.out.println("비밀번호가 일치하지 않습니다.");
					return SignInState.PASSWORD_INCORRECT;
				}
			} 
			else
			{
				System.out.println("아이디가 존재하지 않습니다.");
				return SignInState.ID_INCORRECT;
			}
		} catch (SQLException e) {
			System.out.println("SignIn error : ");
			e.printStackTrace();
			return SignInState.UNKOWN_ERROR;
		}
	}
	
	public SignUpState SignUp(String id, String nickName, String password) {
		String insertSQL = "INSERT INTO user(id, nickName, password) values (?,?,?)";
		
		try(PreparedStatement pStmt = conn.prepareStatement(insertSQL)) {
			pStmt.setString(1, id); pStmt.setString(2, nickName); pStmt.setString(3, password);
			int flag = pStmt.executeUpdate();
			
			if(flag == 0) {
				System.out.println("signup 중 오류가 발생했습니다.");
				return SignUpState.UNKOWN_ERROR;
			}
		} catch (SQLException e) {
			System.out.println("SignUp error : ");
			e.printStackTrace();
			return SignUpState.ID_DUPLICATION;
		}

		System.out.println(id + ", " + nickName + "의 유저가 회원가입되었습니다!");
		return SignUpState.SUCCESS;
	}
	
	private ResultSet SelectSQLMapping(String sql, Object...objects) {
		ResultSet rs = null;
		try(PreparedStatement pStmt = conn.prepareStatement(sql)) {
			for(int i = 0; i < objects.length; i++) {
				pStmt.setObject(i+1, objects[i]);
				rs = pStmt.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("sql 매핑 중 오류 : ");
			e.printStackTrace();
		}
		
		return rs;
	}
}
