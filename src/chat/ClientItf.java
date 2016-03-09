package chat;
import java.rmi.*;

public interface ClientItf extends Remote {
	public void getMessage(String message) throws RemoteException;
        public void setPseudo(String nick) throws RemoteException;
}
