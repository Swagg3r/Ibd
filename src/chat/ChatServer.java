package chat;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
	public static void  main(String [] args){
		try {
			// Register the remote object in RMI registry with a given identifier 
			Registry registryServer = LocateRegistry.createRegistry(2020);
			
			// Get remote object reference
			Registry registryClient = LocateRegistry.getRegistry(2021);
			
			//upload server's methods
			ServerImpl s = new ServerImpl(); 
			ServerItf s_skeleton = (ServerItf) UnicastRemoteObject.exportObject(s, 0); 
			registryServer.bind("server", s_skeleton);
			
			//download client's methods
			ClientItf s_stub = (ClientItf) registryClient.lookup("client");
			
			System.out.println ("Server ready"); 
		} catch (Exception e)  { 
			System.err.println("Error on server :" + e) ; 
			e.printStackTrace(); 
		}
		
		
	}
}