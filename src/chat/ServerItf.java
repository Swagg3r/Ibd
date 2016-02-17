package chat;
import java.rmi.*;

public interface ServerItf extends Remote {
	public void sendMessage(Person p, String m) throws RemoteException;
	public void subscribe(Person p) throws RemoteException;
	public String leave(Person p) throws RemoteException;
        public int genId() throws RemoteException;
        public String whoIsHere() throws RemoteException;
        public String mp(String m, String nick) throws RemoteException;
}
