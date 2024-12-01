package main;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import constants.Cofiguration;
import control.CDirectory;
import control.CGangjwa;
import control.CLogin;
import control.CResult;
import control.CUser;
import remoteInterface.IDirectory;
import remoteInterface.IGangjwa;
import remoteInterface.ILogin;
import remoteInterface.IResult;
import remoteInterface.IUser;

public class Skeleton {
	private Registry registry;
	
	public Skeleton() throws RemoteException {
		this.registry = LocateRegistry.createRegistry(Cofiguration.PORT_NUM);
	}
	
	public void register(String objectName, Remote object) throws RemoteException, AlreadyBoundException {
		Remote remote = UnicastRemoteObject.exportObject(object, 0);
		this.registry.bind(objectName, remote);		
	}
	
	public void initialie() throws RemoteException, AlreadyBoundException {		
		this.register(ILogin.OBJECT_NAME, new CLogin());
		this.register(IUser.OBJECT_NAME, new CUser());
		this.register(IResult.OBJECT_NAME, new CResult());
		this.register(IDirectory.OBJECT_NAME, new CDirectory());
		this.register(IGangjwa.OBJECT_NAME, new CGangjwa());
	}

	public void run() {
	}

}
