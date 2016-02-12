package chat;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
	public static void  main(String [] args){
		try {
			// Register the remote object in RMI registry with a given identifier 
			Registry registry = LocateRegistry.createRegistry(2020);
			
			//upload server's methods
			ServerImpl s = new ServerImpl();
			ServerItf s_skeleton = (ServerItf) UnicastRemoteObject.exportObject(s, 0);
			registry.bind("server", s_skeleton);
			
			System.out.println ("Server ready");
		} catch (Exception e)  {
			System.err.println("Error on server :" + e) ;
			e.printStackTrace();
		}
	}
}