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
			   throw new SQLException("������ ��Ȯ���� �ʽ��ϴ�.");	
			}
			//passwordȮ��
			Electronics dbElec=ElectronicsService.selectByModelNum(modelNum, false);//��ȸ����������
			
			if(dbElec.getPassword().equals(password)){//��ġ
				 if(ElectronicsService.delete(modelNum, password) > 0){//��������
					 mv.setPath("elec?command=list");
					 mv.setRedirect(true);
				 }
			}else{//�������
				throw new SQLException("��й�ȣ Ȯ�����ּ���.");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
		}
       return mv;
	}
}



