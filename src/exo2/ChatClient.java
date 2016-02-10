package exo2;
import java.rmi.registry.*; 

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
		  
		  HelloItf h_stub = (HelloItf) registry.lookup("Hello1"); 
		  ChatItf s_stub = (ChatItf) registry.lookup("Chat1"); 
		  // Person creation 
		  Person p = new Person("Vanille", "Gorgonzola"); 
		  // Remote method invocation
		  String res = h_stub.sayHello();
		  System.out.println(res); 
		  String res2 = s_stub.sayHi(10, h_stub, p);
		  System.out.println(res2); 
	  } catch (Exception e)  { 
		  System.err.println("Error on client: " + e) ; 
	  }
   }  
}