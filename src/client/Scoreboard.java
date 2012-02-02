package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Scoreboard extends JPanel
{
	private TextTwistFrame frame;
	private int numClients;
	private ArrayList<JLabel> users;
	private ArrayList<JLabel> scores;
	private Map<Integer, Integer> numberScore;

	public Scoreboard(TextTwistFrame f, int clients)
	{
		users = new ArrayList<JLabel>();
		scores = new ArrayList<JLabel>();
		numberScore = new HashMap<Integer, Integer>();

		//title = BorderFactory.createTitledBorder("title");
		this.setBorder(BorderFactory.createTitledBorder("Scoreboard"));

		frame = f;
		//if(frame != null)
		frame.setScoreboard(this);
		//this.setBoard(1);
	}


	public void setBoard(int clients, String[] clientNames)
	{
		this.removeAll();
		numClients = clients;

		users = new ArrayList<JLabel>();
		scores = new ArrayList<JLabel>();


		this.setLayout(new GridLayout(numClients, 2));

		//LetterComponent temp;

	//	System.out.println("numClients = " + numClients);

		for(int i = 0; i < numClients; i++)
		{
			//System.out.println("i = " + i);

			String tempName = ""; //converts underscores to spaces
			for(int j = 0; j < clientNames[i].length(); j++)
			{
				if(clientNames[i].substring(j,j+1).equals("_"))
				{
					tempName += " ";
				}
				else
				{
					tempName += clientNames[i].substring(j,j+1);
				}
			}

			JLabel tempClient = new JLabel(tempName + ":");
			tempClient.setHorizontalAlignment(SwingConstants.CENTER);
			users.add(tempClient);
			this.add(tempClient);

			Integer clientScore = numberScore.get(new Integer(i));

			JLabel tempScore;
			if(clientScore == null)
			{
				numberScore.put(new Integer(i), new Integer(0));
				tempScore = new JLabel("0");
			}
			else
			{
				tempScore = new JLabel(clientScore+"");
			}
			tempScore.setHorizontalAlignment(SwingConstants.CENTER);
			scores.add(tempScore);
			this.add(tempScore);
			//numberScore.add(new Integer(0));
		}


		this.setFocusable(false);
		repaint();
	}

	public void updateScore(String clientName, int clientNum, int score)
	{
	//	System.out.println("before: " + scores.get(clientNum).getText());
		scores.get(clientNum).setText(score+"");
		numberScore.put(new Integer(clientNum), new Integer(score));

	//	System.out.println("after: " + scores.get(clientNum).getText());
		//System.out.println("tempScore2 = " + scores.get(clientNum).hashCode());
		//repaint();
		//System.out.println("Updated! clientNum = " + clientNum + " score = " + score);
	}
}