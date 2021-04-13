package d20200723;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	JButton jbtn1, jbtn2;
	JLabel jlb1, jlb2;
	JTextField jtf;
	JPasswordField jpf;
	
	public Login() {
		super("jdbc login test");
		setBounds(1100, 300, 400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		jbtn1 = new JButton("Login");
		jbtn2 = new JButton("register");
		
		jbtn1.setBounds(80, 200, 80, 40);
		jbtn2.setBounds(200, 200, 100, 40);
		
		add(jbtn1);
		add(jbtn2);
		
		jlb1 = new JLabel("id");
		jlb2 = new JLabel("pw");
		
		jlb1.setBounds(30, 60, 70, 40);
		jlb2.setBounds(30, 120, 70, 40);

		add(jlb1);
		add(jlb2);	
		
		jtf = new JTextField(30);
		jpf = new JPasswordField(30);
		
		jtf.setBounds(100, 60, 150, 40);
		jpf.setBounds(100, 130,	150, 40);
		
		add(jtf);
		add(jpf);
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);
		
		setVisible(true);
	}// 생성자 end
	
	public static void main(String[] args) {
		new Login();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		if(ob==jbtn1) {
			System.out.println("click");
			//사용자 입력을 받아오기
			String id = jtf.getText();
			String pw = jpf.getText(); //jpf.getPassword()이걸로 수정해보기
			
			System.out.println(id+" "+pw+"");
			
			//dbms에 접근해서 id, pw접근해서 일치하는지 확인해고
			// 1. 변수설정
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
				//3. connnection 생성
				conn=DriverManager.getConnection(url, user, password);
				
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("드라이버 로딩 실패");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("DBMS 연결 실패");
			}//conn end
			
			//4. sql문장 작성
//			SELECT * FROM LOGIN
//			WHERE ID = ? and Pw = ?
			StringBuffer sb = new StringBuffer();
			sb.append("select * from login ");
			sb.append("where id = ? and pw = ? ");
			
			//5.문장 객체 생성
			try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			//6.실행
			rs = pstmt.executeQuery();
			
			//System.out.println(rs.next());
			
			//간단한 회원 여부 check
			if(rs.next()) {
				System.out.println("존재하는 회원");
				System.out.println(rs.getString("name")+ "님 어서오세요");
			}else {
				System.out.println("회원가입하세요");				
			}
			
			}catch(SQLException e1) {
			e1.printStackTrace();
			}
		
			}else if(ob==jbtn2) {
			System.out.println("회원가입창으로 이동");
			}//if end

}
}

/*
 cmd창에서 테이블 만들어서 이클립스회원가입이랑 비교.
 스캇계정으로 로그인해서 들어가고 나서.
 아래 작업을 먼저하고 위 이클립스 작업을 진행하세요.(cmd창에서 commit은 대체 무엇인가??)
 
 SQL> CREATE TABLE LOGIN
  2  (ID VARCHAR2(20),
  3  PW VARCHAR2(10),
  4  NAME VARCHAR2(12));

Table created.

SQL> INSERT INTO LOGIN
  2  VALUES('hong', '1234', '홍길동');

1 row created.

SQL> select * from login
  2  ;

ID                                       PW
---------------------------------------- --------------------
NAME
------------------------
hong                                     1234
홍길동

SQL> commit;

Commit complete.
*/

