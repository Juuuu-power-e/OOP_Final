package valueObject;

public class VLogin extends VValueObject{
	
	private static final long serialVersionUID = 1L;
	
	private String userId; 
	private String password;
	
	public VLogin() {}


	public String getUserId() {
		return userId;
	}	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public void set(String userId, String password) {
		this.userId = userId;
		this.password = password;		
	}
}
