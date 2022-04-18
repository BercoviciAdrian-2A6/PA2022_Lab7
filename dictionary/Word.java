package dictionary;

import java.util.Arrays;

public class Word implements Comparable<Word>
{
    String wordString;
    char[] letterFrequencies = new char[26];

    public Word (String wordString)
    {
        wordString = wordString.toLowerCase();

        this.wordString = wordString;

        for (int i = 0; i < wordString.length(); i++)
        {
            int a_zIndex = LexController.getIntFromChar( wordString.charAt(i) );

            if (a_zIndex < 0 || a_zIndex >= 26)
                continue;

            letterFrequencies[ a_zIndex ]++;
        }

        //once letter frequencies have been calculated, add self to correct word lists
        for (int i = 0; i < 26; i++)
        {
            if (letterFrequencies[i] == 0)
                continue;

            LexController.addLetterWordLink((char)(i + 97), letterFrequencies[i], this);
        }

        LexController.getPrefixTree().addWord(this);//add self to the prefix tree
    }

    public void printWord()
    {
        System.out.println(wordString);

        for (int i = 0 ; i < 26; i++)
        {
            if (letterFrequencies[i] != 0)
            {
                System.out.println( (char) (i + 97) + "-> frequency: " + (int)letterFrequencies[i] );
            }
        }
    }

    public String getWordString() {
        return wordString;
    }


    @Override
    public int compareTo(Word o)
    {
        if (this.wordString.length() > o.wordString.length())
            return -1;

        if (this.wordString.length() < o.wordString.length())
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        return wordString;
    }
}
