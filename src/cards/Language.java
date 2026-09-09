package cards;

public enum Language { 
	ENGLISH("en"), 
	ITALIAN("it");

	private  String code;
	
Language(String string) {
	this.code = string;
}

 public String  getCode() {
	 return this.code;
 }
}
 