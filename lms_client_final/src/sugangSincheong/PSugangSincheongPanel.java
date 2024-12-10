package sugangSincheong;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;

import constants.LanguageManager;
import mainFrame.MyComponent;
import valueObject.VGangjwa;
import valueObject.VUser;

public class PSugangSincheongPanel extends JPanel implements MyComponent {
	private static final long serialVersionUID = 1L;
	
	private PHeaderPanel pHeaderPanel;
	private PContentPanel pContentPanel;
	private PFooterPanel pFooterPanel;
	
	public PSugangSincheongPanel() {
		this.setLayout(new BorderLayout());
		
		this.pHeaderPanel = new PHeaderPanel();
		this.add(this.pHeaderPanel, BorderLayout.NORTH);
		
		this.pContentPanel = new PContentPanel();
		this.add(this.pContentPanel, BorderLayout.CENTER);
		
		this.pFooterPanel = new PFooterPanel();
		this.add(this.pFooterPanel, BorderLayout.SOUTH);

	}

	public void initialize(VUser vUser) {
		this.pHeaderPanel.intialize(vUser);
		this.pContentPanel.intialize(vUser);		
		this.pFooterPanel.intialize();		
	}

	public void finish() {
		this.pContentPanel.finish();
	}

	@Override
	public void updateText() {

	}

	@Override
	public void setActionListener(ActionListener actionListener) {

	}

	public Vector<VGangjwa> getResult() {
		return pContentPanel.getResult();
	}
}
