package dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LexController
{
    private static PrefixTree prefixTree = new PrefixTree(0);
    private static HashMap<Character, LetterWordLinker> letterWordLinkers = new HashMap<>();

    public LexController ()
    {
        preprocessing();//create letter word linkers
        System.out.println("Words preprocessed succesfully!");
    }

    public static int getIntFromChar(char chr)
    {
        return chr-97;//97 is the ASCII code for 'a'
    }

    public void preprocessing()
    {
        try {
            Scanner dictionaryScanner = new Scanner( new File("src/main/java/dictionary/words.txt"));

            int wordsRead = 0;

            int wordsReadSinceLastUpdate = 0;

            while (dictionaryScanner.hasNext())
            {
                String temp = dictionaryScanner.next();

                Word newWord = new Word(temp);//constructor adds word where it needs to

                wordsRead++;

                wordsReadSinceLastUpdate++;

                if (wordsReadSinceLastUpdate >= 50000)
                {
                    wordsReadSinceLastUpdate = 0;
                    System.out.println("Words processed: " + wordsRead);
                }
            }

            System.out.println("Words processed: " + wordsRead);

            //System.out.println("Prefix tree volume: " + prefixTree.prefixedWordlist.getWordCount());

            /*
            //DEBUGGING..
            System.out.println(letterWordLinkers.size());

            for (Character chr: letterWordLinkers.keySet())
            {
                System.out.println("Words with " + chr + ": " + letterWordLinkers.get( chr ).getWordCount() );
                letterWordLinkers.get( chr ).checkListsIntegrity();
            }*/
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addLetterWordLink(char letter, int letterFrequency, Word word)
    {

        if (letterWordLinkers.containsKey(letter))
        {
            letterWordLinkers.get(letter).addWord(word, letterFrequency);
        }
        else
        {
            LetterWordLinker temp = new LetterWordLinker();
            temp.addWord(word, letterFrequency);
            letterWordLinkers.put(letter, temp);
        }
    }

    /**
     * This structure is used solely in getWordWithLetters to ascending-ly sort the WordLists
     */
    class Criteria implements Comparable<Criteria>
    {
        char letter;
        int letterFrequency;
        int wordlistSize;

        Criteria ( char letter, int letterFrequency, int wordlistSize)
        {
            this.letter = letter;
            this.letterFrequency = letterFrequency;
            this.wordlistSize = wordlistSize;
        }

        @Override
        public String toString() {
            return "Letter: " + letter + ", Frequency: " + letterFrequency + ", WordLists size: " + wordlistSize;
        }

        @Override
        public int compareTo(Criteria o)
        {
            if (this.wordlistSize < o.wordlistSize)
                return -1;

            if (this.wordlistSize > o.wordlistSize)
                return 1;

            return 0;
        }
    }

    /**
     *
     * @param targetLetters an array of characters
     * @return a word containg all characters or null
     *
     * Given the target letters and their frequencies, a criteria will be defined as the need for a word to contain a letter L AT LEAST N times;
     * The strictness of a criteria will be defined by the cardinal of the set of words that meet said criteria
     * Hence criteria with smaller sets of words are stricter
     * The set of words that meet any single criteria [letter L - letterFrequency N] can be obtained in constant time using LetterWordLinker.letterFrequencyWorldListsMap
     * We will start with the "strictest criteria" word set
     * We will iterate though this list
     * And we will check against all other criteria [letter L-letter frequency N]
     * This check is done in constant time using the LetterWordLinker.wordsLetterFrequencyMap hashmap
     * This check is also done from the strictest to the most lenient criteria
     * If a word meets [letter-letter frequency] criteria it is added to a candidate list
     * The longest candidate word is returned
     */
    public String getWordWithLetters(char[] targetLetters)
    {
        char[] targetFrequencies = new char[26];

        for (int letterIndex = 0; letterIndex < 26; letterIndex++ )
        {
            targetFrequencies[letterIndex] = 0;
        }

        for (int index = 0; index < targetLetters.length; index++)
        {
            int letterIndex =  getIntFromChar(targetLetters[index]);
            targetFrequencies[letterIndex]++;
        }

        int[] wordListSize = new int[26];

        ArrayList<Criteria> criteriaList = new ArrayList<>();

        for (int i = 0; i < 26; i++)
        {
            if (targetFrequencies[i] == 0)
            {
                wordListSize[i] = -1;
                continue;
            }

            LetterWordLinker temp = letterWordLinkers.get( (char) (i + 97) );

            wordListSize[i] = temp.getWordCount( targetFrequencies[i] );

            if (wordListSize[i] == -1)
                return null;

            criteriaList.add( new Criteria((char) (i + 97), targetFrequencies[i], wordListSize[i]) );
        }

        //sort criteria by strictness

        criteriaList.sort(Comparator.naturalOrder());

        ArrayList<Word> minWordSet = null;

        for (Criteria criteria: criteriaList)
        {
            LetterWordLinker targetLetterWordLinker = letterWordLinkers.get( criteria.letter );

            if (minWordSet == null)
            {
                minWordSet = targetLetterWordLinker.getWordSet( criteria.letterFrequency );
            }
            else
            {
                minWordSet.removeIf(word -> !targetLetterWordLinker.wordMatchesCriteria(word, criteria.letterFrequency));
            }
        }

        minWordSet.sort(Comparator.naturalOrder());

        if (minWordSet.size() >= 1)
            return minWordSet.get(0).getWordString();

        return null;
    }

    public static PrefixTree getPrefixTree()
    {
        return prefixTree;
    }
}
