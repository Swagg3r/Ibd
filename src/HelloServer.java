import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelloServer {
	public static void  main(String [] args){
		try {
			HelloImpl h = new HelloImpl("Hello world");
			HelloItf skeleton = (HelloItf) UnicastRemoteObject.exportObject(h, 0);
			
			Registry registry = LocateRegistry.createRegistry(2020);
			// Register the remote object in RMI registry with a given identifier
			registry.rebind("Hello1", skeleton); 
			
			System.out.println ("Server ready");
			
		} catch(Exception e){
	        System.err.println("Error on server :" + e); 
	        e.printStackTrace(); 
		}
	}
}