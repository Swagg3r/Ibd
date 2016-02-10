package chat;
import java.rmi.*;

public interface ServerItf extends Remote {
	public String sendMessage(Person p, String m) throws RemoteException;
	public void subscribe(Person p) throws RemoteException;
	public void leave(Person p) throws RemoteException;
	public void recupMessage(Person p) throws RemoteException;
}
