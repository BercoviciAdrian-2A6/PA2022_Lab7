package dictionary;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class links a letter to all the words that contain it at least once
 * Furthermore, this class can be used to isolate the words that contain the target letter exactly a number of times
 */
public class LetterWordLinker
{
    //with the current architecture it is not required for this class to know what letter it represents
    /**
     * This HashMap is used to efficiently locate words that contain the target letter a specific number of times
     *
     * Used to return a list of words
     */
    HashMap<Integer, WordList> letterFrequencyWorldListsMap = new HashMap<>();
    /**
     * This HashMap is used to efficiently check if a word meets some [letter - letterFrequency] criteria
     *  Effectively, it tells you the frequency of a letter L in any word
     * Used to return true or false
     */
    HashMap<String, Integer> wordsLetterFrequencyMap = new HashMap<>();

    public boolean wordMatchesCriteria(Word targetWord,int targetFrequency)
    {
        if ( !wordsLetterFrequencyMap.containsKey(targetWord.getWordString()))
            return false;

        int frequency = wordsLetterFrequencyMap.get(targetWord.getWordString());

        return (frequency == targetFrequency);
    }

    public void addWord( Word word, int letterFrequency )
    {
        if (letterFrequencyWorldListsMap.containsKey(letterFrequency))
        {
            letterFrequencyWorldListsMap.get(letterFrequency).addWord(word);
        }
        else
        {
            WordList temp = new WordList();
            temp.addWord(word);
            letterFrequencyWorldListsMap.put(letterFrequency , temp);
        }

        wordsLetterFrequencyMap.put(word.getWordString(), letterFrequency);
    }

    public int getWordCount()
    {
        return wordsLetterFrequencyMap.size();
    }

    /**
     * @param frequency the minimal letter frequency
     * @return returns the number of words that contain a letter L -!AT LEAST!- N times
     */
    public int getWordCount(int frequency)
    {
        if (letterFrequencyWorldListsMap.containsKey(frequency))
        {
            int total = 0;
            while (letterFrequencyWorldListsMap.containsKey(frequency))
            {
                total += letterFrequencyWorldListsMap.get(frequency).getWordCount();
                frequency++;
            }

            return total;
        }
        return -1;
    }


    /**
     * @param frequency the minimal letter frequency
     * @return all the words that contain the letter L at least Frequency amount of times
     */
    public ArrayList<Word> getWordSet(int frequency)
    {
        ArrayList<Word> output = new ArrayList<>();

        while (letterFrequencyWorldListsMap.containsKey(frequency))
        {
            output.addAll( letterFrequencyWorldListsMap.get(frequency).getWords() );
            frequency++;
        }

        return output;
    }
}
