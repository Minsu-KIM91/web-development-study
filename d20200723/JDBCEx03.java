package d20200723;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
 * 사용자로부터 입력받은 사원의 사번, 이름, 연봉 조회하기.
 */
public class JDBCEx03 {
	public static void main(String[] args) {
		//옵션패널 or 스캐너 사용 가능
		System.out.println("검색할 사원의 사번 입력");
		Scanner sc = new Scanner(System.in);
		
		int empno = sc.nextInt();
		System.out.println("empno: "+empno);
		
		//4. SQL문 작성. 
		//(60줄 또는 100줄 이상일 수도 그래서 편집상 위에 놓는게 편함(유일하세 바뀌는거니까?
		//그리고 엔터로 보기 편하게 해줘야함.)
		StringBuffer sql = new StringBuffer();
		sql.append("select empno, ename, sal*12 "); //스트링버퍼사용해서 append사용할거면 무조건 맨 뒤에 (공백)을 넣어야 한다.
		sql.append("from emp ");	//java.sql.SQLSyntaxErrorException: ORA-00923: FROM keyword not found where expected
		sql.append("where empno= ? "); // ? : 매번 바뀌는 값을 지정하는 곳.
		/* 위에서 표현하고자 한게 이거임.
		"select empno, ename, sal*12
		from emp
		where emp" = +empno 
		*/
		
		
		//1. 변수 설정
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//2. 드라이버 로딩
		try {
			Class.forName(driver);
		//3. Connention 객체 생성 -> I/O형태로 열렸다고 함.
			conn= DriverManager.getConnection(url, user, password);
			
			System.out.println("conn: "+conn);//제대로 연결됐는지 이거 써서 확인.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DBMS 로그인 실패");
		}
		
		//5. 문장 객체 생성
		/* 
		"select empno, ename, sal*12
		from emp
		where emp" = + ?
		이거롤 pstmt라는 그릇에 담는다 
		*/ 
		try {
			pstmt = conn.prepareStatement(sql.toString());
			//pstmt.setInt(parameterIndex, x); 파라미터인덱스는 순서. x는 보낼 거
			//나중에는 ?가 여러개 되기 때문에 이걸 파라미터를 통해 순서로 구분한다. 상하좌우
			pstmt.setInt(1, empno); 
			
			//6. 실행
			rs = pstmt.executeQuery();
			
			// 7. 로직처리.(앞 예제에는 레코드별 처리였는데 이번엔 그냥 로직처리다.)
			//굳이 while문을 돌릴 필요가 없기 때문.
			rs.next(); //컴퓨터한테 확인해! 라는 뜻.
			
			String name = rs.getString("ename");
			//표현식(연산식)을 사용하면 원테이블에 컬럼이 없으므로 그 표현식으 ㄹ다시 적어줘야 함.
			//단, 표현식이 길어지면 부담이 있으므로 별칭을 주고 그 별칭으로 받아오면 됨
			int annual = rs.getInt("sal*12"); 
			//칼럼이나 칼럼인덱스번호를 통해 가져와야하는데 가지고 있는 데이터에 그게없을 수 있다
			
			System.out.println(empno+ "\t"+name+"\t"+annual);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//8.자원반납 -> 메모리 잡아먹으니까 수행이 끝났으면 나가게 만들어놓음.
		finally {
			if(rs!=null) {//try안에 있는 것들을 다 수행했으면
				try {
					rs.close();
					if(pstmt!=null)pstmt.close();
					if(pstmt!=null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		

		
		
		
	}
}
