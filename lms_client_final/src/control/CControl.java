package control;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import constants.Cofiguration;

public class CControl {
	protected Registry registry;
	public CControl() throws RemoteException {
		this.registry = LocateRegistry.getRegistry(Cofiguration.PORT_NUM);
	}
}
