package client;

import java.io.*;
import java.awt.*;
import java.net.*;

public class Client
{
	private ClientSide clientside;
	private GuessPanel guessPanel;
	private TextTwistFrame f;
	private int max_time; // expressed in seconds
	private int clientNumber;

	public Client(String ip, int port) throws IOException
	{
		clientside = new ClientSide(ip, port, this);


		clientside.start();
	}

	public void receive(String s)
	{
		System.out.println(s);
	}

	public void sendName(String name)
	{
		clientside.send("name " + name);
	}

	public void sendWord(String s)
	{
		clientside.send("guess " + s);
	}

	public void isReady()
	{
		clientside.send("ready");
	}

	public void setGuessPanel(GuessPanel p)
	{
		guessPanel = p;
	}

	public void setTextTwistFrame(TextTwistFrame p)
	{
		f = p;
		//System.out.println("setTextTwistFrame");
	}

	public void setClientNumber(int x)
	{
		clientNumber = x;
	}

	public int getClientNumber()
	{
		return clientNumber;
	}

	public void setScrambledWord(String s)
	{
		while(guessPanel == null)
		{
			try
				{Thread.sleep(1);}
			catch(Exception e)
			{}
		}
		guessPanel.setWord(s);

		f.getTextArea().removeAll();
		f.getButton().setBackground(null);
		guessPanel.setReady(true);
	}

	public void updateCorrect(String word, int type, String clientName, int clientNum, int score)
	{
		f.updateCorrect(word, type, clientName, clientNum, score);
	}

	public void setScoreboard(int size, String[] stringarray)
	{
		/*String[] nameArray = new String[(stringarray.length-1)/2];

		for(int i = 1; i < stringarray.length; i = i + 2)
		{
			nameArray[Integer.parseInt(stringarray[i])] = stringarray[i+1];
		}*/

		f.getScoreboard().setBoard(size, stringarray);

		f.pack();

		//f.setScoreboard(temp);
	}

	public TextTwistFrame getTextTwistFrame()
	{
		return f;
	}

	public void setTime(int time)
	{
		int min = time/60;
		int sec = time - (min*60);

		f.setTime(min, sec);

	}

	public void alertRestart()
	{
		f.getButton().setBackground(Color.YELLOW);
	}

	public void alertOverflow()
	{
		f.alertOverflow();
	}
}