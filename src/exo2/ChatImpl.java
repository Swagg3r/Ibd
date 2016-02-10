package exo2;

import java.rmi.RemoteException;

public class ChatImpl implements ChatItf {
	
	public ChatImpl() {
		super();
	} 
	
	public String sayHi(int c, HelloItf o, Person p) throws RemoteException { 
		return p.toString() + "says " + o.sayHello() + " " + c + " times!!!"; 
	}
}