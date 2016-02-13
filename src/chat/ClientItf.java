package chat;
import java.rmi.*;

public interface ClientItf extends Remote {
	public String handleMessage(Person p, String m) throws RemoteException;
	public void getMessage(String message) throws RemoteException;
        public void envoiMessage(String message) throws RemoteException;
        public void deconnexion() throws RemoteException;
}
