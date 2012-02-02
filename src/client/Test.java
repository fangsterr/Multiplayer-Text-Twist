package client;

import java.io.*;
import java.net.*;


public class Test
{
	public static void main(String[] args) throws IOException
	{
		TextTwistFrame frame = new TextTwistFrame(new Client("localhost", 8888));
	}
}