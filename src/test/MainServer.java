package test;
public class MainServer {
	
	public static void main(String [] args) {
		if(args.length == 1) {
			new ServerProtocol(Integer.parseInt(args[0]));
		}
		else {
			new ServerProtocol();
		}	
	}
}
