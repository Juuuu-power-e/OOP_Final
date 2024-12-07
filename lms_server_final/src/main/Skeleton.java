package main;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import constants.Configuration;
import control.CDirectory;
import control.CGangjwa;
import control.CLogin;
import control.CResult;
import control.CUser;
import model.Dao;
import model.DaoFile;
import remoteInterface.IDirectory;
import remoteInterface.IGangjwa;
import remoteInterface.ILogin;
import remoteInterface.IResult;
import remoteInterface.IUser;

public class Skeleton {
	private Registry registry;
	
	public Skeleton() throws RemoteException {
		this.registry = LocateRegistry.createRegistry(Configuration.PORT_NUM);
	}
	
	public void register(String objectName, Remote object) throws RemoteException, AlreadyBoundException {
		Remote remote = UnicastRemoteObject.exportObject(object, 0);
		this.registry.bind(objectName, remote);		
	}
	
	public void initialie() throws RemoteException, AlreadyBoundException {
		Dao dao = new DaoFile();
		this.register(ILogin.OBJECT_NAME, new CLogin(dao));
		this.register(IUser.OBJECT_NAME, new CUser(dao));
		this.register(IResult.OBJECT_NAME, new CResult(dao));
		this.register(IDirectory.OBJECT_NAME, new CDirectory(dao));
		this.register(IGangjwa.OBJECT_NAME, new CGangjwa(dao));
	}

	public void run() {
	}

}
