package mainFrame;

import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;

public class PToolBar extends JToolBar implements MyComponent{
	private static final long serialVersionUID = 1L;
	
	public PToolBar() {
		JButton button1 = new JButton("Test");
		this.add(button1);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateText() {

	}

	@Override
	public void setActionListener(ActionListener actionListener) {

	}
}
