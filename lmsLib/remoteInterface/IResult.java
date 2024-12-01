package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import valueObject.VGangjwa;

public interface IResult extends Remote{
	public final static String OBJECT_NAME = "IRESULT";
	void save(String fileName, Vector<VGangjwa> vGangjwas)throws RemoteException;
	Vector<VGangjwa> get(String fileName)throws RemoteException;
}
