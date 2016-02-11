package test;

import java.io.Serializable;

public class GUIChatServer implements Serializable
{
	private static final long serialVersionUID = 2491560312987568555L;

	// Informations sur le serveur à joindre
	private String serverName;
	private String userName;
	private String password;
	private String ip;
	private int port;
	private Object gui;
	
	GUIChatServer(String serverName, String userName, String password, String ip, int port)
	{
		this.serverName = serverName;
		this.userName = userName;
		this.password = password;
		this.ip = ip;
		this.port = port;		
	}
	
	public String toString()
	{
		return getServerName();
	}
	
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public String getServerName()
	{
		return serverName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setGUI(Object gui) 
	{
		this.gui = gui;
	}
	
	public Object getGUI() 
	{
		return this.gui;
	}
}
