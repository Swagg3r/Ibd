package test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Window extends JFrame 
{
	private static final long serialVersionUID = -3016790751178316799L;
	
	// Fichier des serveurs
	public static final String SERVERS_FILE = "servers.binconf";
	
	// Propriétés de la fenêtre
	public static final String TITLE = "Chat Client Alpha V0.1";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 540;
	
	// Container (corps de la fenêtre)
	private JPanel container;
	
    // Barre de menus
    private JMenuBar menuBar = new JMenuBar();
    
    // Menus
    private JMenu mServers = new JMenu("Servers");
    private JMenu mAbout = new JMenu("About");

    // SousMenus
    private JMenuItem miManage = new JMenuItem("Manage");
    private JMenuItem miExit = new JMenuItem("Exit");
    
    // Liste de serveurs
    private DefaultListModel<GUIChatServer> servers;
      
    private static final Insets INSETS = new Insets(7, 7, 7, 7);
    
    // Construit des gbc 
    private GridBagConstraints gbc(int x, int y) 
    {
        GridBagConstraints gbc = new GridBagConstraints();
      
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        
        gbc.gridx = x;
        gbc.gridy = y;
      
        gbc.insets = INSETS;
        gbc.anchor = x == 0 ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill   = x == 0 ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.weightx = x == 0 ? 0 : 1;
        gbc.weighty = 1;

      return gbc;
    }
       
    // Surcharge de la fonction précédente, permet de gérer le nombre de cellules
    private GridBagConstraints gbc(int x, int y, int largeur, int hauteur)
    {
        GridBagConstraints gbc = gbc(x, y);
        gbc.gridwidth = largeur;
        gbc.gridheight = hauteur;

        return gbc;
    }
    
    public class SubWindowManage extends JDialog
    {
		private static final long serialVersionUID = -1453930167518304040L;

		// Modes d'éditions
		public final char EDITION_MODE_NONE = 0;
		public final char EDITION_MODE_NEW = 1;
		public final char EDITION_MODE_CHANGE = 2;
		
		private char editionMode = EDITION_MODE_NONE;
		
		// Labels...
        private JLabel lServerName = new JLabel("Server Name: ");
        private JLabel lIP = new JLabel("Address: ");
        private JLabel lPort = new JLabel("Port: ");
        private JLabel lUserName = new JLabel("User name: ");
        private JLabel lPassword = new JLabel("Password: ");

        // Champs
        private JTextField fServerName = new JTextField();
        private JTextField fIP = new JTextField();
        private JTextField fPort = new JTextField();
        private JTextField fUserName = new JTextField();
        private JTextField fPassword = new JPasswordField();

        // Boutons
        private JButton bChange = new JButton("Edit");
        private JButton bRemove = new JButton("Remove");
        private JButton bNew = new JButton("New");
        private JButton bValidate = new JButton("Validate");
        private JButton bCancel = new JButton("Cancel");

	    private JList<GUIChatServer> serversList = new JList<GUIChatServer>(servers);
	    
        private void addComponents()
        { 
        	// Liste des serveurs
            Border border = BorderFactory.createTitledBorder("Servers list");
            JPanel panel = new JPanel();
	        JScrollPane scServers = new JScrollPane(serversList);
	       
	        scServers.setPreferredSize(new Dimension(170, 368));
	        scServers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	        scServers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
	        serversList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        
	        panel.setBorder(border);
	        panel.add(scServers);
	        
            this.add(panel);            
    	
            Box container = Box.createVerticalBox();

            // Champs et labels
            border = BorderFactory.createTitledBorder("Information Servers");
            panel = new JPanel();
            
            panel.setLayout(new GridBagLayout());
            panel.setBorder(border);
            
            panel.add(lServerName, gbc(0, 0));    
            panel.add(fServerName, gbc(1, 0));    
            
            panel.add(lIP, gbc(0, 1));    
            panel.add(fIP, gbc(1, 1));    
            
            panel.add(lPort, gbc(0, 2));    
            panel.add(fPort, gbc(1, 2));    
            
            panel.add(lUserName, gbc(0, 3));    
            panel.add(fUserName, gbc(1, 3));    
              
            panel.add(lPassword, gbc(0, 4));    
            panel.add(fPassword, gbc(1, 4));    
              
            // Boutons
            panel.add(bChange, gbc(0, 5, 2, 1));
            panel.add(bRemove, gbc(0, 6, 2, 1));

            panel.setPreferredSize(new Dimension(250, 300));
            container.add(panel);

            // Boutons
            border = BorderFactory.createTitledBorder("Changes");
            panel = new JPanel();
            
            panel.setLayout(new GridBagLayout());
            panel.setBorder(border);
            
            panel.add(bNew, gbc(0, 0, 2, 1)); 
            panel.add(bCancel, gbc(0, 1));
            panel.add(bValidate, gbc(1, 1));
            
            container.add(panel);          
            
            this.add(container);
        }
        
        private void fieldsReset()
        {
        	fServerName.setText("");
        	fIP.setText("");
        	fPort.setText("");
        	fUserName.setText("");
        	fPassword.setText("");
        }
        
        private void fillFieldsByServer()
        {
        	GUIChatServer cs = serversList.getSelectedValue();
        	
        	if(cs == null)
        		return;
        	
        	this.fServerName.setText(cs.getServerName());
        	this.fIP.setText(cs.getIp());
        	this.fPort.setText(String.valueOf(cs.getPort()));
        	this.fUserName.setText(cs.getUserName());
        	this.fPassword.setText(cs.getPassword());
        }
        
        private void fillServerByFields()
        {
        	GUIChatServer cs = serversList.getSelectedValue();
        	
        	if(cs == null)
        		return;
                	
        	cs.setServerName(fServerName.getText());
        	cs.setIp(fIP.getText());
        	cs.setPort(Integer.parseInt(fPort.getText()));
        	cs.setUserName(fUserName.getText());
        	cs.setPassword(fPassword.getText());
        }
        
        private void setFieldsEnabled(boolean b)
        {
        	this.fServerName.setEnabled(b);
        	this.fIP.setEnabled(b);
        	this.fPort.setEnabled(b);
        	this.fUserName.setEnabled(b); 
        	this.fPassword.setEnabled(b);    
        }
        
        private void setEditionMode(boolean b, char mode)
        {
			this.serversList.setEnabled(!b);
        	this.setFieldsEnabled(b);    
        	
        	this.bChange.setEnabled(!b);
        	this.bRemove.setEnabled(!b);
        	this.bNew.setEnabled(!b);
        	this.bValidate.setEnabled(b);
        	this.bCancel.setEnabled(b);	
        	
        	this.editionMode = mode;
        }
        
        private void removeServer(int index)
        {
		    if(index >= 0) {
		        servers.removeElementAt(index);
		    
		        if(servers.getSize() == 0) {
		        	bChange.setEnabled(false);
		        	bRemove.setEnabled(false);
		        	fieldsReset();
		        }
		        else
			    	serversList.setSelectedIndex(0);
		    }
        }
        
        private void addActions()
        {
        	// Etats des champs
        	this.setFieldsEnabled(false);       	
        	
        	/*
        	 * S'il existe au moins un élément dans la liste des serveurs,
        	 * alors on sélectionne le premier élément et on active les 
        	 * boutons d'édition de ce serveur.
        	 */
        	
        	// Etats des boutons
         	this.bValidate.setEnabled(false);
        	this.bCancel.setEnabled(false);
        	
        	if(servers.getSize() == 0) {
        		this.bChange.setEnabled(false);
        		this.bRemove.setEnabled(false);
        	}
        	else {
            	this.serversList.setSelectedIndex(0);
            	this.fillFieldsByServer();
        	}
        	
        	// Action sur un serveur de la liste
        	this.serversList.addListSelectionListener(new ListSelectionListener() 
        	{
				@Override
        		public void valueChanged(ListSelectionEvent e) 
        		{
        			if (!e.getValueIsAdjusting())
        			{
        	    	  fillFieldsByServer();
        			}
        	    }
        	});
        	
        	// Edition d'un serveur de la liste
        	bChange.addActionListener(new ActionListener() 
        	{	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					setEditionMode(true, EDITION_MODE_CHANGE);
				}
			});
        	
        	// Suppression
        	bRemove.addActionListener(new ActionListener() 
        	{	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					int index = serversList.getSelectedIndex();
					removeServer(index);
					Window.this.saveServers();
				}
			});
        	
        	// Ajout
        	bNew.addActionListener(new ActionListener() 
        	{	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
		    		servers.addElement(new GUIChatServer("New Server", "", "", "", 0));
		    		serversList.setSelectedIndex(servers.size() - 1);
					setEditionMode(true, EDITION_MODE_NEW);
				}
			});
        	
        	// Validation
        	bValidate.addActionListener(new ActionListener() 
        	{	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					String msg = "";
					
					// Vérifications des champs
					if(fServerName.getText().length() == 0)
						msg += "- Server name field is empty\n";
					if(fIP.getText().length() == 0)
						msg += "- IP field is empty\n";
					if(fUserName.getText().length() == 0)
						msg += "- User name field is empty\n";	
					if(fPassword.getText().length() == 0)
						msg += "- Password field is empty\n";	
					if(fPort.getText().length() == 0)
						msg += "- Port field is empty\n";
					else
					{
						try
						{
							NumberFormat.getInstance().parse(fPort.getText());
						}
						catch(ParseException exc)
						{
					     msg += "- Port field is not a number\n";
						}
					}
					
					// Plus d'édition
					if(msg.length() == 0)
					{
						setEditionMode(false, EDITION_MODE_NONE);
						fillServerByFields();
						Window.this.saveServers();
					}
					else
					{
						JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
        	
        	// Annulation
        	bCancel.addActionListener(new ActionListener() 
        	{	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					// Si on annule la création d'un serveur, on l'enlève de la liste
					if(editionMode == EDITION_MODE_NEW)
						removeServer(servers.size() - 1);
					// Si on annule la modification d'un serveur, on reset les champs avec les bonnes valeurs
					else if(editionMode == EDITION_MODE_CHANGE)
						fillFieldsByServer();
					
					// Plus d'édition
					setEditionMode(false, EDITION_MODE_NONE);
				}
			});
        }
        
		public SubWindowManage()
    	{			
    		this.setLayout(new FlowLayout());
    		this.setTitle("Manage servers");
            this.setModal(true);

            this.addComponents();
            this.addActions();

    		this.pack();
    		this.setLocationRelativeTo(null);
    		this.setResizable(false);
            this.setVisible(true);
    	} 
    }
     
    private void makeMenuBar()
    {    	
    	// Création des menus
    	this.mServers.add(miManage);
    	this.mServers.addSeparator();
    	this.mServers.add(miExit);
    	    	
       	this.menuBar.add(mServers);
    	this.menuBar.add(mAbout);
    	
    	// Action des menus
    	this.miManage.addActionListener(new ActionListener() 
        {
			@Override
            public void actionPerformed(ActionEvent e) 
            {
				new SubWindowManage();
            }        
        });
        
    	this.miExit.addActionListener(new ActionListener() 
        {
			@Override
            public void actionPerformed(ActionEvent e) 
            {
				System.exit(0);
            }        
        });
    	
    	this.setJMenuBar(menuBar);
    }
    
    @SuppressWarnings("unchecked")
	public void loadServers()
    {    	
    	ObjectInput input = null;
    	
    	try {
        	File fileBase = new File(SERVERS_FILE);
        	
        	if(!fileBase.exists())
        	    fileBase.createNewFile();
        	
    		InputStream file = new FileInputStream(SERVERS_FILE);
    		InputStream buffer = new BufferedInputStream(file);
    		input = new ObjectInputStream(buffer);

    		servers = (DefaultListModel<GUIChatServer>)input.readObject();
    	}
    	catch(Exception e) {
    		servers = new DefaultListModel<>();
    	}
   		finally {
   			try {
   				input.close();
			} 
   			catch (Exception e) {}
    	}
    }
    	    
    public void saveServers()
    {
    	ObjectOutput output = null;
    	
    	try {
    		OutputStream file = new FileOutputStream(SERVERS_FILE);
    		OutputStream buffer = new BufferedOutputStream(file);
    		output = new ObjectOutputStream(buffer);

    		output.writeObject(servers);
    	} 
    	catch(Exception e) {}
    	finally {
   			try {
   				output.close();
			} 
   			catch (Exception e) {}
    	}
    }  
    
	public Window()	
    {
		Dimension dim = new Dimension(Window.WIDTH, Window.HEIGHT);
		
		// Chargement des serveurs
		this.loadServers();
		
		// Propriétés de base de la fenêtre
        this.setTitle(Window.TITLE);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

        // Barre de menu
        this.makeMenuBar();
        
        // Ajout du chat
    	this.container = new GUIChatContainer(this.servers);        
    	this.setContentPane(container);

		// Fenêtre visible et non redimensionnable par défaut       
    	this.pack();
		this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
	
	public static void main(String args[])
	{
		new Window();	
	}
}
