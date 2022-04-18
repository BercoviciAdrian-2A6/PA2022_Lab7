package dictionary;

import java.util.ArrayList;
import java.util.HashMap;

public class PrefixTree
{
    int treeLevel = 0;
    HashMap<Character, PrefixTree> branches = new HashMap<>();
    WordList prefixedWordlist = new WordList();

    public PrefixTree (int treeLevel)
    {
        this.treeLevel = treeLevel;
    }

    public void addWord(Word word)
    {
        prefixedWordlist.addWord(word);

        if (treeLevel >= word.getWordString().length())
        {
            return;
        }

        char targetChar = word.getWordString().charAt(treeLevel);

        if ( !branches.containsKey(targetChar))
        {
            branches.put(targetChar, new PrefixTree(treeLevel + 1));
        }

        branches.get(targetChar).addWord(word);
    }

    public WordList getPrefixedWords(String prefix)
    {
        if (treeLevel == prefix.length())
            return prefixedWordlist;

        char targetChar = prefix.charAt(treeLevel);

        if ( branches.containsKey(targetChar) )
            return branches.get(targetChar).getPrefixedWords(prefix);

        return null;
    }

    public WordList getPrefixedWordsBruteForce(String prefix)
    {
        WordList output = new WordList();
        ArrayList<Word> allWords = prefixedWordlist.getWords();

        for (Word word : allWords)
        {
            if (prefix.length() > word.wordString.length())
                continue;

            boolean wordIsValid = true;

            for (int charIndex = 0; charIndex < prefix.length(); charIndex++)
            {
                if ( prefix.charAt(charIndex) != word.wordString.charAt(charIndex))
                {
                    wordIsValid = false;
                    break;
                }
            }

            if (wordIsValid)
                output.addWord(word);
        }

        return output;
    }
}
