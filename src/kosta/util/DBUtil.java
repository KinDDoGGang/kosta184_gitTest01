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
 * 	DB���� �ε�, ����, �ݱ⸦ ���� Ŭ����
 * */

// ������ �ȿ� �ۼ��ϰ� �Ǹ� ȣ��� �ٷ� �������� ������ �ȵȴ�.
// �޼ҵ带 ����� ������ ������ ȣ�����־�� ��

public class DBUtil {
		static DataSource ds;
		
	/**
	 * 	�ε�
	 * Context.xml���� �̹� �ۼ��� �Ϸ��!
	 * 	������ context.xml�� ������ ���־�� ��
	 * */
	
		static {
			try {
				Context context	= new InitialContext();
			    ds = (DataSource)context.lookup("java:/comp/env/jdbc/myoracle");
				// Object�� ��������Ƿ� ĳ�����ؼ� datasource�� �־��ش�.
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 *  �ε� ���� ��
		 * */
			public static  Connection getConnection() throws SQLException  {
					return ds.getConnection();
				
				// getConnection�� Connection�� �����Ѵ�!!
				// ������ �ʿ��� ������ �����ؼ� ����ϴ� �� ���ٴ� static�� �ٿ��� ���� ���� ȣ�����ִ� ���� ����.
					
			}
		
			/**
			 * 	�ݱ�
			 * */
				
			/**
			 *  insert,update, delete�� ���
			 *   Statement�� ���־ �θ�Ÿ���̹Ƿ� ������!!
			 * */
			public  static void dbClose(Connection con, Statement st ) {
				/* ���ܸ� ������ �ȴٸ� fianlly ��� �ȿ� try catch ������ ��ø�Ǿ� ���ǹǷ� ���ܸ� ó�����ִ�
				 ���� ����. */
			
				try {
					if (st != null) st.close();				// ����� �Ͽ��ٸ� �ݾ��ش�
					if (con != null )	con.close();			//  ����� �Ͽ��ٸ� �ݾ� �ش�
					
				} catch (SQLException e) {e.printStackTrace(); }
		}
			
			/**
			 * 	�ݾ� �־�� �ϴ� ���� select�� ���ԵǾ� �ִ� ���!
			 * */
			public  static void dbClose(Connection con, Statement st, ResultSet rs ) {
				try {
					if (rs != null) {
						rs.close();
						dbClose(con, st);			// �޼ҵ� ����
					}
				} catch (SQLException e) {e.printStackTrace(); }
			}
}




