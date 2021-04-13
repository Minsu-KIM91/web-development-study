package d20200723;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {
	public static void main(String[] args) {
		
		//자바에서 Oracle DB를 사용할 때 JDBC 라이브러리를 사용하게 되는데
		//오라클 사용자 계정에 대한 id, password 등. 그리고url이 필요하다.

		//mysql -> com.mysql.jdbc.Driver		
		String driver = "oracle.jdbc.driver.OracleDriver";//드라이버정보 대소문자구분해서써라.. 접근하는 주소
		//오라클에서 제공하는 드라이버를 쓰겠다는 의미.
		
		//mysql -> jdbc:mysql//localhost:3306/DBname (DBname은 이름을 지정해줘야하는 것)
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		//jdbc:oracle:thin은 사용하는 JDBC드라이버가 thin 타입이란 걸 의미한다. 
		//자바용 오라클 JDBC드라이버는 크게 두가지가 있는데 하나는 Java JDBC THIN 드라이버고, 다른 하나는 OCI기반의 드라이버다.

		//localhost는 Oracle DB가 설치되어 있는 서버의 IP인데 위 경우는 로컬에 설치되어 있다는 뜻이다.

		//1521 오라클포트번호/오라클 listener의 포트번호. 집마다 다르다
		
		///XE는 Oracle database client의 고유한 service name이다. 디폴트로 XE를 사용하므로 이 정보도 option이다. 이에 대한 설정 정보는 Oracle이 설치된 폴더 아래의 app\oracle\product\11.2.0\server\network\ADMIN\listener.ora 파일에 다음과 같이 표시되어 있다.
		//DEFAULT_SERVICE_LISTENER = (XE)


		String user = "scott"; //내 컴퓨터의 저장된 유저와 패스워드 정보를 입력.
		
		String password = "tiger";
		
		//Class.forName(driver); db,i/o는 중요한 거라 트라이캐치로 강제성을 부여해서 실행시킴...
		try {
			Class.forName(driver);
			try {
				Connection conn = //내 작업과 디비를 연결하는 실행문.
						DriverManager.getConnection(url, user, password);
						//위에 변수들과 일치시켜서 입력시킨 것.
						// 위 connection~ 명령만 쓰면 오류뜨는데 캐치만 눌러서 예외를 잡아주게 하면됨.
				
				//제대로 연결됐는지 확인
				System.out.println("conn:"+conn); 
				System.out.println("DBMS에 연결되었습니다.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 드라이버 정보를 올려서 객체화 시킴.
				
			
		
	}
}
