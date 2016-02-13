package chat;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame 
    implements WindowListener, MouseListener, KeyListener {
    
    private TextArea messageArea;
    private TextField sendArea;
    //private String userName;
    
    public GUI(String s) {
        super(s);
        this.addWindowListener(this);
        this.setSize(800,600);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        messageArea = new TextArea();
        messageArea.setEditable(false);
        this.add(messageArea, "Center");
        messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
        
        Panel p = new Panel();
        p.setLayout(new FlowLayout());
        
        sendArea = new TextField(30);
        sendArea.addKeyListener(this);
        sendArea.setFont(new Font("Arial", Font.PLAIN, 16));
        
        p.add(sendArea);
        p.setBackground(new Color(221, 221, 221));
        Button send = new Button("Send");
        send.addMouseListener(this);
        p.add(send);
        Button clear = new Button("Clear");
        clear.addMouseListener(this);
        p.add(clear);
        
        this.add(p, "South");
        this.setVisible(true);
        sendArea.requestFocus();
    }
    
    public static void main(String[] args) {
        GUI chat = new GUI("Chat v1.0");
    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {

    }

    public void windowClosed(WindowEvent e) {
        
    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {
       
    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {
        
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
       
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
       
    }

    public void keyReleased(KeyEvent e) {

    }
}
