package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;

public class ServerMain {

	private Skeleton skeleton;

	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	private ExceptionManager exceptionManager;


	public ServerMain() {
		exceptionManager = new ExceptionManager();
	}
	
	public void initialize() {
	}

	public void run() {
		try {
			this.skeleton = new Skeleton();
			this.skeleton.initialie();
			this.skeleton.run();
		} catch (RemoteException | AlreadyBoundException e) {
			exceptionManager.process(e);
		}

	}


	public void stop() {
		skeleton.stop();
	}

}
