package server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ClientConnection extends Thread
{
    private Server server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    private int clientNum;

    public ClientConnection(Server s, Socket sock, int n)
    {
        server = s;
        socket = sock;
        clientNum = n;
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = in.readLine(); //waiting for the client to input name
            name = name.substring(5); //gets rid of "name "
            in.readLine(); //waiting for client to say it's ready
            server.clientReady(clientNum);

        }
        catch(java.io.IOException e) {System.out.println("Something's wrong in ClientConnection");}
    }

    public void run()
    {
		while(!amIReady())
	    {
			try
			{
				Thread.sleep(50);
			}
			catch(Exception e)
			{
			}
		}
		System.out.print("Both clients SHOULD be ready now!");
		out.println("numbe " + clientNum); //number assigned to the client
		out.println("scram " + server.getWord()); //the scrambled answer
        out.println("clien " + server.numClients());
        ArrayList<ClientConnection> clients = server.getClients();
        String output = "names ";
        for(int i = 0; i < server.numClients(); i++) {
        	output += clients.get(i).getClientName();
        	if(i < server.numClients()-1) {output += " ";}
        }
        out.println(output);
        out.println("total " + Server.ROUND_TIME); // Set the game round timer
        server.alert(null, 5, clientNum, 0); //you're telling people that you're ready. that is all.
        while(true)
        {
            try
            {
                System.out.println("Waiting for something from client");
                String guess = in.readLine();
                if(guess.startsWith("guess"))
                {
                    guess = guess.substring(6, guess.length());
                    System.out.println("here's the guess - " + guess);
                }
                server.checkWord(guess, clientNum);
            }
            catch(java.io.IOException e) {System.out.println("Problem in the thread;");}
        }
    }

    public void alert(String answers, int type, int num, int score)
    {
		if(num < 999)
		{
        	if(answers == null) // you are alerting everyone that someone is READY
          	  	out.println(num + " is Ready.");
        	else
            	out.println("corre " + answers + " " + type + " " + server.getClients().get(num).getClientName() + " " + num + " " + score);
		}
		else
		{
			out.println(answers + " " + score); //this is actually the time :)
		}
    }
    
    public void alertTime(int time) {
    	out.println("curre " + time);
    }
    
    public String getClientName()
    {
    	return name;
    }

    private boolean amIReady()
    {
		return server.amIReady();
	}
}