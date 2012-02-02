package client;

import java.net.*;
import java.io.*;

public class TestServer
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket serversocket = new ServerSocket(8888);
		Socket socket = serversocket.accept();

		//ServerSocket serversocket1 = new ServerSocket(8888);
		//Socket socket1 = serversocket1.accept();

		PrintWriter p = new PrintWriter(socket.getOutputStream(), true);


//		Thread.sleep(1000);
		p.println("");
		System.out.println("D");
		p.println("clien 2");
		p.println("scram ailing");
	}
}