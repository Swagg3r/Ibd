package test;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class GUIChat extends JPanel
{
	private final static long serialVersionUID = 8578994257946130579L;
	
	private final static int SUB_BORDER_SIZE = 4;
	private final static int BORDER_SIZE = 4;
	private final static int BUTTON_WIDTH = 100;
	private final static int BUTTON_HEIGHT = 30;

	private JTextArea input = new JTextArea();
	private JTextArea output = new JTextArea();
	private JButton bSend = new JButton("Send");
	private JButton bLogOut = new JButton("Log out");

	// Protocole pour contacter le serveur
	private ClientProtocol protocol;
	private boolean setActions = true;
	
	public boolean isConnected()
	{
		return protocol != null;
	}
	
	public void setProtocol(ClientProtocol clientProtocol)
	{
		this.protocol = clientProtocol;
		input.setEditable(true);
		
		if(!this.setActions)
			return;
		
		this.setActions = false;
		
		bSend.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(protocol == null)
					return;
				
				try {
					protocol.sendMessage(input.getText());
					input.setText("");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		bLogOut.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(protocol == null)
					return;
				
				try {
					protocol.disconnect();
					protocol = null;
					input.setEditable(false);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}				
			}
		});
	}

	public GUIChat()
	{    		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(0, BORDER_SIZE, 0, 0));

		Border border = BorderFactory.createTitledBorder("Chat");
		Border margin = new EmptyBorder(SUB_BORDER_SIZE, SUB_BORDER_SIZE, SUB_BORDER_SIZE, SUB_BORDER_SIZE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JScrollPane sp;

		// Output
		sp = new JScrollPane(this.output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.output.setEditable(false);
		this.output.setLineWrap(true);
		this.output.setWrapStyleWord(true);

		panel.add(sp);

		// Blank
		panel.add(Box.createRigidArea(new Dimension(0, BORDER_SIZE * 2)));

		// Input
		sp = new JScrollPane(this.input, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.input.setLineWrap(true);
		this.input.setWrapStyleWord(true);

		sp.setPreferredSize(new Dimension(0, 120));
		sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

		panel.setBorder(new CompoundBorder(border, margin));
		panel.add(sp);

		// Bouton send & log out
		Box box = new Box(BoxLayout.X_AXIS);
		margin = new EmptyBorder(SUB_BORDER_SIZE + 2, 0, 0, 0);

		bSend.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		bSend.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		bLogOut.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		bLogOut.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		box.setBorder(margin);
		box.add(bSend);
		box.add(bLogOut);

		panel.add(box);

		// Tout y est
		this.add(panel);
	}

	public JTextArea getOutput() {
		return this.output;
	}
}
