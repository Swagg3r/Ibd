package chat;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner; 

public class ChatClient { 
    public static void main(String [] args) { 
        try { 
              Fenetre fenetre = new Fenetre();

        } catch (Exception e)  { 
                System.err.println("Error on client: " + e); 
        }
    }
}