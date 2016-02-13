package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl implements ServerItf {
	
	private ArrayList<ClientItf> members;
        private ArrayList<String> pseudos;
	private ArrayList<String> historique;
        private static int idGen = 0;
	private Registry registry;
	
	public ServerImpl() {
		members = new ArrayList<>();
                pseudos = new ArrayList<>();
		historique = new ArrayList<>();
		try {
			registry = LocateRegistry.getRegistry(2020);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	public void sendMessage(Person p, String m) throws RemoteException {
		//Appel a la fonction du client getMessage();
		String tmp = "["+p.getNickName()+"] : "+m;
		for (ClientItf client : members) {
			client.getMessage(tmp);
		}
	}

	public void subscribe(Person p) throws RemoteException {
            try {
                ClientItf c_strub = (ClientItf) registry.lookup(""+p.getId());
                members.add(c_strub);
                pseudos.add(p.getNickName());

                historique.add(p.getNickName()+" a rejoint la salle de chat");
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getMessage("["+p.getNickName()+"] a rejoint la salle !");
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
	}

	public String leave(Person p) throws RemoteException {
            int numToRemove = pseudos.indexOf(p.getNickName());
            members.remove(numToRemove);
            pseudos.remove(numToRemove);
            //Affiche : p a quitter la salle de chat.
            historique.add(p.getNickName()+" a quitté la salle de chat");
            for (int i = 0; i < members.size(); i++) {
                members.get(i).getMessage("["+p.getNickName()+"] a quitté la salle !");
            }
            return "Au revoir !";
	}
        
        public int genId() throws RemoteException{
            return idGen++;
        }
}