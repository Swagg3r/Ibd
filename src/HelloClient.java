import java.rmi.registry.*; 

public class HelloClient { 
  public static void main(String [] args) { 
	  try { 
		  if (args.length < 1) { 
			  System.out.println("Usage: java HelloClient <server host>"); 
			  return;
		  }
		  
		  String host = args[0];
		  
		  // Get remote object reference
		  Registry registry = LocateRegistry.getRegistry(Integer.parseInt(host));
		  HelloItf stub = (HelloItf) registry.lookup("Hello1");
		  
		  // Remote method invocation
		  String res = stub.sayHello();
		  System.out.println(res);
		  
	  } catch (Exception e) { 
		  System.err.println("Error on client: " + e) ; 
	  }
  }
}