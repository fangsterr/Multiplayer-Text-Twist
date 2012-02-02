package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GuessPanel extends JPanel implements KeyListener
{
	ArrayList<LetterComponent> input;
	ArrayList<LetterComponent> textArea;
	char[] scrambledLetters;
	public static final int WORD_LENGTH = 6;
	private int placeHolder;
	private Client network;
	private int count = 0;
	private String original;
	private boolean isReady;


	public GuessPanel(Client c)
	{
		network = c;
		network.setGuessPanel(this);

		this.setLayout(new GridLayout(2,WORD_LENGTH));
		input = new ArrayList<LetterComponent>();
		textArea = new ArrayList<LetterComponent>();
		placeHolder = 0;

		LetterComponent temp;
		for(int i = 0; i < WORD_LENGTH; i++)
		{
			temp = new LetterComponent(true);
			this.add(temp);
			input.add(temp);
		}
		for(int i = 0; i < WORD_LENGTH; i++)
		{
			temp = new LetterComponent(false);
			this.add(temp);
			textArea.add(temp);
		}

		this.setFocusable(true);
		isReady = true;

		this.addKeyListener(this);
	}

	public void setClient(Client c)
	{
		network = c;
	}

	public void setReady(boolean b)
	{
		isReady = b;
	}

	public void setWord(String s)
	{
		if(count == 0)
		{
			original = s;
			count++;
		}

		scrambledLetters = s.toCharArray();

		scrambledLetters = this.scramble(scrambledLetters);
		this.setChars();
	}

	public String getWord()
	{
		return String.copyValueOf(scrambledLetters);
	}

	public char[] scramble(char[] flarb)
	{
		String s = String.copyValueOf(flarb);
		char[] temp = new char[s.length()];

		for(int i = 0; i < temp.length; i++)
		{
			int temporary = (int)(Math.random() * s.length());
			temp[i] = s.charAt(temporary);
			s = s.substring(0,temporary) + s.substring(temporary+1);
		}
		if(String.copyValueOf(temp).equals(original))
			this.scramble(temp);

		return temp;
	}

	private void setChars()
	{
		for(int i = 0; i < WORD_LENGTH; i++)
		{
			if(textArea == null)
			{
				System.out.println("textArea is null.");
			}
			if(textArea.get(i) == null)
			{
				System.out.println("textArea.get(i) is null.");
			}
			if(scrambledLetters == null)
			{
				System.out.println("scrambledLetters is null.");
			}
			textArea.get(i).setLetter(scrambledLetters[i]);
			input.get(i).setLetter(' ');
		}
	}

	public void reset()
	{
		for(int i = 0; i < placeHolder; i++)
		{
			for(int j = 0; j < WORD_LENGTH; j++)
			{
				if(textArea.get(j).getLetter() == ' ')
				{
					textArea.get(j).setLetter(input.get(i).getLetter());
					input.get(i).setLetter(' ');
					break;
				}
			}
		}
		placeHolder = 0;
	}

	public ArrayList<LetterComponent> getTextArea()
	{
		return textArea;
	}

	public void keyPressed(KeyEvent k)
	{
		if(isReady)
		{
			char c = k.getKeyChar();

			if(c >= 'A' && c <= 'Z')
				c += 'a' - 'A';

			//System.out.println(c);

			if(c == '\n')
			{
				String temp = "";
				for(int i = 0; i < placeHolder; i++)
				{
					temp += input.get(i).getLetter();
				}

				System.out.println(temp);

				network.sendWord(temp);

				reset();
			}
			else if(k.getKeyCode() == KeyEvent.VK_BACK_SPACE)
			{
				if(placeHolder > 0)
					placeHolder--;

				for(int i = 0; i < WORD_LENGTH; i++)
				{
					if(textArea.get(i).getLetter() == ' ')
					{
						textArea.get(i).setLetter(input.get(placeHolder).getLetter());
						input.get(placeHolder).setLetter(' ');
						break;
					}
				}
			}
			else if(c == ' ')
			{
				char[] tempArray = new char[WORD_LENGTH];

				for(int i = 0; i < tempArray.length; i++)
				{
					tempArray[i] = textArea.get(i).getLetter();
				}

				tempArray = scramble(tempArray);

				for(int i = 0; i < tempArray.length; i++)
				{
					textArea.get(i).setLetter(tempArray[i]);
				}
			}
			else
			{

				for(int i = 0; i < WORD_LENGTH; i++)
				{
					if(c == textArea.get(i).getLetter())
					{
						input.get(placeHolder).setLetter(c);
						if(placeHolder < WORD_LENGTH)

						placeHolder++;
						textArea.get(i).setLetter(' ');
						break;
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent k)
	{
	}

	public void keyTyped(KeyEvent k)
	{
	}
}