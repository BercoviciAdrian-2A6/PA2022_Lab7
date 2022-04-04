package players;

import tiles.Bag;
import tiles.Tile;

import java.util.ArrayList;

public class Player implements Runnable
{
    private String playerName;
    ArrayList<Tile> hand;

    public Player(String playerName)
    {
        this.playerName = playerName;
    }

    @Override
    public void run()
    {
        while (RoundManager.getActivePlayerName() != playerName)
        {
        }

        hand = Bag.requestTiles(7);

        System.out.println(playerName + " has extracted the following tokens");

        for (Tile tile : hand)
            System.out.print(tile + ", ");

        System.out.println("\n-----------------------------------------------------");

        RoundManager.passTurn();

    }

    public String getPlayerName() {
        return playerName;
    }
}
