package sugangSincheong;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import constants.Constants.EPResultPanel;
import control.CResult;
import valueObject.VGangjwa;

public class PResultPanel extends PGangjwaContainer {
	private static final long serialVersionUID = 1L;

	private Vector<VGangjwa> vGangjwas;
	
	private JTable table;
	private DefaultTableModel tableModel;
	
	public PResultPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.table = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.table);
		this.add(scrollPane);
		
		Vector<String> header = new Vector<String>();
		for (EPResultPanel ePResultPanel: EPResultPanel.values()) {
			header.addElement(ePResultPanel.getText());
		}
		
		this.tableModel = new DefaultTableModel(header, 0);			
		this.table.setModel(tableModel);
	}

	public void initialize(String fileName) {
		CResult cResult = null;
		try {
			cResult = new CResult();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.vGangjwas = cResult.get(fileName);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.updateTableContents();
	}
	public void finish(String fileName) {
		CResult cResult = null;
		try {
			cResult = new CResult();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cResult.save(fileName, vGangjwas);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public Vector<VGangjwa> getGangjwas() {
		return this.vGangjwas;
	}
	
	public void addGangjwas(Vector<VGangjwa> vSelectedGangjwas) {
		this.vGangjwas.addAll(vSelectedGangjwas);
		this.updateTableContents();
	}
	
	public Vector<VGangjwa> removeSelectedGangjwas() {
		Vector<VGangjwa> removedGangjwas = new Vector<VGangjwa>();
		int[] indices = this.table.getSelectedRows();
		for (int i = indices.length-1; i>=0; i--) {
			VGangjwa removedGangjwa = this.vGangjwas.remove(indices[i]);
			removedGangjwas.add(removedGangjwa);
		}
		this.updateTableContents();
		return removedGangjwas;
	}
	
	private void updateTableContents() {
		this.tableModel.setRowCount(0);
		for (VGangjwa vGangjwa: this.vGangjwas) {
			Vector<String> row = new Vector<String>();
			row.add(vGangjwa.getId());
			row.add(vGangjwa.getName());
			this.tableModel.addRow(row);
		}
		if (this.vGangjwas.size() > 0) {
			this.table.setRowSelectionInterval(0, 0);
		}	
	}
}
