package test;
import java.rmi.*;

public interface ServerInterface extends Remote {
	// A method provided by the remote object
	public String getNewClientId() throws RemoteException;
	public boolean addClient(String pseudo, String pass, String clientid)  throws RemoteException;
	public void sendMessage(String pseudo, String message) throws RemoteException;
	public void removeClient(String pseudo, String clientid) throws RemoteException;
}
