package chat;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerImpl implements ServerItf {
	
	ArrayList<ChatClient> members;
	ArrayList<String> historique;
	
	public ServerImpl() {
		super();
	} 

	@Override
	public String sendMessage(Person p, String m) throws RemoteException {
		return "[" + p.getNickName() + "] : " + m; 
	}

	@Override
	public void subscribe(Person p) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public void leave(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recupMessage(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}