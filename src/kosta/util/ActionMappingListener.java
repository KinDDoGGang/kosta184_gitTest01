package kosta.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import kosta.controller.Action;

@WebListener
public class ActionMappingListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent e)  { 
    	
    	
    }

    public void contextInitialized(ServletContextEvent e)  { 
    	// 객체들의 정보는 ~.properties 파일에 있기 때문에 properties을 먼저 읽어온다. (ResourceBundle이용)
    	// properties를 application 영역에 저장하기 위해서 web.xml을 이용하여 저장을 해준다
    	// 미리 객체를 생성해서 map에 저장하고 다시 application에 저장하는 곳 (사전 초기화 작업이 이루어지는 곳)
    	// 서버가 켜질 때 초기화가 되므로 
    	
    	Map<String,Action> map = new HashMap<>();
    	ServletContext application = e.getServletContext();
    	String fileName = application.getInitParameter("fileName");
    	
    	ResourceBundle rb = ResourceBundle.getBundle(fileName);
    	Iterator<String> it = rb.keySet().iterator();
    	
    	while(it.hasNext()) {
    		String key = it.next();
    		String className = rb.getString(key);
    		try {
    				Action action = (Action)Class.forName(className).newInstance();
    				map.put(key, action);
    				
    		} catch (Exception ex) {ex.printStackTrace();}
    			application.setAttribute("map", map);
    	}
    }
}
