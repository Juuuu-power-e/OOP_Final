package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import valueObject.VLogin;
import valueObject.VResult;

public interface ILogin extends Remote {
	public final static String OBJECT_NAME = "CLOGIN";
	public VResult login(VLogin vLogin) throws RemoteException;
}
