package exo2;
import java.rmi.*;

public interface HelloItf extends Remote {
	public String sayHello() throws RemoteException;
}
