package sugangSincheong;

import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants.EPHeaderPanel;
import constants.LanguageManager;
import mainFrame.MyComponent;
import valueObject.VUser;

import java.awt.event.ActionListener;

public class PHeaderPanel extends JPanel implements MyComponent {
	private static final long serialVersionUID = 1L;

	private JLabel wecomeLabel;
	private String userName;
	
	public PHeaderPanel() {
		this.wecomeLabel = new JLabel();
		this.add(this.wecomeLabel);
		LanguageManager.getInstance().registerObserver(this);
	}
	public void intialize(VUser vUser) {
		this.wecomeLabel.setText(vUser.getName()+EPHeaderPanel.greetings.getText());
		userName = vUser.getName();
	}

	@Override
	public void updateText() {
		this.wecomeLabel.setText(userName+EPHeaderPanel.greetings.getText());
	}

	@Override
	public void setActionListener(ActionListener actionListener) {

	}
}
