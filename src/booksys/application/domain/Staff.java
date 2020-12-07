package booksys.application.domain;

public class Staff {

	private String id;
	private String password;
	private String name;
	private String check;
	
	public Staff(String id, String password, String name, String check) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.check = check;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCheck() {
		return check;
	}
	
}
