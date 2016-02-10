package chat;

import java.rmi.RemoteException;

public class ClientImpl implements ClientItf {

	public String handleMessage(Person p, String m) throws RemoteException {
		return m;
	}
}
