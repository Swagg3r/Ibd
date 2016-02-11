package test;

import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JTextArea;

public class ClientProtocol implements ClientInterface {
	private ServerInterface server;
	private Registry registry = null; 
	private static final int DEFAULT_PORT = 1099;
	private JTextArea output;
	private String id;
	private String host;
	private int port;
	private String username;
	private String password;
	
	ClientProtocol(String host, String username, String password, JTextArea output, int port) {
		this.output = output;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}
	
	ClientProtocol(String host, String username, String password, JTextArea output) throws RemoteException {
		this(host, username, password, output, DEFAULT_PORT);
	}
	
	// Return false when if the username/pass is not correct
	public boolean connect() throws RemoteException  {
		try {
			// Get remote object reference
			this.registry = LocateRegistry.getRegistry(host, port);
			this.server = (ServerInterface) registry.lookup("Server");
			id = this.server.getNewClientId();
			ClientInterface c_strub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
			registry.bind(id, c_strub);
			return this.server.addClient(username, password, id);
		}
		catch (AlreadyBoundException | NotBoundException e) {
			System.err.println("Error on client :" + e);
			e.printStackTrace();
			System.exit(-1);
		}
		
		return false;
	}
	
	public void sendMessage(String message) throws RemoteException {
		System.out.println("Sending message...");
		this.server.sendMessage(username, message);
	}
	
	public void getMessage(String message) throws RemoteException {
		System.out.println("New message !");
		output.append(message + "\n");
	}
	
	public void disconnect() throws RemoteException {
		System.out.println("Remove client...");
		this.server.removeClient(username, id);
	}
	
	public String getUsername() {
		return this.username;
	}
}
