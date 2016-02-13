package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl implements ClientItf {
	
    private String nickname;
    Registry registry;
    Person pClient;
    ServerItf s_stub = null;

    public ClientImpl (Person p){
        try {
            pClient = p;
            registry = LocateRegistry.getRegistry(2020);
            ClientItf c_skeleton = (ClientItf) UnicastRemoteObject.exportObject(this, 0); 
            registry.bind("client", c_skeleton);
            nickname = p.getNickName();
            //TODO : recuperer l'id du nouveau client pour l'exporter et le bind avec sont propre id
            //Histoire de pouvoir de pouvoir l'identifier su coté serveur.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String handleMessage(Person p, String m) throws RemoteException {
        return m;
    }

    public void getMessage(String message) throws RemoteException {
        //Affiche le message envoyé par le serveur
        System.out.println(message);
    }

    public void connect() throws RemoteException {
        try {
            //download server's methods
            s_stub = (ServerItf) registry.lookup("server");
            System.out.println("Je suis in da connect");
        } catch (Exception e) {
            e.printStackTrace();
        }
        s_stub.subscribe(pClient);
    }	

    public void envoiMessage(String message) throws RemoteException {
        s_stub.sendMessage(pClient, message);
    }

    public void deconnexion() throws RemoteException {
        s_stub.leave(pClient);
    }
}
