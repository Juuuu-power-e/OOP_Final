package control;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import aspect.LogManager;
import remoteInterface.IResult;
import valueObject.VGangjwa;

public class CResult extends CControl implements IResult{
	
	private IResult iResult;

	public CResult() throws RemoteException, NotBoundException {	
		super();
		this.iResult = (IResult) this.registry.lookup(IResult.OBJECT_NAME);
	}
	
	public void save(String fileName, Vector<VGangjwa> vGangjwas) throws RemoteException {
		try {
			iResult.save(fileName, vGangjwas);
		}catch (NoSuchObjectException e){
			LogManager.getInstance().log(e.getStackTrace());
		}
	}

	public Vector<VGangjwa> get(String fileName) throws RemoteException {
		Vector<VGangjwa> vGangjwas = iResult.get(fileName);
		return vGangjwas;
	}

}
