package d20200723;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

//EMP 테이블의 모든 정보를 조회하세요.
// https://iwbtbitj.tistory.com/87 스콧계정활성화하기 cmd 창에서... 여기서 데이터 열고 비교하면서 
// 작업해야 한다.

public class JDBCEx02 {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);//add catch만 한다.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("드라이버로딩실패");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DBMS 로그인 실패");
		}
		
		//내가 데이터에서 뽑아올 자료를 입력하는 sql변수
		String sql = "select * from emp";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//이 PreparedStatement 객체에서 SQL 쿼리를 실행하고 쿼리에 의해 생성 된 ResultSet 객체를 반환합니다.
			//반환한 것을 rs에 대입.
			
			
			while(rs.next()) {
				int empno =rs.getInt("empno");
				int mgr = rs.getInt("mgr");
				int sal = rs.getInt("sal");
				int comm = rs.getInt("comm");
				int deptno = rs.getInt("deptno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				Date hiredate = rs.getDate("hiredate");
				String hiredate1 = rs.getString("hiredate1");
				
				System.out.println("empno: "+empno);
				System.out.println("mgr: "+mgr);
				System.out.println("sal: "+sal);
				System.out.println("comm: "+comm); 
				//db내에서 comm값이 null인 경우가 있는데 이때 0으로 초기화해서 들어온다.
				System.out.println("deptno: "+deptno);
				System.out.println("ename: "+ename);
				System.out.println("job: "+job);
				System.out.println("hiredate: "+hiredate);
				System.out.println("hiredate1: "+hiredate1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
			
	}
}
