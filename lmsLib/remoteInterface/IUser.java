package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import valueObject.VLogin;
import valueObject.VResult;
import valueObject.VUser;

public interface IUser extends Remote {
	public final static String OBJECT_NAME = "CUSER";
	public VUser getUser(String userId) throws RemoteException;
}
