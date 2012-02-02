package server;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * This class deals with getting word guesses from the various clients and calculating scores.
 * 
 * @author AndyFang
 *
 */
public class Server
{
    private ArrayList<ClientConnection> clients;
    private WordHandler wordHandler;
    private ArrayList<String> correctWords;
    private ArrayList<Integer> scores;
    private Clock clock;
    private int n; //total number of players
    private int numReady; //number of players ready
    
    public static final int ROUND_TIME = 120; // expressed in seconds
    
	/* types of guesses */
    private static final int GOOD_GUESS = 0;
    private static final int GUESSED_ALREADY = 1;
    private static final int INCORRECT = 2;

    public Server(int port, int totalNumberofClients)
    {
		n = totalNumberofClients;
		numReady = 0;
		clock = new Clock(this, ROUND_TIME);
        scores = new ArrayList<Integer>();
        correctWords = new ArrayList<String>();
        wordHandler = new WordHandler();
        System.out.println(wordHandler.getAnswer());
        clients = new ArrayList<ClientConnection>();
        try
        {
            int num = 0;
            ServerSocket server = new ServerSocket(port);
            while(true)
            {
                clients.add(new ClientConnection(this, server.accept(), num));
                clients.get(clients.size() - 1).start();
                System.out.println("You have added client # " + num);
                scores.add(new Integer(0), num);
                num++;
            }
        }
        catch(java.io.IOException e)
        {
            System.out.println("Server object initialization failed.");
            e.printStackTrace();
		}
    }
    
    public void runClock() {
    	clock.run();
    }

    public boolean amIReady()
    {
		return numReady == n;
	}

    public void clientReady(int num)
    {
		numReady++;
		System.out.println("The number ready - " + numReady);
		System.out.println("The overall number - " + n);
	}

    public String getWord()
    {
        return wordHandler.getAnswer();
    }

    public boolean checkWord(String guess, int num) //num is the number of the client
    {
    	//type 0 good, 1 guessed already, 2 incorrect
    	int type = GOOD_GUESS;
    	
    	if (!wordHandler.checkWord(guess)) { type = INCORRECT; }
    	else if (correctWords.contains(guess)) { type = GUESSED_ALREADY; }
    	
        if (correctWords.contains(guess) == false)
        {
            if (wordHandler.checkWord(guess))
            {
                correctWords.add(guess);
                scores.set(num, scores.get(num) + (guess.length() * 100));
                alert(guess, type, num, scores.get(num));

                return true;
            }
            return false;
        }
        return false;
    }

	public int numClients()
	{
		return clients.size();
	}
	
	public ArrayList<ClientConnection> getClients()
	{
		return clients;
	}

    public void alert(String answer, int type, int num, int score) //main use is to alert on guesses, but does other stuff too
    {
        for(int i = 0; i < clients.size(); i++)
        {
            clients.get(i).alert(answer, type, num, score);
        }
    }

    /*
     * Alerts client connections to set time to the given time left (in seconds)
     */
    public void setTime(int time)
    {
		if(n == numReady)
		{
			for(int i = 0; i < clients.size(); i++)
			{
				clients.get(i).alertTime(time/1000);
			}
		}
	}
}