package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import valueObject.VDirectory;

public interface IDirectory extends Remote{

	String OBJECT_NAME = "CDIRECTORY";
	Vector<VDirectory> getData(String fileName) throws RemoteException;
	
}
