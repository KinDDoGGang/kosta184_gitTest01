package kosta.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downLoad")		// ����� ����, url����, �ٿ�ε� �̸��κ�!!
public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);			// doPost����� ������ ���־����Ƿ� post ������� ���͵� ���డ��
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. �Ѿ���� ������ �̸��� �ޱ�
		String fName = request.getParameter("fileName");
		//2. ���������� ���� ��θ� ������
		String saveDir=request.getServletContext().getRealPath("/save");
		File file = new File(saveDir, fName);		// ���ε��ؾ� �ϴ� �̸��� �ϳ��� ���Ϸ� ������
		
		//�ΰ����� �ɼ�!!!
		//��û�� ������ mimeType�� �����Ѵ�(������ ���¼���), ������ ������ �о���� ��
		// text, jpg, avi ���� ~���� �о���� ��
		String mimeType = getServletContext().getMimeType(file.toString());
		
		if(mimeType==null){			// ������ ���¸� �о���� �� �Ѵٸ� binary Ÿ������ �о��
			response.setContentType("application/octet-stream");
		}
		
		//������ �� �����̸������� �ѱ����ڵ�����
		// request.getHeader("user-agent") �����ϸ� �������� ������ �������� �˷���
		// indexOf("Trident") == -1 IE �� �ƴ� ���
		if (request.getHeader("user-agent").indexOf("Trident") == -1) {// IE�� �ƴѰ��
			System.out.println(1);
			fName = new String(file.getName().getBytes("UTF-8"), "8859_1");	// IE�� �ƴ� ��� ���ڵ�
		} else {
			System.out.println(2);
			fName = new String(file.getName().getBytes("euc-kr"), "8859_1");	// IE�� ��� ���ڵ�
		}
		
		// �������� �ؼ��Ҽ� �ִ� ������ �ؼ����� �ʰ� �ٿ�ε�!!! [Ŭ���Ͽ��� ��� �ٷ� �ٿ�ε� �ǵ���]
		// attachment �ٷ� �ٿ�ǵ��� ������ִ� ��
		response.setHeader("Content-Disposition", "attachment;filename=\""+ fName + "\";");
				
		//3. �������� �����̸��� �ش��ϴ� ������ �о 
		//Ŭ���̾�Ʈ ���������� �ٿ�ε�(���=����)

		FileInputStream  fi = new FileInputStream(file);		// ������ �о�´�
		ServletOutputStream so = response.getOutputStream();		// �о�� ������ �����Ѵ�.
			// �о�� ���� �����Ͽ� �����ִ� �� response �̿���
		
		byte b [] = new byte [1024];
	   
		int i=0;
		while((i = fi.read(b) ) != -1){		// inputStream���� ������ �о�
			so.write(b);								// outputStream���� ������ ���ش�.
		}
		so.flush();
		fi.close();
		so.close();
	}
}






