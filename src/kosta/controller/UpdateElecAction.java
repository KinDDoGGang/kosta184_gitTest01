package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;



public class UpdateElecAction implements Action {
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ModelAndView mv = new ModelAndView();
		mv.setPath("errorView/error.jsp");
		
		//넘어오는 값들 받기
		String modelNum = request.getParameter("modelNum");
		String modelName = request.getParameter("model_name");
		String price =  request.getParameter("price");
		String description = request.getParameter("description");
		String password = request.getParameter("password");
		
		
		//유효성 검사
		try{
		  if(modelNum==null || modelName==null || price==null
				  || description==null ||password==null ){
			
			  throw new SQLException("입력값이 충분하지 않습니다.");
		  }
		  
		  //비밀번호 체크
		 Electronics dbElec = ElectronicsService.selectByModelNum(modelNum, false);
		 
		 if(dbElec.getPassword().equals(password)){//일치
			Electronics elec = new Electronics(modelNum, modelName, 
					    Integer.parseInt(price), description, password);
			 
			 if( ElectronicsService.update(elec) > 0){//수정완료
				 // commmand=detailView , modelNum=? , flag=?
				 mv.setPath("elec?command=detailView&flag=1&modelNum="+modelNum);
			 }
			 
		 }else{//비번오류
			 throw new SQLException("비밀번호 다시 확인해주세요.");
		 }
		
		}catch(SQLException e){
			request.setAttribute("errorMsg", e.getMessage());
		}
		
      return mv;
	}
}



