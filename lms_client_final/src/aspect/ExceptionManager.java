package aspect;

import javax.swing.*;

public class ExceptionManager {
	
	public void process(Exception exception) {
		JDialog dialog = new JDialog();
		dialog.setTitle("Error");
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		JOptionPane.showMessageDialog(null, exception);
	}
}
