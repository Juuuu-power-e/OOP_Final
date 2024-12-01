package mainFrame;

import javax.swing.JMenuBar;

public class PMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private PFileMenu pFileMenu;
	private PEditMenu pEditMenu;
	
	public PMenuBar() {		
		this.pFileMenu = new PFileMenu();
		this.add(this.pFileMenu);
		
		this.pEditMenu = new PEditMenu();
		this.add(this.pEditMenu);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
