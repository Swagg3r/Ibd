package chat;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerImpl implements ServerItf {
	
	private ArrayList<ClientItf> members;
        private ArrayList<String> pseudos;
	private ArrayList<String> historique;
        private static int idGen = 0;
	private Registry registry;
        FileWriter writer;
	
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

	public synchronized void sendMessage(Person p, String m) throws RemoteException {
		//Appel a la fonction du client getMessage();
                String heure = new SimpleDateFormat("[HH:mm:ss]",Locale.FRANCE).format(new Date());
                heure += " ";
		String tmp = heure+"["+p.getNickName()+"] : "+m;
                historique.add(tmp);
            try {
                copieHistorique(tmp+"\n");
                for (ClientItf client : members) {
                	try{
                		client.getMessage(tmp);
                	}catch (Exception exe){
                		int pos = members.indexOf(client);
                		members.remove(pos);
                		pseudos.remove(pos);
                		System.out.println(pseudos.get(pos)+" a ete tué et supprimé");
                	}
                }
            } catch (Exception ex) {
            	System.out.println("Erreur, un client n'est pas connecté.");
            }
	}

	public synchronized void subscribe(Person p) throws RemoteException {
            try {
                //On recupere le pseudo du client qui souhaite se connecter.
                String pseudo = p.getNickName();
                int val = 0;
                String s = "";
                //On test si le pseudo existe deja.
                while (pseudos.indexOf(pseudo + s) != -1){
                    val++;
                    s = "("+val+")";
                }
                ClientItf c_strub = (ClientItf) registry.lookup(""+p.getId());
                
                //Si il existe deja, alors on le change en rajoutant un suffixe.
                if (val > 0) {
                    pseudo = pseudo+"("+val+")";
                    p.setNickName(pseudo);
                    c_strub.setPseudo(pseudo);                    
                }
                
                //Ajout du nouveau client dans le tableau.
                members.add(c_strub);
                pseudos.add(p.getNickName());

                //Gestion de l'historique
                String heure = new SimpleDateFormat("[HH:mm:ss]",Locale.FRANCE).format(new Date());
                heure += " ";
                historique.add(heure+p.getNickName()+" a rejoint la salle de chat");
                copieHistorique(heure+p.getNickName()+" a rejoint la salle de chat\n");
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getMessage(heure +p.getNickName()+" a rejoint la salle !");
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
	}

	public synchronized String leave(Person p) throws RemoteException {
            int numToRemove = pseudos.indexOf(p.getNickName());
            members.remove(numToRemove);
            pseudos.remove(numToRemove);
            //Affiche : p a quitter la salle de chat.
            String heure = new SimpleDateFormat("[HH:mm:ss]",Locale.FRANCE).format(new Date());
            heure += " ";
            historique.add(heure+p.getNickName()+" a quitté la salle de chat");
            try {
                copieHistorique(heure+p.getNickName()+" a quitté la salle de chat\n");
            } catch (IOException ex) {
               ex.getStackTrace();
            }
            for (int i = 0; i < members.size(); i++) {
                members.get(i).getMessage(heure+"["+p.getNickName()+"] a quitté la salle !");
            }
            return "Au revoir !";
	}
        
        public int genId() throws RemoteException{
            return idGen++;
        }
        
        private void copieHistorique(String texte) throws IOException{
            writer = null;
            try{
                writer = new FileWriter("historique.txt", true);
                writer.write(texte,0,texte.length());
            }catch(IOException ex){
                ex.printStackTrace();
            }finally{
                if(writer != null){
                writer.close();
                }
            }
        }

    public synchronized String whoIsHere() throws RemoteException {
        String tmp = "";
        for (int i = 0; i < pseudos.size(); i++) {
            tmp += pseudos.get(i)+"\n";
        }
      return tmp;
    }

	@Override
	public String mp(String m, String nick) throws RemoteException {
		//System.out.println("Dans la fonction !["+m+"]");
		String pseudo = m.substring(0, m.indexOf(' '));
		//System.out.println("["+pseudo+"]");
		String message = m.substring(m.indexOf(' ')+1);
		String messagePour = "Message privé recu de ["+nick+"] : "+ message;
		//System.out.println("["+message+"]");
		String heure = new SimpleDateFormat("[HH:mm:ss]",Locale.FRANCE).format(new Date());
		String retour = heure+" Message envoyé a  ["+pseudo+"] : "+message;
		int pos = pseudos.indexOf(pseudo);
		if (pos != -1) {
			members.get(pos).getMessage(messagePour);
			return retour;
		}
		return "Ce client n'existe pas.";
	}
}