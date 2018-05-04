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
		// ������ �߻���Ű�� ���ܴ� ���� ó������� �Ѵ�. (����ڰ� ���� �߻���Ű�� ����)
		ElectronicsService service = new ElectronicsService();
		try {
			 list = service.selectAll();
			 
			 if (list.isEmpty()) {
				 mv.setPath("error.jsp");
			 }
			 request.setAttribute("list", list);		// forward ������� �̵�
			  mv = new ModelAndView();
			  mv.setPath("elecView/list.jsp");
		} catch(Exception e) {e.printStackTrace();
										request.setAttribute("errorMsg", e.getMessage());
		}		// view�������� ${errorMsg}�� ���� �̸��� ��������� �Ѵ�.
		
		return mv;
	}
}
