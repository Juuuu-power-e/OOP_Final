package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;

public class Main {

	private Skeleton skeleton;
	private ExceptionManager exceptionManager;
	
	public Main() {
		try {
			this.skeleton = new Skeleton();
			this.exceptionManager = new ExceptionManager();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			exceptionManager.process(e);
		}
	}
	
	private void intialize() {
		try {
			this.skeleton.initialie();
		} catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			exceptionManager.process(e);
		}
	}

	private void run() {
		this.skeleton.run();
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.intialize();	
		main.run();
	}
}
