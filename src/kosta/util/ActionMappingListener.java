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
    	// ��ü���� ������ ~.properties ���Ͽ� �ֱ� ������ properties�� ���� �о�´�. (ResourceBundle�̿�)
    	// properties�� application ������ �����ϱ� ���ؼ� web.xml�� �̿��Ͽ� ������ ���ش�
    	// �̸� ��ü�� �����ؼ� map�� �����ϰ� �ٽ� application�� �����ϴ� �� (���� �ʱ�ȭ �۾��� �̷������ ��)
    	// ������ ���� �� �ʱ�ȭ�� �ǹǷ� 
    	
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
