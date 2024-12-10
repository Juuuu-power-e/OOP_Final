package aspect;

import javax.swing.*;

public class ExceptionManager {
	
	public void process(Exception exception) {
		JOptionPane.showMessageDialog(null, exception);
		LogManager.getInstance().log(exception.getStackTrace());
		LogManager.getInstance().flushLogFile();
	}
}
