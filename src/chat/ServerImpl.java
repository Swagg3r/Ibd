package chat;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerImpl implements ServerItf {
	
	ArrayList<Person> members;
	ArrayList<String> historique;
	
	public ServerImpl() {
		members = new ArrayList<>();
		historique = new ArrayList<>();
	} 

	public String sendMessage(Person p, String m) throws RemoteException {
		return "[" + p.getNickName() + "] : " + m; 
	}

	public void subscribe(Person p) throws RemoteException {
		members.add(p);
		//Affiche : p a rejoint la salle de chat.
		historique.add(p.getNickName()+" a rejoint la salle de chat");
		//TODO : Envoyer notification de nouveau message a la liste de clients.
	}

	public String leave(Person p) throws RemoteException {
		members.remove(p);
		//Affiche : p a quitter la salle de chat.
		historique.add(p.getNickName()+" a quitté la salle de chat");
		//TODO : Envoyer notification de nouveau message a la liste de clients.
		return "Au revoir !";
	}

	public String recupMessage(Person p) throws RemoteException {
		//TODO : Recuperation du dernier message ???
		return historique.get(historique.size());
	}
}