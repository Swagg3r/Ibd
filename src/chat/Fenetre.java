package chat;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;

public class Fenetre extends JFrame implements KeyListener, ActionListener, MouseListener, MouseMotionListener{
	
    private JFrame fenetre;
    private JPanel pane;
    private JButton send;
    private JButton clear;
    private JButton mp;
    private JTextArea message;
    private JTextField sendArea;

    // Barre des menus
    private JMenuBar barreMenu = new JMenuBar();

    // Menu pricipal
    private JMenu menu1 = new JMenu("Options");
    private JMenu menu2 = new JMenu("A propos");

    // Choix du menu principal
    private JMenuItem sous1 = new JMenuItem("Se conecter");
    private JMenuItem sous2 = new JMenuItem("Se deconnecter");
    private JMenuItem sous3 = new JMenuItem("Quitter");

    // Choix du menu secondaire
    private JMenuItem sous6 = new JMenuItem("Auteur");
    
    boolean isConnect;
    Person p;
    ClientImpl c;
    
    
   public Fenetre(){
        isConnect = false;
        //Infos sur la fenetre.
        fenetre = new JFrame("Chat distribué");
        fenetre.setSize(500,700);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        
        //Creation du panel
        pane = new JPanel();
        pane.setLayout(null);
        //pane.setBackground(new Color(221, 221, 221));
        fenetre.add(pane);
       
        //Bouton pour envoyer des messages dans le chat.
        send = new JButton("Envoyer");
        send.addActionListener(this);
        send.setEnabled(false);
        pane.add(send);
        send.setBounds(320, 630, 90, 30);
        send.setToolTipText("Cliquez pour envoyer");
        
        //Zone de texte JTextField
        sendArea = new JTextField();
        sendArea.addActionListener(this);
        sendArea.addKeyListener(this);
        sendArea.setEditable(false);
        pane.add(sendArea);
        sendArea.setBounds(10, 630, 300, 30);
        
        //Zone d'affichage des messages JTextArea
        message = new JTextArea();
        message.setEditable(false);
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setText("Veuillez vous connecter au serveur pour afficher les messages.\nPour cela, cliquer sur Options > Connection\nRentrez 'd' pour vous deconnecter ou 'de' pour quitter l'application");
        pane.add(message);
        message.setBounds(10, 20, 480, 580);
        
        //Bouton clear
        
        //Bouton de messages privé : mp
        
        //Placement des menus
        barreMenu.add(menu1);
        barreMenu.add(menu2);
        
        sous1.addActionListener(this);
        sous2.addActionListener(this);
        sous3.addActionListener(this);
        sous6.addActionListener(this);
        menu1.add(sous1);
        menu1.add(sous2);
        menu1.add(sous3);
        menu2.add(sous6);
        
        fenetre.setJMenuBar(barreMenu);
        fenetre.setVisible(true);
   }
   
   public void afficheMessage(String message){
       this.message.insert(message+"\n",0);
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == send) {
            String tmp = sendArea.getText();
            sendArea.setText("");
            try{
                if (tmp.equals("d")) {
                    c.deconnexion();
                    isConnect = false;
                    sendArea.setEditable(false);
                    send.setEnabled(false);
                } else if (tmp.equals("de")) {
                    c.deconnexion();
                    isConnect = false;
                    System.exit(0);
                }else {
                    c.envoiMessage(tmp);
                }
            } catch (Exception ex){
                ex.getStackTrace();
            }
        }
        else if (e.getSource() == sous1) {
            sendArea.setEditable(true);
            send.setEnabled(true);
            String nickname = JOptionPane.showInputDialog(this, "Choisissez votre pseudo :");
            message.insert("\n["+nickname+"] tente de se connecter au serveur\n",0);
            
            p = new Person(nickname);
            c = new ClientImpl(p,this);
            try {
                // join chat
                c.connect();
                isConnect = true;
            } catch (Exception ex) {
                ex.getStackTrace();
            }
        }
        else if (e.getSource() == sous2) {
            if (isConnect) {
                try {
                    c.deconnexion();
                    sendArea.setEditable(false);
                    send.setEnabled(false);
                } catch (Exception ex) {
                   ex.getStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vous n'êtes actuellement connecter sur aucun serveur");
            }
        }
        else if (e.getSource() == sous3) {
            if (isConnect) {
                try {
                    c.deconnexion();
                } catch (Exception ex) {
                   ex.getStackTrace();
                }
            }
            System.exit(0);
        }
        else if (e.getSource() == sous6) {
            JOptionPane.showMessageDialog(this, "Ce Chat a été réalisé par\n      Antoine Thebaud\n                  et\n Aurélien Monnet-Paquet");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
//    public static void main(String [] args) { 
//        Fenetre fenetre = new Fenetre();
//    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            send.doClick();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}