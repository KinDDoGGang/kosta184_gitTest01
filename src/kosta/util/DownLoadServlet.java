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

@WebServlet("/downLoad")		// 등록이 끝남, url패턴, 다운로드 이름부분!!
public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);			// doPost방식을 재정의 해주었으므로 post 방식으로 들어와도 실행가능
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 넘어오는 파일의 이름을 받기
		String fName = request.getParameter("fileName");
		//2. 저장폴더의 실제 경로를 얻어오기
		String saveDir=request.getServletContext().getRealPath("/save");
		File file = new File(saveDir, fName);		// 업로드해야 하는 이름이 하나의 파일로 생성됨
		
		//부가적인 옵션!!!
		//요청된 파일의 mimeType을 설정한다(문서의 형태설정), 문서의 형식을 읽어오는 것
		// text, jpg, avi 인지 ~인지 읽어오는 것
		String mimeType = getServletContext().getMimeType(file.toString());
		
		if(mimeType==null){			// 문서의 형태를 읽어오지 못 한다면 binary 타입으로 읽어옴
			response.setContentType("application/octet-stream");
		}
		
		//브라우져 별 파일이름에대한 한글인코딩설정
		// request.getHeader("user-agent") 까지하면 브라우저의 종류가 무엇인지 알려줌
		// indexOf("Trident") == -1 IE 가 아닌 경우
		if (request.getHeader("user-agent").indexOf("Trident") == -1) {// IE가 아닌경우
			System.out.println(1);
			fName = new String(file.getName().getBytes("UTF-8"), "8859_1");	// IE가 아닌 경우 인코딩
		} else {
			System.out.println(2);
			fName = new String(file.getName().getBytes("euc-kr"), "8859_1");	// IE인 경우 인코딩
		}
		
		// 브라우져가 해석할수 있는 파일을 해석하지 않고 다운로드!!! [클릭하였을 경우 바로 다운로드 되도록]
		// attachment 바로 다운되도록 만들어주는 것
		response.setHeader("Content-Disposition", "attachment;filename=\""+ fName + "\";");
				
		//3. 폴더에서 파일이름에 해당하는 파일을 읽어서 
		//클라이언트 브라우져에서 다운로드(출력=쓰기)

		FileInputStream  fi = new FileInputStream(file);		// 파일을 읽어온다
		ServletOutputStream so = response.getOutputStream();		// 읽어온 파일을 쓰기한다.
			// 읽어온 것을 응답하여 보내주는 것 response 이용함
		
		byte b [] = new byte [1024];
	   
		int i=0;
		while((i = fi.read(b) ) != -1){		// inputStream으로 파일을 읽어
			so.write(b);								// outputStream으로 파일을 써준다.
		}
		so.flush();
		fi.close();
		so.close();
	}
}






