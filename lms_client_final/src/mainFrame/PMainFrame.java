package mainFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import constants.Constants.EMainFrame;
import sugangSincheong.PSugangSincheongPanel;
import valueObject.VUser;

public class PMainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;	
	// components	
	private PMenuBar pMenuBar;
	private PToolBar pToolBar;
	private PSugangSincheongPanel pSugangSincheongPanel;
	// utility
	private WindowListener windowsHandler;
	// value object
	private VUser vUser;
	
	// constructor
	public PMainFrame() {
		// attributes
		this.setSize(EMainFrame.width.getInt(), EMainFrame.height.getInt());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		this.setLayout(new BorderLayout());
		
		// utility
		this.windowsHandler = new WinodowsHandler();
		this.addWindowListener(this.windowsHandler);		
		
		// components
		this.pMenuBar = new PMenuBar();
		this.setJMenuBar(this.pMenuBar);		
		
		this.pToolBar = new PToolBar();
		this.add(this.pToolBar, BorderLayout.NORTH);
		
		this.pSugangSincheongPanel = new PSugangSincheongPanel();
		this.add(this.pSugangSincheongPanel, BorderLayout.CENTER);
	}

	public void initialize(VUser vUser) {
		this.vUser = vUser;
		this.setVisible(true);
		
		this.pMenuBar.initialize();
		this.pToolBar.initialize();
		this.pSugangSincheongPanel.initialize(this.vUser);
	}
	private void finish() {
		this.pSugangSincheongPanel.finish();
	}
	
	private class WinodowsHandler implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
		}
		
		@Override
		public void windowClosing(WindowEvent e) {			
			finish();
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
		}
		@Override
		public void windowIconified(WindowEvent e) {
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
		}
		@Override
		public void windowActivated(WindowEvent e) {
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
		}		
	}
}
