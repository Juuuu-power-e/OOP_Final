package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import valueObject.VGangjwa;

public interface IGangjwa extends Remote{
	public final static String OBJECT_NAME = "CGANGJWA";

	Vector<VGangjwa> getData(String fileName)throws RemoteException;
	
}
