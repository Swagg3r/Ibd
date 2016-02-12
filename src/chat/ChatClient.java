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

		  // Get remote object reference
		  Registry registry = LocateRegistry.getRegistry(2020);
		  
		  // Person creation
		  System.out.println("Choisissez votre pseudo");
		  Scanner sc = new Scanner(System.in);
		  String str = sc.nextLine();
		  String rep;
		  Person p = new Person(str);
		  
		  //upload client's methods
		  ClientImpl c = new ClientImpl(p); 
	      
	  } catch (Exception e)  { 
		  System.err.println("Error on client: " + e) ; 
	  }
   }  
}