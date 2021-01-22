package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn;//�����ͺ��̽��� �����ϰ� ���ִ� �ϳ��� ��ü
	private PreparedStatement pstmt;
	private ResultSet rs;//������ ���� �� �ִ� ��ü
	
	public UserDAO() { //mysql�� ������ �ϰ� ����,�ڵ����� �����ͺ��̽� Ŀ�ؼ��� �Ͼ
	      try {//����ó��
	         String dbURL = "jdbc:mysql://localhost:3306/AJAX?serverTimezone=UTC";
	         String dbID="root";
	         String dbPasseord="mysql";
	         Class.forName("com.mysql.jdbc.Driver");//mysql����̹��� ã�´�.
	         //����̹��� mysql�� ������ �� �ֵ��� �Ű�ü ������ �ϴ� �ϳ��� ���̺귯��
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
			return pstmt.executeUpdate(); // return 1 (���� ��)
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
}
	
