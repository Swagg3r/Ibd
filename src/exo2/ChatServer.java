package exo2;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
	public static void  main(String [] args){
	     try {
	    	// Create a Hello remote object
	    	HelloImpl h = new HelloImpl ("Hello world !"); 
	    	HelloItf h_skeleton = (HelloItf) UnicastRemoteObject.exportObject(h, 0); 
	    	// Create a Chat remote object
	    	ChatImpl s = new ChatImpl(); 
	    	ChatItf s_skeleton = (ChatItf) UnicastRemoteObject.exportObject(s, 0); 
	    	
	    	// Register the remote object in RMI registry with a given identifier 
	    	Registry registry = LocateRegistry.createRegistry(2020);
	    	
	    	registry.bind("Hello1", h_skeleton); 
	    	registry.bind("Chat1", s_skeleton);
	    	 System.out.println ("Server ready"); 
	    } catch (Exception e)  { 
	    	System.err.println("Error on server :" + e) ; 
	    	e.printStackTrace(); 
	    }
		
		
	}
}