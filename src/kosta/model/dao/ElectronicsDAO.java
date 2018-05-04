package kosta.model.dao;

import java.sql.SQLException;
import java.util.List;

import kosta.model.dto.Electronics;

public interface ElectronicsDAO {
	 /**
	  * 레코드 전체 검색
	  * */
	  List<Electronics> selectAll() throws SQLException;
	  
  	 /**
	  * 모델번호에 해당하는 레코드 검색
	  * */
	  Electronics selectByModelNum(String modelNum) throws SQLException;
	  
	  /**
	   * 	상세보기를 클릭하였을 때에 조회수를 증가
	   * 	update electronics set readNum=readNum+1 where model_Num=?
	   * */
	  int updateByreadNum(String modelNum) throws SQLException;
	  
	/**
	 * 레코드 삽입
	 * @return : 1-삽입성공 , 0 - 삽입실패
	 * */
	  int insert(Electronics electronics) throws SQLException;
	  
	  /**
	   * 모델번호에 해당하는 레코드 삭제
	   * @return : 1-삭제성공 , 0 - 삭제실패
	   *  DELETE FROM ELECTRONICS WHERE MODEL_NUM=? AND PASSWORD=?
	   *  DB에서 쓰는 명칭과 동일하게 해줘야함
	   * */
	  int delete(String modelNum, String password) throws SQLException;
	  
	   /**
	    * 모델번호에 해당하는 레코드 수정(모델이름, 가격, 내용)
	    * @return : 1-수정성공 , 0 - 수정실패
	    * UPDATE ELECTRONICS 
	    * */
	  int update(Electronics electronics) throws SQLException;
}
