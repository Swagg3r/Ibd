package test;

import java.awt.Dimension;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUIChatContainer extends JPanel 
{	
	private final static long serialVersionUID = -3016790751178316799L;
	
	private final static int BORDER_SIZE = 4;
	private final static int SUB_BORDER_SIZE = 4;
	private final static int SERVERS_LIST_WIDTH = 200;

    private JList<GUIChatServer> serversList;
    private GUIChat currentChat;
    
    private void addActions()
    {    	
    	this.serversList.addListSelectionListener(new ListSelectionListener() 
    	{
			@Override
    		public void valueChanged(ListSelectionEvent e) 
    		{
    			if (!e.getValueIsAdjusting())
    			{
    				GUIChatServer cs = serversList.getSelectedValue();
    				
    	        	if(cs == null)
    	        		return;
    	        	
    	        	GUIChat chat = (GUIChat)cs.getGUI();

    	        	// Gestion de la connexion des clients
    	        	if(chat == null || !chat.isConnected())
    	        	{
    	        		if(chat == null)
    	        		{
    	        			chat = new GUIChat();    	        		
    	        			cs.setGUI(chat);
    	        		}
    	        		
    	        		ClientProtocol protocol = null;
    	        		
						try {
							protocol = new ClientProtocol(cs.getIp(), cs.getUserName(),cs.getPassword(), chat.getOutput(), 
													      cs.getPort());
	    	        		if(protocol.connect() == false) {
								serversList.clearSelection();

								JOptionPane.showMessageDialog(null, "Unable to join the server: Bad Password", "Warning", 
										  				      JOptionPane.WARNING_MESSAGE);
								return;
	    	        		}
	    	        		
	    	        		chat.setProtocol(protocol);
	    	        		
						} catch (RemoteException e1) {
							// Echec de connexion
							JOptionPane.showMessageDialog(null, "Unable to join the server: No Network", "Warning", 
														  JOptionPane.WARNING_MESSAGE);
							
							serversList.clearSelection();
							
							return;
						}
    	        	}
    	        	
    	        	// MAJ du container
    	        	if(getCurrentChat() != null)
    	        		remove(getCurrentChat());
    	
    	        	add(chat);
    	        	currentChat = chat;
    	            revalidate();
    	       	    repaint(); 	
    			}
    	    }
    	});
    }
	
	public GUIChat getCurrentChat() {
		return this.currentChat;
	}
    
	public GUIChatContainer(DefaultListModel<GUIChatServer> servers)
	{		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.serversList = new JList<GUIChatServer>(servers);
		this.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		
		// Liste des serveurs
        JScrollPane scServers = new JScrollPane(serversList);
        Border border = BorderFactory.createTitledBorder("Servers list");
        Border margin = new EmptyBorder(SUB_BORDER_SIZE, SUB_BORDER_SIZE, SUB_BORDER_SIZE, SUB_BORDER_SIZE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        scServers.setPreferredSize(new Dimension(SERVERS_LIST_WIDTH, 0));
        scServers.setMaximumSize(new Dimension(SERVERS_LIST_WIDTH, Integer.MAX_VALUE));
        
        scServers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scServers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
      
        serversList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.addActions();
        
        panel.setBorder(new CompoundBorder(border, margin));
        panel.add(scServers);

        // Ajout de la liste
        this.add(panel);          
	}
}
