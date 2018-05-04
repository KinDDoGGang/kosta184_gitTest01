package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

@WebServlet("/insert")
public class InsertElecAction implements Action {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ������ ������ ������ �ڵ����� save�� �����
		// fname, fsize�� �ڵ����� ���ؾ��Ѵ�.
		
		 String saveDir = request.getSession().getServletContext().getRealPath("/save/");
		 System.out.println(saveDir);
		 ModelAndView mv = new ModelAndView();
			int maxSize=1024 * 1024 * 100;
			String encoding="utf-8";
		try {
			MultipartRequest m = new MultipartRequest(request, saveDir , maxSize ,encoding , new DefaultFileRenamePolicy());
			
			String modelNum = m.getParameter("model_num");
			String modelName = m.getParameter("model_name");
			int price = Integer.parseInt(m.getParameter("price"));
			String description = m.getParameter("description");
			String password = m.getParameter("password");
			
			 if(modelNum==null || modelName==null || price==0
					  || description==null ||password==null ){
				
				  throw new SQLException("�Է°��� ������� �ʽ��ϴ�.");
			  }
			 
			  	 System.out.println("��ȯ �� : " + description);
				 //description�ȿ� �±׸� ���ڷ� ��ȯ!!!
				 description = description.replaceAll("<", "&lt;");
				 
				 System.out.println("��ȯ �� : " + description);
			 
			// file�� write.html�� name=file �� ��
			
			Electronics electronics = new Electronics(modelNum, modelName, price, description, password);
			
			if(m.getOriginalFileName("file")!=null) {		// ������ ÷�εǾ��ٸ�
				electronics.setfName(m.getFilesystemName("file"));
				
				// DTO�� ����� ������� int���̹Ƿ� (int)������ �ٿ�ĳ������ ������Ѵ�.
				electronics.setfSize((int)m.getFile("file").length());
			}
			
		// dto�� ����
		
				int re = ElectronicsService.insert(electronics);
				if (re > 0) {		// ����� �Ǿ��ٸ�
						mv.setPath("elec?command=list");		// command=list ��������, default�� list
						mv.setRedirect(true);		// ���� ������ ���� �����Ƿ� redirect ������� ������
				}
		} catch (Exception e) { e.printStackTrace(); 
		
		// ������������ �̵��� �� �׳� ���� �ȵǰ� errorMsg�� ��� �����Ѵ�.
											request.setAttribute("errorMsg", e.getMessage());
											mv.setPath("errorView/error.jsp");
		}
		return mv;
	}
}
