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
				// ���ø����̼� ������ ����� map�� ������ ���������� �����ϴ� �۾� �ʿ�
				map = (Map<String,Action>)config.getServletContext().getAttribute("map");
	}
	
	// �Ȱ��� ����� �ϴ� �� �ʿ�(�л�)-Action �������̽�
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// parameter�� �Ѿ���� ���� �޾ƾ� �Ѵ�.
				String key =	request.getParameter("command");
				
				// key�� �������� �ʴ´ٸ� key�� list�� �ٲ��ش�.
				// list�� default�� 
				if (key==null) {
					key="list";
				}
				Action action = map.get(key);
				// map���� key�� �ش��ϴ� Ŭ������ ������ �޼ҵ带 ȣ���ϰ� ���ϰ��� �޴´�.
				mv = action.execute(request, response);				
				
				// ���ϰ�(ModelAndView)�� ���� �̵���İ� �̵���θ� ���Ѵ�.
				if (mv.isRedirect()) {
					response.sendRedirect(mv.getPath());
				} else {
					request.getRequestDispatcher(mv.getPath()).forward(request, response);
				}		//mv.getPath() �� error.jsp Ȥ�� list.jsp�� �̵��ϰ� �ȴ�
		}
}
