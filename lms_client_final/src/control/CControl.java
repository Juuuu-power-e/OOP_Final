package control;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import constants.Configuration;

public class CControl {
	protected Registry registry;
	public CControl() throws RemoteException {
		this.registry = LocateRegistry.getRegistry(Configuration.PORT_NUM);
	}
}
