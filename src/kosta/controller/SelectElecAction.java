package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.env.ISourceMethod;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

@WebServlet("/select")
public class SelectElecAction implements Action {
	List<Electronics> list=null;
	ModelAndView mv=null;
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 실제로 발생시키는 예외는 직접 처리해줘야 한다. (사용자가 직접 발생시키는 예외)
		ElectronicsService service = new ElectronicsService();
		try {
			 list = service.selectAll();
			 
			 if (list.isEmpty()) {
				 mv.setPath("error.jsp");
			 }
			 request.setAttribute("list", list);		// forward 방식으로 이동
			  mv = new ModelAndView();
			  mv.setPath("elecView/list.jsp");
		} catch(Exception e) {e.printStackTrace();
										request.setAttribute("errorMsg", e.getMessage());
		}		// view페이지에 ${errorMsg}와 같게 이름을 설정해줘야 한다.
		
		return mv;
	}
}
