package testing;

import dictionary.LexController;
import dictionary.WordList;

import java.sql.SQLOutput;
import java.util.Scanner;

public class DebugPrefixFinder
{
    public DebugPrefixFinder (LexController lexController)
    {
        while (true)
        {
            Scanner keyboardScanner = new Scanner(System.in);

            System.out.println("Input the target prefix ( 'a-z' only)");

            String inputString = null;

            do {
                if (inputString != null)
                    System.out.println("Invalid input, try again");

                inputString = keyboardScanner.next();
            } while (! DebugWordGeneration.inputIsValid(inputString) );

            WordList wordList;

            long prefixTreeTime = 0, bruteforceTime = 0;

            for (int prefWay = 0; prefWay < 2; prefWay++)
            {
                long startTime = System.nanoTime();

                if (prefWay == 0)
                {
                    System.out.println("\n------------------------------");
                    System.out.println("Prefix tree method: ");
                    wordList = LexController.getPrefixTree().getPrefixedWords(inputString);
                }
                else
                {
                    System.out.println("\n------------------------------");
                    System.out.println("Classic method: ");
                    wordList = LexController.getPrefixTree().getPrefixedWordsBruteForce(inputString);
                }

                if (wordList != null)
                {
                    wordList.printWords();

                    if (prefWay == 0)
                        prefixTreeTime = (System.nanoTime() - startTime) / 1000000;
                    else
                        bruteforceTime = (System.nanoTime() - startTime) / 1000000;

                    System.out.println("Execution time: " + (System.nanoTime() - startTime) / 1000000 + "ms");
                }
                else
                    System.out.println("Invalid prefix");
            }

            System.out.println("\n=========================");
            System.out.println("Time comparison: ");
            System.out.println("Prefix tree: " + prefixTreeTime + "ms");
            System.out.println("Classic: " + bruteforceTime + "ms");
        }
    }
}
