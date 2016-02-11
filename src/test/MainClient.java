package test;

public class MainClient {
	public static void main(String [] args) {
		if(args.length < 1) {
			System.out.println("Usage java HelloClient <server host>");
			return;
		}
		
		String host = args[0];		
	}
}
