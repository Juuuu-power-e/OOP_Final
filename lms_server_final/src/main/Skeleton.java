package main;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import aspect.LogManager;
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
	private LogManager logManager;
	
	public Skeleton() throws RemoteException {
		logManager = LogManager.getInstance();
		this.registry = LocateRegistry.createRegistry(Configuration.PORT_NUM);
		logManager.log("registry created: port " + Configuration.PORT_NUM);
	}
	
	public void register(String objectName, Remote object) throws RemoteException, AlreadyBoundException {
		Remote remote = UnicastRemoteObject.exportObject(object, 0);
		this.registry.bind(objectName, remote);
		logManager.log("registry bound: " + objectName);
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
		logManager.log("Skeleton started");
	}


	public void stop() {
		try {
			for (String name : registry.list()) {
				registry.unbind(name);
				logManager.log("Unbound: " + name);
			}
			UnicastRemoteObject.unexportObject(registry, true);
			logManager.log("Registry stopped.");
		} catch (Exception e) {
			System.err.println("Error during stop: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
