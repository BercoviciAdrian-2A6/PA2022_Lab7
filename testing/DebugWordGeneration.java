package testing;

import dictionary.LexController;

import java.util.Scanner;

public class DebugWordGeneration
{
    public static boolean inputIsValid(String input)
    {
        for (int i = 0; i < input.length(); i++)
        {
            if ( input.charAt(i) - 97 < 0 || input.charAt(i) - 97 >= 26 )
                return  false;
        }

        return true;
    }

    public DebugWordGeneration (LexController lexController)
    {
        while (true)
        {
            Scanner keyboardScanner = new Scanner(System.in);

            System.out.println("Input the target of letters ( 'a-z' only, duplicates allowed)");

            String inputString = null;

            do {
                if (inputString != null)
                    System.out.println("Invalid input, try again");

                inputString = keyboardScanner.next();
            } while (!inputIsValid(inputString));

            char[] letters;

            letters = inputString.toCharArray();

            String outputWord = lexController.getWordWithLetters(letters);

            if (outputWord != null)
            {
                System.out.println("The longest word containg these letters is:");
                System.out.println(outputWord);
            }
            else
            {
                System.out.println("No such word exists");
            }
        }
    }
}
