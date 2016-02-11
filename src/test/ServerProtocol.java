package test;
import java.io.*;
import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerProtocol  implements ServerInterface {
	
	private ArrayList<ClientInterface> clients;
	private int id = 0;
	private Registry registry;
	private PrintWriter writer;
	private FileWriter inscriptions;
	private String historiqueName;
	private static final int DEFAULT_PORT = 1099;
	
	public ServerProtocol(int port) {
		try {
			ServerInterface t_strub = (ServerInterface) UnicastRemoteObject.exportObject(this, 0);
			this.registry = LocateRegistry.getRegistry(port);
			registry.bind("Server", t_strub);
			System.out.println ("Server ready");
		}
		catch (Exception e) {
			System.err.println("Error on server protocol :" + e) ;
			System.exit(-1);
		}
		
		clients = new ArrayList<>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date date = new Date();
		historiqueName = "history" + dateFormat.format(date) + ".txt";
				
		try {
			writer = new PrintWriter(historiqueName, "UTF-8");
			inscriptions = new FileWriter(new File("inscriptions"), true);
		}
		catch (IOException e) {
			System.err.println("Error on printwriter :" + e) ;
			e.printStackTrace();
			return;
		}
	}
	
	public ServerProtocol() {
		this(DEFAULT_PORT);
	}
	
	synchronized public String getNewClientId() throws RemoteException {
		return "Client" + this.id++;
	}
	
	synchronized public boolean addClient(String pseudo, String pass, String clientid) throws RemoteException {
		ClientInterface client = null;
		boolean to_add = true;
		
		try {
			for (String line : Files.readAllLines(Paths.get("inscriptions"), StandardCharsets.UTF_8)) {
			    String[] subline = line.split(" ");
			    String file_pseudo = subline[0];
			    String file_pass = subline[1];
			    if(file_pseudo.compareTo(pseudo) == 0) {
			    	if(file_pass.compareTo(pass) != 0) {
			    		return false;
			    	}
			    	
			    	to_add = false;
			    }
			}
		}
		catch(Exception e) {
			System.err.println("Error on server, add client :" + e) ;
			e.printStackTrace();
			return false;
		}
		
		if(to_add) {
			try {
				String insc = pseudo + " " + pass + "\n";
				inscriptions.append(insc);
				inscriptions.flush();
			} catch (IOException e) {
				System.err.println("Error on server, add client :" + e) ;
				e.printStackTrace();
				return false;
			}
		}
		
		try {
			client = (ClientInterface) this.registry.lookup(clientid);
		}
		catch (Exception e) {
			System.err.println("Error on server, add client :" + e) ;
			e.printStackTrace();
			return false;
		}
		clients.add(client);
		
		try {
			for (String line : Files.readAllLines(Paths.get(this.historiqueName), StandardCharsets.UTF_8)) {
			    client.getMessage(line);
			}
		}
		catch(Exception e) {
			System.err.println("Error on server, add client :" + e) ;
			e.printStackTrace();
			return false;
		}
		

		String debug = "Client " + pseudo + " connecté au serveur";
		sendMessage("", debug);
		
		return true;
	}
	
	synchronized public void removeClient(String pseudo, String clientid)  throws RemoteException {
		ClientInterface client = null;
		
		try {
			client = (ClientInterface) this.registry.lookup(clientid);
		}
		catch (Exception e) {
			System.err.println("Error on server :" + e) ;
			e.printStackTrace();
			return;
		}
		
		clients.remove(client);
		String debug = "Client " + pseudo + " déconnecté du serveur";
		sendMessage("", debug);
	}
	
	synchronized public void sendMessage(String pseudo, String message) throws RemoteException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String debug = "[" + dateFormat.format(date) + "] " + pseudo + " : " + message;
		System.out.println(debug);
		writer.println(debug);
		writer.flush();
		
		for(ClientInterface client: clients) {
			try {
				client.getMessage(debug);
			}
			catch(Exception e) {
				System.err.println("Error on server, send message :" + e) ;
			}
		}
	}
	
	protected void finalize() throws Throwable {
		inscriptions.close();
		writer.close();
	};
}
