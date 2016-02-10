package exo2;
import java.rmi.*;

public interface ChatItf extends Remote {
	public String sayHi(int c, HelloItf o, Person p) throws RemoteException; 
}
