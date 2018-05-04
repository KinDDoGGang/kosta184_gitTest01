package kosta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;



public class UpdateElecAction implements Action {
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ModelAndView mv = new ModelAndView();
		mv.setPath("errorView/error.jsp");
		
		//�Ѿ���� ���� �ޱ�
		String modelNum = request.getParameter("modelNum");
		String modelName = request.getParameter("model_name");
		String price =  request.getParameter("price");
		String description = request.getParameter("description");
		String password = request.getParameter("password");
		
		
		//��ȿ�� �˻�
		try{
		  if(modelNum==null || modelName==null || price==null
				  || description==null ||password==null ){
			
			  throw new SQLException("�Է°��� ������� �ʽ��ϴ�.");
		  }
		  
		  //��й�ȣ üũ
		 Electronics dbElec = ElectronicsService.selectByModelNum(modelNum, false);
		 
		 if(dbElec.getPassword().equals(password)){//��ġ
			Electronics elec = new Electronics(modelNum, modelName, 
					    Integer.parseInt(price), description, password);
			 
			 if( ElectronicsService.update(elec) > 0){//�����Ϸ�
				 // commmand=detailView , modelNum=? , flag=?
				 mv.setPath("elec?command=detailView&flag=1&modelNum="+modelNum);
			 }
			 
		 }else{//�������
			 throw new SQLException("��й�ȣ �ٽ� Ȯ�����ּ���.");
		 }
		
		}catch(SQLException e){
			request.setAttribute("errorMsg", e.getMessage());
		}
		
      return mv;
	}
}



