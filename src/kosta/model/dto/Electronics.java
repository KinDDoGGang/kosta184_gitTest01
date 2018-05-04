package kosta.model.dto;

// DB에는 필드명에 _가 있지만 DTO에는 존재x (카멜표기법)
public class Electronics {
	private String modelNum;
	private String modelName ;
	private int price ;
	private String description; 
	private String password ;
	private String writeday; 
	private int readnum ;
	private String  fName; 
	private int  fSize;
	
	
	// 생성자 오버로딩
	public Electronics(){}
	
	//삭제할때..
	public Electronics(String modelNum, String password) {
		this.modelNum = modelNum;
		this.password = password;
	}
	
	//업로드 안될경우 생성자
	public Electronics(String modelNum, String modelName, int price,
			String description, String password) {
		this(modelNum, password);
		this.modelName = modelName;
		this.price = price;
		this.description = description;
	}

	//업로드가 될때
	public Electronics(String modelNum, String modelName, int price,
			String description, String password, String writeday, int readnum,
			String fName, int fSize) {
		this(modelNum,modelName,price,description,password);
		this.writeday = writeday;
		this.readnum = readnum;
		this.fName = fName;
		this.fSize = fSize;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWriteday() {
		return writeday;
	}

	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}

	public int getReadnum() {
		return readnum;
	}

	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public int getfSize() {
		return fSize;
	}

	public void setfSize(int fSize) {
		this.fSize = fSize;
	}
}
