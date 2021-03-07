package d20200723;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCEx01 {
	public static void main(String[] args) {
		//0. 변수를 설정
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//1. 드라이버 로딩
		try {
			Class.forName(driver);
		//2. Connection 객체 생성
				conn = DriverManager.getConnection(url, user, password);
				System.out.println("conn: "+conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DBMS 로그인 실패");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();		
			System.out.println("드라이버로딩실패");
		}
			
		//3. 사용할 sql 문장 작성
		String sql = "select * from dept";
		
		//4. 문장 객체 선언
		try {
			pstmt = conn.prepareStatement(sql);
		//5. 실행
			//DBMS가 실행 후 결과값을 리턴해주면 되돌려 받을 자바 객체가 필요함. - ResultSet
			rs = pstmt.executeQuery();
		
		//6. 레코드별로 로직 처리
			//실질적으로 몇개인지 모르니까 while문으로 받는다 (반복문) 레코드별로 하나ㅣㄱ 가져오니까.
			//하나받아서 처리하고 또 하나 받아 처리하고....
			while(rs.next()) {
				int deptno = rs.getInt("deptno");//받아올 때 해당 데이터 타입에 맞춰서 변화해 가져와야 한다.
				// ""안에 입력한 건 내가 가져올 데이터의 카테고리를 그냥 표시한 거임.
				String dname = rs.getString("dname");
				String loc = rs.getString("loc");
				
				System.out.println("부서번호 : "+deptno);
				System.out.println("부서이름 : "+dname);
				System.out.println("부서위치 : "+loc);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}// 7. 자원 반납 -> 끝났으면 닫아주는게 권장사항. 메모리를 계속 잡아먹어서 그럼.
		finally {
			//에러가 생기든 말든 여기 안에 내용을 진행해라. try catch finally에서 finally의 역할.
			//열린 순서 역순으로 닫아줘야함.
			//rs.close(); //시스템에 영향을 주니까 트라이 캐치하라고 안내해줌.
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
		
		
	



