package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl implements ClientItf {
	
    Registry registry;
    Person pClient;
    ServerItf s_stub = null;

    public ClientImpl (Person p){
        try {
            registry = LocateRegistry.getRegistry(2020);
            s_stub = (ServerItf) registry.lookup("server");
            
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
        System.out.println(message);
    }

    public void connect() throws RemoteException {
        s_stub.subscribe(pClient);
    }	

    public void envoiMessage(String message) throws RemoteException {
        s_stub.sendMessage(pClient, message);
    }

    public void deconnexion() throws RemoteException {
        System.out.println(s_stub.leave(pClient));
    }
}
