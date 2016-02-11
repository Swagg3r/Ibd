package test;

import java.rmi.*;

public interface ClientInterface extends Remote {
	// A method provided by the remote object
	public void getMessage(String message) throws RemoteException;
}
