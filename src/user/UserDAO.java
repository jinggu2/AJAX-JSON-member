package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn;//데이터베이스에 접근하게 해주는 하나의 객체
	private PreparedStatement pstmt;
	private ResultSet rs;//정보를 담을 수 있는 객체
	
	public UserDAO() { //mysql에 접속을 하게 해줌,자동으로 데이터베이스 커넥션이 일어남
	      try {//예외처리
	         String dbURL = "jdbc:mysql://localhost:3306/AJAX?serverTimezone=UTC";
	         String dbID="root";
	         String dbPasseord="mysql";
	         Class.forName("com.mysql.jdbc.Driver");//mysql드라이버를 찾는다.
	         //드라이버는 mysql에 접속할 수 있도록 매개체 역할을 하는 하나의 라이브러리
	         conn=DriverManager.getConnection(dbURL,dbID,dbPasseord);
	      }catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public ArrayList<User> search(String userName){
		String SQL = "SELECT * FROM USER WHERE userName LIKE ?";
		ArrayList<User> userList = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + userName +"%" );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString(1));
				user.setUserAge(rs.getInt(2));
				user.setUserGender(rs.getString(3));
				user.setUserEmail(rs.getString(4));
				userList.add(user);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}
	
	public int register(User user) {
		String SQL = "INSERT INTO  USER VALUES (?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, user.getUserAge());
			pstmt.setString(3, user.getUserGender());
			pstmt.setString(4, user.getUserEmail());
			return pstmt.executeUpdate(); // return 1 (행의 수)
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
	
