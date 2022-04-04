import players.Player;
import players.RoundManager;
import tiles.Bag;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        Bag.generateTiles();
        ArrayList<Player> players = new ArrayList<>();


        for (int playerIndex = 0; playerIndex < 5; playerIndex++)
        {
            players.add( new Player("Player " + playerIndex) );
        }

        RoundManager.setPlayerNameList(players);

        for (Player player : players)
        {
            Thread playerThread = new Thread(player);
            playerThread.start();
        }
    }
}
