package valueObject;

public class VUser extends VValueObject {
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String name;
	private String address;
	
	public VUser(String userId, String name, String address) {
		this.userId = userId;
		this.name = name;
		this.address = address;		
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

}
