package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import remoteInterface.IResult;
import valueObject.VGangjwa;

public class CResult extends CControl implements IResult{
	
	private IResult iResult;

	public CResult() throws RemoteException, NotBoundException {	
		super();
		this.iResult = (IResult) this.registry.lookup(IResult.OBJECT_NAME);
	}
	
	public void save(String fileName, Vector<VGangjwa> vGangjwas) throws RemoteException {
		iResult.save(fileName,vGangjwas);
	}

	public Vector<VGangjwa> get(String fileName) throws RemoteException {
		Vector<VGangjwa> vGangjwas = iResult.get(fileName);
		return vGangjwas;
	}

}
