package client;

import javax.swing.*;
import java.awt.*;

public class LetterComponent extends JComponent
{
	private char letter;
	private boolean isInput;

	public LetterComponent(boolean x)
	{
		isInput = x;
		letter = ' ';
		this.setPreferredSize(new Dimension(40,40));
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect(0, 0, getWidth(), getHeight());

		if(letter == ' ')
			g.setColor(Color.white);
		else
			g.setColor(Color.orange);

		g.fillOval(5, 5, getWidth()-10, getHeight()-10);

		g.setFont(new Font(null, Font.PLAIN, 30));

		FontMetrics fontMetrics = g.getFontMetrics();

		int x = 20 - fontMetrics.charWidth(letter)/2;

		int y = 20 + (fontMetrics.getAscent() - fontMetrics.getDescent())/2;

		g.setColor(Color.black);
		g.drawString(letter + "", x,y);
	}

	public void setLetter(char s)
	{
		letter = s;
		this.repaint();
	}

	public char getLetter()
	{
		return letter;
	}

}