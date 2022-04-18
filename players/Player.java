package players;

import dictionary.LexController;
import tiles.Bag;
import tiles.Tile;

import javax.swing.text.AbstractDocument;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Player implements Runnable
{
    public static final int DEFAULT_TILES_TO_DRAW = 7;

    LexController lexController;
    private String playerName;
    ArrayList<Tile> hand;
    int score = 0;
    int tilesToDraw = DEFAULT_TILES_TO_DRAW;
    int isStillPlaying = 1;

    public Player(String playerName, LexController lexController)
    {
        this.playerName = playerName;
        this.lexController = lexController;
    }

    @Override
    public void run()
    {
        while (true)
        {

            while (RoundManager.getActivePlayerName() != playerName) {
                tilesToDraw = DEFAULT_TILES_TO_DRAW;
            }

            if (RoundManager.gameIsOver())
                break;

            do {

                if (Bag.getRemainingTiles() == 0)
                    break;

                hand = Bag.requestTiles(tilesToDraw);

                System.out.println("* " + playerName + " has extracted the following tokens");

                for (Tile tile : hand)
                    System.out.print(tile + ", ");

                System.out.println("Tiles remaining in the bag: " + Bag.getRemainingTiles());


                char[] letters = new char[hand.size()];

                int tilesValue = 0;

                for (int tileIndex = 0; tileIndex < hand.size(); tileIndex++) {
                    letters[tileIndex] = hand.get(tileIndex).getLinkedChar();
                    tilesValue += hand.get(tileIndex).getPoints();
                }

                String outputWord = lexController.getWordWithLetters(letters);

                if (outputWord != null) {
                    System.out.println("\n" + playerName + " created the following word: ");
                    System.out.println(outputWord);
                    tilesToDraw = outputWord.length();
                    score += tilesToDraw * tilesValue;
                    System.out.println(playerName + " new score: " + score + " (" + (score - tilesToDraw * tilesValue) + " + " + (tilesToDraw * tilesValue) + ")");
                    System.out.println("Tiles remaining in the bag: " + Bag.getRemainingTiles());
                } else {
                    System.out.println("\n" + playerName + " could not create a word. Returning last hand of tiles..");
                    Bag.returnTiles(hand);
                    System.out.println("Tiles remaining in the bag: " + Bag.getRemainingTiles());
                    System.out.println(playerName + " score remains: " + score);
                    break;
                }
            } while (true);

            System.out.println("\n============================================");

            RoundManager.passTurn();
        }

        System.out.println("\n" + playerName + " realized the game is over.");

        isStillPlaying = 0;

        RoundManager.passTurn();//pass the turn so the other threads can realise the game is over
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getIsStillPlaying() {
        return isStillPlaying;
    }

    public int getScore() {
        return score;
    }
}
