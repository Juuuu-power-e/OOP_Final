package mainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Constants.ELoginDialog;
import control.CLogin;
import control.CUser;
import mainFrame.Main.ActionHandler;
import valueObject.VLogin;
import valueObject.VResult;
import valueObject.VUser;

public class PLoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	// components
	private JLabel nameLabel;
	private JTextField nameText;
	private JLabel passwordLabel;
	private JTextField passwordText;
	private JButton okButton;
	private JButton cancelButton;
	
	// Control
	private CLogin cLogin;
	private CUser cUser;
	
	public PLoginDialog(ActionHandler actionHandler) throws RemoteException, NotBoundException {
		this.setSize(ELoginDialog.width.getInt(), ELoginDialog.height.getInt());
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setLayout(new FlowLayout());
		
		JPanel line1 = new JPanel();
			this.nameLabel = new JLabel(ELoginDialog.nameLabel.getText());
			line1.add(this.nameLabel);		
			this.nameText = new JTextField();
			this.nameText.setColumns(ELoginDialog.sizeNameText.getInt());
			line1.add(this.nameText);
		this.add(line1, BorderLayout.NORTH);		
		JPanel line2 = new JPanel();		
			this.passwordLabel = new JLabel(ELoginDialog.passwordLabel.getText());
			line2.add(this.passwordLabel);		
			this.passwordText = new JTextField();
			this.passwordText.setColumns(ELoginDialog.sizePasswordText.getInt());
			line2.add(this.passwordText);
		this.add(line2, BorderLayout.CENTER);
		JPanel line3 = new JPanel();
			this.okButton = new JButton(ELoginDialog.okButtonLabel.getText());
			this.okButton.addActionListener(actionHandler);
			this.getRootPane().setDefaultButton(this.okButton);
			
			line3.add(this.okButton);			
			this.cancelButton = new JButton(ELoginDialog.cancelButtonLabel.getText());
			this.cancelButton.addActionListener(actionHandler);
			line3.add(this.cancelButton);
		this.add(line3, BorderLayout.SOUTH);	
		
		// create control
		this.cLogin = new CLogin();
		this.cUser = new CUser();
	}
	
	public void initialize() {
		this.setVisible(true);	
	}
	
	public VUser validateUser(Object eventSource) throws RemoteException {
		VUser vUser = null;
		if (eventSource.equals(this.okButton)) {
			String userId = this.nameText.getText();
			String password = this.passwordText.getText();
			VLogin vLogin= new VLogin();
			vLogin.set(userId, password);
			
			VResult vResult = this.cLogin.login(vLogin);
			if (vResult != null) { // �α��� ����
				vUser = this.cUser.getUser(userId);
			} else {
				// ���̵�� �н����尡 Ʋ��/����� ������ ���� ���� �ʴ� ��
				JOptionPane.showMessageDialog(this, ELoginDialog.loginFailed.getText());
			}
		} else if (eventSource.equals(this.cancelButton)) {
		}
		return vUser;
	}
}
