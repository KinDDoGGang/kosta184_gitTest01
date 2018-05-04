package kosta.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/elec")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Map<String, Action> map = null;
	ModelAndView mv = new ModelAndView();
	
	public void init(ServletConfig config) throws ServletException {
				// 어플리케이션 영역에 저장된 map을 꺼내서 전역변수에 저장하는 작업 필요
				map = (Map<String,Action>)config.getServletContext().getAttribute("map");
	}
	
	// 똑같은 기능을 하는 것 필요(분산)-Action 인터페이스
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// parameter로 넘어오는 값을 받아야 한다.
				String key =	request.getParameter("command");
				
				// key가 존재하지 않는다면 key를 list로 바꿔준다.
				// list가 default값 
				if (key==null) {
					key="list";
				}
				Action action = map.get(key);
				// map에서 key에 해당하는 클래스를 꺼내서 메소드를 호출하고 리턴값을 받는다.
				mv = action.execute(request, response);				
				
				// 리턴값(ModelAndView)에 따라 이동방식과 이동경로를 정한다.
				if (mv.isRedirect()) {
					response.sendRedirect(mv.getPath());
				} else {
					request.getRequestDispatcher(mv.getPath()).forward(request, response);
				}		//mv.getPath() 가 error.jsp 혹은 list.jsp로 이동하게 된다
		}
}
