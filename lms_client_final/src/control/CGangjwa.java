package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import remoteInterface.IGangjwa;
import valueObject.VGangjwa;

public class CGangjwa extends CControl implements IGangjwa{
	
	private IGangjwa iGangjwa;
	
	public CGangjwa() throws RemoteException, NotBoundException {
		super();
		this.iGangjwa = (IGangjwa) this.registry.lookup(IGangjwa.OBJECT_NAME);
	}
	
	public Vector<VGangjwa> getData(String fileName) throws RemoteException {
		Vector<VGangjwa> vGangjwas = iGangjwa.getData(fileName);
		return vGangjwas;
	}

}
