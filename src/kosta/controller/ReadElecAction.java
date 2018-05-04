package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

@WebServlet("/detailView")
public class ReadElecAction implements Action {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ModelAndView mv = new ModelAndView();
		
		String flag = request.getParameter("flag");		// 수정이 완료된 후에 전달된다.
		boolean state = false;
		
		if (flag==null) {		// 전달이 되지 않았다면
			state=true;			// 수정이 완료된 상태가 아니라면, list에서 제목을 클릭했을때의 상황
		}
		
		// 모델번호 받기
		String modelNum = request.getParameter("modelNum");
		
		// read.jsp로 다음에 이동하게 되는데 거기에는 ${} 보면 저장할때 request값을 elec으로 저장하므로
		// elec이란 이름으로 저장해주어야 한다.
		// setAttribute로 저장을 해서 forward방식으로 보내줘야 하므로 isRedirect를 하지 않는다.
		
		try {
				Electronics elec = ElectronicsService.selectByModelNum(modelNum, state);
				request.setAttribute("elec", elec);
				mv.setPath("elecView/read.jsp");
		} catch (SQLException e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			mv.setPath("errorView/error.jsp");
			e.printStackTrace();
		}
		return mv;
	}
}
