package sugangSincheong;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton moveRightButton;
	private JButton moveLeftButton;
	
	public PControlPanel(ActionListener actionHandler) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.moveRightButton = new JButton(">>");
		this.moveRightButton.addActionListener(actionHandler);
		this.add(this.moveRightButton);
		
		this.moveLeftButton = new JButton("<<");
		this.moveLeftButton.addActionListener(actionHandler);
		this.add(this.moveLeftButton);
	}

	public void initialize() {
	}

	public Object getMoveRightButton() {
		return this.moveRightButton;
	}

	public Object getMoveLeftButton() {
		return this.moveLeftButton;
	}
}
