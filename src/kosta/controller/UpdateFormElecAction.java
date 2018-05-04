package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

@WebServlet("/updateForm")
public class UpdateFormElecAction implements Action {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ModelAndView mv = new ModelAndView();
		mv.setPath("errorView/error.jsp");
		
		String modelNum = request.getParameter("modelNum");
		
		try {
		if (modelNum==null) {
			throw new SQLException("ModelNum이 없습니다.");
		}
		
		Electronics elec=ElectronicsService.selectByModelNum(modelNum, false);	// 조회수 증가 방지
		if (elec==null) {
			throw new SQLException("해당하는 상품이 없습니다.");
		} else {
			request.setAttribute("elec", elec);
			mv.setPath("elecView/update.jsp");
		}
		
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
		}
		return mv;
	}
}
