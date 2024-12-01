package sugangSincheong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import constants.Constants.EConfigurations;
import valueObject.VGangjwa;
import valueObject.VUser;

public class PContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private VUser vUser;
	
	private ListSelectionListener listSelectionHandler;
	private PSelectionPanel pSelectionPanel;
	
	private PResultPanel pMiridamgiPanel;
	private PResultPanel pSincheongPanel;
	
	private ActionHandler actionHandler;
	private PControlPanel pControlPanel1;
	private PControlPanel pControlPanel2;
	
	
	public PContentPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));		
		
		this.listSelectionHandler = new ListSelectionHandler();
		this.pSelectionPanel = new PSelectionPanel(this.listSelectionHandler);
		this.add(this.pSelectionPanel);
		
		this.actionHandler = new ActionHandler();
		this.pControlPanel1 = new PControlPanel(this.actionHandler);
		this.add(this.pControlPanel1);		
		
		this.pMiridamgiPanel = new PResultPanel();
		this.add(pMiridamgiPanel);		
		
		this.pControlPanel2 = new PControlPanel(this.actionHandler);	
		this.add(this.pControlPanel2);
		
		this.pSincheongPanel = new PResultPanel();	
		this.add(pSincheongPanel);		
	}

	public void intialize(VUser vUser) {
		this.vUser = vUser;	
		
		this.pMiridamgiPanel.initialize(this.vUser.getUserId()+EConfigurations.miridamgiFilePostfix.getText());
		this.pSincheongPanel.initialize(this.vUser.getUserId()+EConfigurations.singcheongFilePostfix.getText());
		
		this.pSelectionPanel.initialize(this.pMiridamgiPanel, this.pSincheongPanel);
		
		this.pControlPanel1.initialize();
		this.pControlPanel2.initialize();
	}
	
	public void finish() {
		this.pMiridamgiPanel.finish(this.vUser.getUserId()+EConfigurations.miridamgiFilePostfix.getText());
		this.pSincheongPanel.finish(this.vUser.getUserId()+EConfigurations.singcheongFilePostfix.getText());
	}
	
	/////////////////////////////////////////////////////////////
	// Selection Listener: Gangjwa Table
	////////////////////////////////////////////////////////////
	public void update(Object source) {
		this.pSelectionPanel.update(source);
	}
	
	public class ListSelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent event) {
			update(event.getSource());
		}		
	}
	
	/////////////////////////////////////////////////////
	//  Action Listener: Move Buttons
	/////////////////////////////////////////////////////
	private void moveGangJwas(PGangjwaContainer source, PGangjwaContainer target) {
		Vector<VGangjwa> vSelectedGangjwas = source.removeSelectedGangjwas();
		target.addGangjwas(vSelectedGangjwas);
	}
	
	public class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(pControlPanel1.getMoveRightButton())) {
				moveGangJwas(pSelectionPanel, pMiridamgiPanel);			
			} else if (source.equals(pControlPanel1.getMoveLeftButton())) {
				moveGangJwas(pMiridamgiPanel, pSelectionPanel);			
			} else if (source.equals(pControlPanel2.getMoveRightButton())) {
				moveGangJwas(pMiridamgiPanel, pSincheongPanel);			
			} else if (source.equals(pControlPanel2.getMoveLeftButton())) {
				moveGangJwas(pSincheongPanel, pMiridamgiPanel);			
			}
		}		
	}


}
