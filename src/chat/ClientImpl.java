package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl implements ClientItf {
	
    Registry registry;
    Person pClient;
    ServerItf s_stub = null;
    Fenetre fe;

    public ClientImpl (Person p, Fenetre f){
        try {
            registry = LocateRegistry.getRegistry(2020);
            s_stub = (ServerItf) registry.lookup("server");
            
            fe = f;
            pClient = p;
            pClient.setId(s_stub.genId());
            
            ClientItf c_skeleton = (ClientItf) UnicastRemoteObject.exportObject(this, 0); 
            registry.bind(""+pClient.getId(), c_skeleton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessage(String message) throws RemoteException {
        //Affiche le message envoyé par le serveur
        fe.afficheMessage(message);
    }

    public void connect() throws RemoteException {
        s_stub.subscribe(pClient);
    }

    public void envoiMessage(String message) throws RemoteException {
        s_stub.sendMessage(pClient, message);
    }

    public void deconnexion() throws RemoteException {
        fe.afficheMessage(s_stub.leave(pClient));
        fe.afficheMessage("\nDéconnecté du serveur.\n");
    }
    
    public void setPseudo(String nick) throws RemoteException{
        pClient.setNickName(nick);
    }
    
    public void afficheMembres() throws RemoteException{
        String tmp = "Actuellement sur le serveur : \n";
        fe.afficheMessage(tmp+s_stub.whoIsHere());
    }

	public void mp(String substring) throws RemoteException {
		fe.afficheMessage(s_stub.mp(substring,pClient.getNickName()));
	}
}
