package sugangSincheong;

import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import constants.Constants.EPGangjwaSelectionPanel;
import constants.LanguageManager;
import control.CGangjwa;
import mainFrame.MyComponent;
import valueObject.VGangjwa;

public class PGangjwaSelectionPanel extends JTable implements MyComponent {
	private static final long serialVersionUID = 1L;

	private Vector<VGangjwa> vGangjwas;
	private DefaultTableModel tableModel;
	
	private String hakgwaFileName;
	private PResultPanel pMiridamgiPanel;
	private PResultPanel pSincheongPanel;

	public PGangjwaSelectionPanel(ListSelectionListener listSelectionHandler) {
		Vector<String> header = new Vector<String>();
		for (EPGangjwaSelectionPanel ePGangjwaSelectionPanel: EPGangjwaSelectionPanel.values()) {
			header.addElement(ePGangjwaSelectionPanel.getText());
		}
		
		this.tableModel = new DefaultTableModel(header, 0);
		this.setModel(tableModel);

		LanguageManager.getInstance().registerObserver(this);
	}

	public void intialize(String hakgwaFileName, PResultPanel pMiridamgiPanel, PResultPanel pSincheongPanel) {
		this.pMiridamgiPanel = pMiridamgiPanel;
		this.pSincheongPanel = pSincheongPanel;
		
		this.update(hakgwaFileName);
	}

	public void update(String hakgwaFileName) {
		this.hakgwaFileName = hakgwaFileName;
		
		CGangjwa cGangjwa = null;
		try {
			cGangjwa = new CGangjwa();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.vGangjwas = cGangjwa.getData(this.hakgwaFileName);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.removeDuplicatedGangjwas(this.pMiridamgiPanel.getGangjwas());
		this.removeDuplicatedGangjwas(this.pSincheongPanel.getGangjwas());
		
		this.updateTableContents();
	}
	
	public void update() {
		this.update(this.hakgwaFileName);
	}

	public Vector<VGangjwa> removeSelectedGangjwas() {
		int[] indices = this.getSelectedRows();
		Vector<VGangjwa> vSelectedGangjwas = new Vector<VGangjwa>();
		for (int index=indices.length-1; index>=0; index--) {
			vSelectedGangjwas.add(this.vGangjwas.get(index));
			this.vGangjwas.remove(index);
		}
		this.updateTableContents();
		return vSelectedGangjwas;
	}
	
	
	private void removeDuplicatedGangjwas(Vector<VGangjwa> vSelectedGangjwas) {
		for (VGangjwa vSelectedGangjwa : vSelectedGangjwas) {
			for (int i = this.vGangjwas.size() - 1; i >= 0; i--) {
				if (this.vGangjwas.get(i).getId().equals(vSelectedGangjwa.getId())) {
					this.vGangjwas.remove(i);
					break;
				}
			}
		}
	}
	
	private void updateTableContents() {
		this.tableModel.setRowCount(0);
		for (VGangjwa vGangjwa : this.vGangjwas) {
			Vector<String> row = new Vector<String>();
			row.add(vGangjwa.getId());
			row.add(vGangjwa.getName());
			row.add(vGangjwa.getLecturer());
			row.add(vGangjwa.getCredit());
			row.add(vGangjwa.getTime());
			this.tableModel.addRow(row);
		}
		if (this.vGangjwas.size() > 0) {
			this.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void updateText() {
		// 새로운 헤더 텍스트를 갱신
		Vector<String> header = new Vector<>();
		for (EPGangjwaSelectionPanel ePGangjwaSelectionPanel : EPGangjwaSelectionPanel.values()) {
			header.addElement(ePGangjwaSelectionPanel.getText());
		}

		// 기존 테이블 모델의 헤더를 교체
		this.tableModel.setColumnIdentifiers(header);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {

	}

	public Vector<VGangjwa> getResult() {
		return pSincheongPanel.getGangjwas();
	}
}
