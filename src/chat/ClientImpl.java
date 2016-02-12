package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl implements ClientItf {
	
	private String nickname;
	Registry registry;
	
	public ClientImpl (Person p){
		try {
			registry = LocateRegistry.getRegistry(2020);
			ClientItf c_skeleton = (ClientItf) UnicastRemoteObject.exportObject(this, 0); 
			registry.bind("client", c_skeleton);
			nickname = p.getNickName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String handleMessage(Person p, String m) throws RemoteException {
		return m;
	}
	
	public void getMessage(String message) throws RemoteException {
		//Affiche le message envoyer par le serveur
		System.out.println(message);
	}
	
	public void connect() throws RemoteException {
		ServerItf s_stub;
		
		try {
			//download server's methods
			s_stub = (ServerItf) registry.lookup("Server");
		} catch (Exception e) {
			e.printStackTrace();
		}
		s_stub.subscribe(nickname);
	}
	
	
//	
//
//	  
//	  // join chat
//	  c_stub.subscribe(p);
//	  // loop until leave chat
//	  do {
//		  str = sc.nextLine();
//		  System.out.println("str contient : ["+str+"]");
//		  c_stub.sendMessage(p, str);
//	  } while(!str.equals("wq"));
//
//	  //Affiche un message de deconnexion pour l'utilisateur quittant la salle de chat.
//	  System.out.println(str = c_stub.leave(p));
//	
//	
//	
	
	
}
