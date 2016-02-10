package chat;
import java.rmi.registry.*;
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
		  Registry registry = LocateRegistry.getRegistry(Integer.parseInt(host)); 
		  
		  ServerItf c_stub = (ServerItf) registry.lookup("Chat1"); 
		  
		  // Person creation
		  System.out.println("Choisissez votre pseudo");
		  Scanner sc = new Scanner(System.in);
	      String str = sc.nextLine();
	      Person p = new Person(str);
	      
	      // join chat
	      c_stub.subscribe(p);
	      // loop until leave chat
	      do {
	    	  str = sc.nextLine();
	    	  str = c_stub.sendMessage(p, str);
	    	  // affichage de son propre message
			  System.out.println(str);
	      } while(!str.equals(":q"));
	      
	      c_stub.leave(p);
	      
	      
	  } catch (Exception e)  { 
		  System.err.println("Error on client: " + e) ; 
	  }
   }  
}