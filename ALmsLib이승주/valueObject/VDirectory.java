package valueObject;

public class VDirectory extends VValueObject {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String fileName;

	public VDirectory(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	public String getName() {
		return name;
	}
	public String getFileName() {
		return fileName;
	}	
}
