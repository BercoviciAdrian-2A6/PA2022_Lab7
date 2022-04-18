package tiles;

import players.Player;

import java.util.ArrayList;
import java.util.Random;

public class Bag
{
    static volatile ArrayList<Tile> tiles = new ArrayList<>();

    public static void generateTiles()
    {
        Random rand = new Random();

        for (int letter = 0; letter < 26; letter++)
        {
            int tileVal = rand.nextInt(10) + 1;

            for (int tileCounter = 1; tileCounter <= 10; tileCounter++)
            {
                tiles.add( new Tile((char) (letter + 'a'), tileVal) );
            }
        }
    }

    public static ArrayList<Tile> requestTiles(int count)
    {
        Random rand = new Random();

        ArrayList<Tile> requested = new ArrayList<>();

        for (int index = 0; index < count; index++)
        {
            if (tiles.size() == 0)
                break;

            int randIndex = rand.nextInt(tiles.size());

            requested.add( tiles.get(randIndex) );

            tiles.remove(randIndex);
        }

        return requested;
    }

    public static void returnTiles(ArrayList<Tile> returnedTiles)
    {
        tiles.addAll(returnedTiles);
    }

    public static int getRemainingTiles()
    {
        return tiles.size();
    }

    public static void debugPrint()
    {
        for (Tile tile : tiles)
            System.out.println(tile);
    }
}
