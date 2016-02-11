package chat;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner; 

public class ChatClient { 
 	public static void main(String [] args) { 
 		try { 
		  if (args.length < 1) { 
			  System.out.println("Usage: java HelloClient <server host>"); 
			  return;
		  }
		  String host = args[0];
		  
		  // Register the remote object in RMI registry with a given identifier 
		  Registry registryClient = LocateRegistry.createRegistry(2021);
		  
		  // Get remote object reference
		  Registry registryServer = LocateRegistry.getRegistry(Integer.parseInt(host)); 
		  
		  //upload client's methods
		  ClientImpl c = new ClientImpl(); 
		  ClientItf c_skeleton = (ClientItf) UnicastRemoteObject.exportObject(c, 0); 
		  registryServer.bind("client", c_skeleton); 
		  
		  //download server's methods
		  ServerItf c_stub = (ServerItf) registryServer.lookup("server"); 
		  
		  // Person creation
		  System.out.println("Choisissez votre pseudo");
		  Scanner sc = new Scanner(System.in);
		  String str = sc.nextLine();
		  String rep;
		  Person p = new Person(str);
		  
		  // join chat
		  c_stub.subscribe(p);
		  // loop until leave chat
		  do {
			  str = sc.nextLine();
			  System.out.println("str contient : ["+str+"]");
		  rep = c_stub.sendMessage(p, str);
		  // affichage de son propre message
			  System.out.println(rep);
		  } while(!str.equals("wq"));
  
  //Affiche un message de deconnexion pour l'utilisateur quittant la salle de chat.
  System.out.println(str = c_stub.leave(p));
	      
	  } catch (Exception e)  { 
		  System.err.println("Error on client: " + e) ; 
	  }
   }  
}