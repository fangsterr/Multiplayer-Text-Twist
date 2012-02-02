package server;

import java.util.*;

public class WordHandler
{
    private String answer;
    private Set<String> words;
    private ArrayList<String> chosenWords;
   	private ArrayList<Integer> potentials;
   	
   	private static final int MAX_WORD_LENGTH = 6;
   	private static final int MIN_WORD_LENGTH = 2;

    public WordHandler()
    {
		potentials = new ArrayList<Integer>();
        words = FileUtil.loadFile("words.txt");
        Iterator<String> it = words.iterator();
        chosenWords = new ArrayList<String>();
        while(it.hasNext())
        {
            String query = it.next();
            if(query.length() == MAX_WORD_LENGTH)
            {
                chosenWords.add(query);
            }
        }
        chooseWord();
        System.out.println(potentialAnswers());
        for(int i = 0; i < potentials.size(); i++)
        {
			System.out.println(potentials.get(i));
		}
    }

    public String chooseWord()
    {
        answer = chosenWords.get((int)(Math.random() * chosenWords.size()));
        System.out.println("the chosen word - " + answer);
        return answer;
    }

    public String getAnswer()
    {
        return answer;
    }

    public int potentialAnswers()
    {
		int total = 0;
		Iterator<String> it = words.iterator();
		while(it.hasNext())
		{
			String test = it.next();
			if(test.length() > 2)
			{
				if(answer.contains(test.substring(0, 1)))
				{
					if(checkWord(test))
					{
						potentials.add(test.length());
						total++;
					}
				}
			}
		}
		return total;
	}

    public boolean checkWord(String guess)
    {
        String tempAnswer = answer;
        if(guess.length() <= MAX_WORD_LENGTH && guess.length() >= MIN_WORD_LENGTH && words.contains(guess))
        {
            int num = 0;
            for (int i = 0; i < guess.length(); i++)
            {
                String letter = guess.substring(i, i+1);
                if (tempAnswer.indexOf(letter) != -1) {
                    num++;
                    tempAnswer = tempAnswer.substring(0, tempAnswer.indexOf(letter)) + tempAnswer.substring(tempAnswer.indexOf(letter)+1);
                }
                else return false;
            }
            
            return (num == guess.length());
        }
        
        return false;
    }
}