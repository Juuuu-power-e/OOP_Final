package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import remoteInterface.IDirectory;
import valueObject.VDirectory;

public class CDirectory extends CControl implements IDirectory{
	
	private IDirectory iDirectory;

	public CDirectory() throws RemoteException, NotBoundException {
		super();
		this.iDirectory = (IDirectory) this.registry.lookup(IDirectory.OBJECT_NAME);
	}
	
	public Vector<VDirectory> getData(String fileName) throws RemoteException {
		
		Vector<VDirectory> vDrectories = iDirectory.getData(fileName);
		return vDrectories;
	}

}
