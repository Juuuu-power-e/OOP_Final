package aspect;


public class ExceptionManager {
	
	public void process(Exception e) {
		LogManager.getInstance().log(e.getStackTrace());
		LogManager.getInstance().log(e.getMessage());
		LogManager.getInstance().flushLogFile();
	}
}
