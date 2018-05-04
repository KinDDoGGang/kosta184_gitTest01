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
		
		String flag = request.getParameter("flag");		// ������ �Ϸ�� �Ŀ� ���޵ȴ�.
		boolean state = false;
		
		if (flag==null) {		// ������ ���� �ʾҴٸ�
			state=true;			// ������ �Ϸ�� ���°� �ƴ϶��, list���� ������ Ŭ���������� ��Ȳ
		}
		
		// �𵨹�ȣ �ޱ�
		String modelNum = request.getParameter("modelNum");
		
		// read.jsp�� ������ �̵��ϰ� �Ǵµ� �ű⿡�� ${} ���� �����Ҷ� request���� elec���� �����ϹǷ�
		// elec�̶� �̸����� �������־�� �Ѵ�.
		// setAttribute�� ������ �ؼ� forward������� ������� �ϹǷ� isRedirect�� ���� �ʴ´�.
		
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
