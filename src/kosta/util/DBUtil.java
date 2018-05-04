package kosta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 	DB관련 로드, 연결, 닫기를 위한 클래스
 * */

// 생성자 안에 작성하게 되면 호출시 바로 사용되지만 재사용이 안된다.
// 메소드를 만들면 재사용은 되지만 호출해주어야 함

public class DBUtil {
		static DataSource ds;
		
	/**
	 * 	로드
	 * Context.xml에서 이미 작성이 완료됨!
	 * 	하지만 context.xml과 연결은 해주어야 함
	 * */
	
		static {
			try {
				Context context	= new InitialContext();
			    ds = (DataSource)context.lookup("java:/comp/env/jdbc/myoracle");
				// Object로 만들어지므로 캐스팅해서 datasource에 넣어준다.
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 *  로드 연결 끝
		 * */
			public static  Connection getConnection() throws SQLException  {
					return ds.getConnection();
				
				// getConnection은 Connection을 리턴한다!!
				// 연결이 필요할 떄마다 생성해서 사용하는 것 보다는 static을 붙여서 생성 없이 호출해주는 것이 좋다.
					
			}
		
			/**
			 * 	닫기
			 * */
				
			/**
			 *  insert,update, delete인 경우
			 *   Statement만 써주어도 부모타입이므로 다형성!!
			 * */
			public  static void dbClose(Connection con, Statement st ) {
				/* 예외를 던지게 된다면 fianlly 블록 안에 try catch 구문이 중첩되어 사용되므로 예외를 처리해주는
				 것이 좋다. */
			
				try {
					if (st != null) st.close();				// 사용을 하였다면 닫아준다
					if (con != null )	con.close();			//  사용을 하였다면 닫아 준다
					
				} catch (SQLException e) {e.printStackTrace(); }
		}
			
			/**
			 * 	닫아 주어야 하는 곳에 select가 포함되어 있는 경우!
			 * */
			public  static void dbClose(Connection con, Statement st, ResultSet rs ) {
				try {
					if (rs != null) {
						rs.close();
						dbClose(con, st);			// 메소드 재사용
					}
				} catch (SQLException e) {e.printStackTrace(); }
			}
}




