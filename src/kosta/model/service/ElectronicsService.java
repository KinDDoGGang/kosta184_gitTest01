package kosta.model.service;

import java.sql.SQLException;
import java.util.List;

import kosta.model.dao.ElectronicsDAO;
import kosta.model.dao.ElectronicsDAOImpl;
import kosta.model.dto.Electronics;

public class ElectronicsService {

														// ����Ͻ� ������ �ϴ� �κ�
	
	private static ElectronicsDAO electronicsDAO = new ElectronicsDAOImpl();
	
	/**
	 * ElectronicsDAOImpl�� ��緹�ڵ� �˻��ϴ� �޼ҵ� ȣ��
	 * */
		public static List<Electronics> selectAll() throws SQLException {
			List<Electronics> list = electronicsDAO.selectAll();
			/*if(list.size()==0 || list.isEmpty()) throw new SQLException("��ǰ�� ������ �����ϴ�.");*/
			// dao���� ������ ���ֹǷ� null �� �߻� �� ���� ����.
			return list;
		}
	  
	  /**
	   * ElectronicsDAOImpl�� ���ڵ� �����ϴ� �޼ҵ� ȣ��
	   * */
	 public static int insert(Electronics electronics) throws SQLException {
		 int result = electronicsDAO.insert(electronics);
		 
		 if (result==0) throw new SQLException("��ϵ��� �ʾҽ��ϴ�.");
		 
		 return result;
	 }
	  
	  /**
	   * ElectronicsDAOImpl�� �𵨹�ȣ�� �ش��ϴ� ���ڵ� �˻��ϴ� �޼ҵ� ȣ��
	   * @param : boolean flag - ��ȸ�� ���� ���θ� �Ǻ��ϴ� �Ű�������(true�̸� ��ȸ������ / false�̸� ��ȸ�� ���� ����)
	   * */
		public static Electronics selectByModelNum(String modelNum, boolean state) throws SQLException {
			Electronics electronics =new Electronics();
			
				// 	��ȸ���� ������Ű�� �󼼺��� �Խù� ��������
			if (state == true) {
				// ��ȸ�� ����
				if (electronicsDAO.updateByreadNum(modelNum) ==0) {
					throw new SQLException("��ȸ�� ���� ��Ű�µ� ����");
				}
			} 
					electronics = electronicsDAO.selectByModelNum(modelNum);
					if(electronics==null) {
						throw new SQLException(modelNum+"�� �ش��ϴ� ��ǰ ������ �����ϴ�.");
					}
			
				// ����, ���� ���� �� ������ ��ȸ���� ������ �� �����Ƿ� state�� �̿��Ͽ� ����
			return electronics;
		}
	  
	  /**
	   * ElectronicsDAOImpl�� �𵨹�ȣ�� �ش��ϴ� ���ڵ� ���� �޼ҵ� ȣ��
	   * */
	  public static int delete(String modelNum,String password) throws SQLException{
		  	int re = electronicsDAO.delete(modelNum, password);
		  	
		  	if (re==0) throw new SQLException("�������� �ʾҽ��ϴ�.");
		  
		  	return re; 
	  }
	  
	  /**
	   * ElectronicsDAOImpl�� �𵨹�ȣ�� �ش��ϴ� ���ڵ� ����  �޼ҵ� ȣ��
	   * */
	  public static int update(Electronics electronics) throws SQLException{
		  	int re = electronicsDAO.update(electronics);
		  	
		  	if (re==0) throw new SQLException("�������� �ʾҽ��ϴ�.");
		  
		  	return re; 
	  }
}



