package chat;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
	public static void  main(String [] args){
	     try {
	    	// Create a Chat remote object
	    	ServerImpl s = new ServerImpl(); 
	    	ServerItf s_skeleton = (ServerItf) UnicastRemoteObject.exportObject(s, 0); 
	    	
	    	// Register the remote object in RMI registry with a given identifier 
	    	Registry registry = LocateRegistry.createRegistry(2020);
	    	
	    	registry.bind("Chat1", s_skeleton);
	    	System.out.println ("Server ready"); 
	    } catch (Exception e)  { 
	    	System.err.println("Error on server :" + e) ; 
	    	e.printStackTrace(); 
	    }
		
		
	}
}