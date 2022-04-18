package dictionary;

import java.util.ArrayList;

public class WordList
{
    ArrayList<Word> words = new ArrayList<>();

    public void addWord(Word word)
    {
        words.add(word);
    }

    public int getWordCount()
    {
        return words.size();
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void printWords()
    {
        for (Word word : words)
        {
            System.out.println(word);
        }
    }
}
