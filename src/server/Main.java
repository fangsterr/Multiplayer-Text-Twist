package server;

/**
 * Run this class to get server up and running for client to communicate with.
 * 
 * @author AndyFang
 *
 */
public class Main
{
	public static void main(String[] args) throws java.io.IOException
	{
		Server server = new Server(8888, 2);
	}
}