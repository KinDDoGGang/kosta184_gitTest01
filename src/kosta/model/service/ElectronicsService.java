package kosta.model.service;

import java.sql.SQLException;
import java.util.List;

import kosta.model.dao.ElectronicsDAO;
import kosta.model.dao.ElectronicsDAOImpl;
import kosta.model.dto.Electronics;

public class ElectronicsService {

														// 비즈니스 로직을 하는 부분
	
	private static ElectronicsDAO electronicsDAO = new ElectronicsDAOImpl();
	
	/**
	 * ElectronicsDAOImpl의 모든레코드 검색하는 메소드 호출
	 * */
		public static List<Electronics> selectAll() throws SQLException {
			List<Electronics> list = electronicsDAO.selectAll();
			/*if(list.size()==0 || list.isEmpty()) throw new SQLException("상품의 정보가 없습니다.");*/
			// dao에서 생성을 해주므로 null 이 발생 할 수는 없다.
			return list;
		}
	  
	  /**
	   * ElectronicsDAOImpl의 레코드 삽입하는 메소드 호출
	   * */
	 public static int insert(Electronics electronics) throws SQLException {
		 int result = electronicsDAO.insert(electronics);
		 
		 if (result==0) throw new SQLException("등록되지 않았습니다.");
		 
		 return result;
	 }
	  
	  /**
	   * ElectronicsDAOImpl의 모델번호에 해당하는 레코드 검색하는 메소드 호출
	   * @param : boolean flag - 조회수 증가 여부를 판별하는 매개변수임(true이면 조회수증가 / false이면 조회수 증가 안함)
	   * */
		public static Electronics selectByModelNum(String modelNum, boolean state) throws SQLException {
			Electronics electronics =new Electronics();
			
				// 	조회수를 증가시키고 상세보기 게시물 가져오기
			if (state == true) {
				// 조회수 증가
				if (electronicsDAO.updateByreadNum(modelNum) ==0) {
					throw new SQLException("조회수 증가 시키는데 문제");
				}
			} 
					electronics = electronicsDAO.selectByModelNum(modelNum);
					if(electronics==null) {
						throw new SQLException(modelNum+"에 해당하는 상품 정보는 없습니다.");
					}
			
				// 수정, 삭제 등을 할 때에도 조회수가 증가될 수 있으므로 state를 이용하여 방지
			return electronics;
		}
	  
	  /**
	   * ElectronicsDAOImpl의 모델번호에 해당하는 레코드 삭제 메소드 호출
	   * */
	  public static int delete(String modelNum,String password) throws SQLException{
		  	int re = electronicsDAO.delete(modelNum, password);
		  	
		  	if (re==0) throw new SQLException("삭제되지 않았습니다.");
		  
		  	return re; 
	  }
	  
	  /**
	   * ElectronicsDAOImpl의 모델번호에 해당하는 레코드 수정  메소드 호출
	   * */
	  public static int update(Electronics electronics) throws SQLException{
		  	int re = electronicsDAO.update(electronics);
		  	
		  	if (re==0) throw new SQLException("수정되지 않았습니다.");
		  
		  	return re; 
	  }
}



