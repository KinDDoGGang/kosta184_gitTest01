package kosta.controller;

public class ModelAndView {

	private String path;
	private boolean isRedirect=false;		// false이면 forward방식, true이면 redirect방식
	
	// 경우에 따라서 끌어오기도 하고 설정도 해줘야 하므로 setter, getter필요
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
