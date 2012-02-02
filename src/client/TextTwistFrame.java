package client;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class TextTwistFrame extends JFrame implements ActionListener
{
	private JTextArea textArea;
	private GuessPanel guessPanel;
	private Client network;
	private Scoreboard score;
	private JLabel time;
	private JButton readyButton;
	private JTextArea systemInfo;

	public TextTwistFrame(Client c)
	{
		network = c;
		network.setTextTwistFrame(this);

		this.setTitle("Online Text Twist");
		this.setResizable(false);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

		//correct words
		textArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.append("Correct Words: \n");
		this.getContentPane().add(scrollPane);
		scrollPane.setFocusable(false);

		JPanel rightSide = new JPanel();
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.PAGE_AXIS));
		this.getContentPane().add(rightSide);

		systemInfo = new JTextArea(5, 25);
		systemInfo.setLayout(new BoxLayout(systemInfo, BoxLayout.PAGE_AXIS));
		JScrollPane scrollPane1 = new JScrollPane(systemInfo);
		systemInfo.setEditable(false);
		systemInfo.setFocusable(false);
		this.getContentPane().add(scrollPane1);
		scrollPane1.setFocusable(false);
		systemInfo.setForeground(Color.red);
		systemInfo.setBackground(Color.yellow);
		systemInfo.setFont(new Font("DialogInput", Font.PLAIN, 12));
		systemInfo.setLineWrap(true);
		//systemInfo.append("HI");

		JPanel topRight = new JPanel();
		topRight.setLayout(new BoxLayout(topRight, BoxLayout.LINE_AXIS));
		rightSide.add(topRight);

		time = new JLabel("0:00");
		time.setPreferredSize(new Dimension(80,40));
		//time.setBorder(BorderFactory.createLineBorder(Color.black));
		time.setHorizontalAlignment(SwingConstants.CENTER);
		topRight.add(time);
		score = new Scoreboard(this,1);
		topRight.add(score);

		//GuessPanel
		guessPanel = new GuessPanel(network);
		rightSide.add(guessPanel);

		JPanel bottomRight = new JPanel();
		bottomRight.setLayout(new BoxLayout(bottomRight, BoxLayout.LINE_AXIS));
		rightSide.add(bottomRight);

		//adding buttons
		readyButton = new JButton("Click When Ready");
		readyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		readyButton.setMnemonic(KeyEvent.VK_R);
		readyButton.setActionCommand("ready");
		readyButton.addActionListener(this);
		bottomRight.add(readyButton);
		readyButton.setFocusable(false);

		JButton button1 = new JButton("Scramble");
		button1.setAlignmentX(Component.LEFT_ALIGNMENT);
		button1.setMnemonic(KeyEvent.VK_S);
		button1.setActionCommand("scramble");
		button1.addActionListener(this);
		bottomRight.add(button1);
		button1.setFocusable(false);

		//gets focus
		this.addWindowFocusListener(new WindowAdapter() {
		public void windowGainedFocus(WindowEvent e) {
			guessPanel.requestFocusInWindow();
		}
		});

		this.pack();

		//focus default
		guessPanel.requestFocusInWindow();

		this.setVisible(true);

	//	try{Thread.sleep(50);}catch(Exception e){}
		String name = JOptionPane.showInputDialog(this, "Enter your name:");


		String tempName = ""; //converts spaces to underscores
		for(int i = 0; i < name.length(); i++)
		{
			if(name.substring(i,i+1).equals(" "))
			{
				tempName += "_";
			}
			else
			{
				tempName += name.substring(i,i+1);
			}
		}

		System.out.println(tempName);

		network.sendName(tempName);
		this.setTitle("Online Text Twist: " + name);
	}

	public GuessPanel getGuessPanel()
	{
		return guessPanel;
	}

	public void setScoreboard(Scoreboard s)
	{
		score = s;
	}

	public Scoreboard getScoreboard()
	{
		return score;
	}

	public void actionPerformed(ActionEvent e)
	{
		final String action = e.getActionCommand();

		if(action.equals("ready"))
		{
			network.isReady();
		}
		else if(action.equals("scramble"))
		{
			char[] tempArray = new char[GuessPanel.WORD_LENGTH];

			for(int i = 0; i < tempArray.length; i++)
			{
				tempArray[i] = guessPanel.getTextArea().get(i).getLetter();
			}
			tempArray = guessPanel.scramble(tempArray);

			for(int i = 0; i < tempArray.length; i++)
			{
				guessPanel.getTextArea().get(i).setLetter(tempArray[i]);
			}

//guessPanel.setWord(guessPanel.getWord());
		}
	}
	public void updateCorrect(String word, int type, String clientName, int clientNum, int scoret)
	{
		//0 is good 1 guessed already 2 nonexistent

		if(type == 0)
		{
			textArea.append(word + " - Guessed by " + clientName.replace('_', ' ') + "\n");
			textArea.setCaretPosition(textArea.getDocument().getLength());
			score.updateScore(clientName, clientNum, scoret);
		}
		if(clientNum == network.getClientNumber())
		{
			if(type == 0)
			{
				systemInfo.append("Word is correct.\n");
			}
			else if(type == 1)
			{
				systemInfo.append("Word already guessed.\n");
			}
			else if(type == 2)
			{
				systemInfo.append("Word does not exist.\n");
			}

			systemInfo.setCaretPosition(systemInfo.getDocument().getLength());
		}
	}

	public void setTime(int min, int sec)
	{
		if(min >= 0 && sec >= 0)
		{
			if(min == 0 && sec == 0)
			{
				time.setText("Round's up!");
				guessPanel.setReady(false);
				guessPanel.reset();
			}
			else if(sec > 9)
				time.setText(min + ":" + sec);
			else
				time.setText(min + ":0" + sec);
		}
	}

	public JButton getButton()
	{
		return readyButton;
	}

	public JTextArea getTextArea()
	{
		return textArea;
	}

	public void alertOverflow()
	{
		systemInfo.append("All words have been guessed. Press ready to start a new round.\n");
		systemInfo.setCaretPosition(systemInfo.getDocument().getLength());
	}
}