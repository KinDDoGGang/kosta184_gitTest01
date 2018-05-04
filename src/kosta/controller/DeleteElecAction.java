package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

@WebServlet("/delete")
public class DeleteElecAction implements Action {
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mv = new ModelAndView();
		mv.setPath("errorView/error.jsp");
		
		String modelNum = request.getParameter("modelNum");
		String password = request.getParameter("password");
		try{
			if(modelNum==null || password == null){
			   throw new SQLException("정보가 정확하지 않습니다.");	
			}
			//password확인
			Electronics dbElec=ElectronicsService.selectByModelNum(modelNum, false);//조회수증가안함
			
			if(dbElec.getPassword().equals(password)){//일치
				 if(ElectronicsService.delete(modelNum, password) > 0){//삭제성공
					 mv.setPath("elec?command=list");
					 mv.setRedirect(true);
				 }
			}else{//비번오류
				throw new SQLException("비밀번호 확인해주세요.");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
		}
       return mv;
	}
}



