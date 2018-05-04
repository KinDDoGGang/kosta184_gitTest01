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
		
		// 생성에 오류가 없으면 자동으로 save에 저장됨
		// fname, fsize는 자동으로 구해야한다.
		
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
				
				  throw new SQLException("입력값이 충분하지 않습니다.");
			  }
			 
			  	 System.out.println("변환 전 : " + description);
				 //description안에 태그를 문자로 변환!!!
				 description = description.replaceAll("<", "&lt;");
				 
				 System.out.println("변환 후 : " + description);
			 
			// file은 write.html의 name=file 인 것
			
			Electronics electronics = new Electronics(modelNum, modelName, price, description, password);
			
			if(m.getOriginalFileName("file")!=null) {		// 파일이 첨부되었다면
				electronics.setfName(m.getFilesystemName("file"));
				
				// DTO에 저장된 사이즈는 int형이므로 (int)형으로 다운캐스팅을 해줘야한다.
				electronics.setfSize((int)m.getFile("file").length());
			}
			
		// dto에 저장
		
				int re = ElectronicsService.insert(electronics);
				if (re > 0) {		// 등록이 되었다면
						mv.setPath("elec?command=list");		// command=list 생략가능, default가 list
						mv.setRedirect(true);		// 딱히 가저갈 것이 없으므로 redirect 방식으로 가야함
				}
		} catch (Exception e) { e.printStackTrace(); 
		
		// 에러페이지로 이동할 때 그냥 가면 안되고 errorMsg를 들고 가야한다.
											request.setAttribute("errorMsg", e.getMessage());
											mv.setPath("errorView/error.jsp");
		}
		return mv;
	}
}
