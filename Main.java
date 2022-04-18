import dictionary.LexController;
import dictionary.Word;
import dictionary.WordList;
import players.Player;
import players.RoundManager;
import players.TimeKeeper;
import testing.DebugPrefixFinder;
import testing.DebugWordGeneration;
import tiles.Bag;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {

        LexController lexController = new LexController();

        //DebugPrefixFinder debugPrefixFinder = new DebugPrefixFinder(lexController);
        //DebugWordGeneration debugWordGeneration = new DebugWordGeneration(lexController);

        Bag.generateTiles();

        System.out.println("\n============================================");

        System.out.println("Tiles remaining in the bag: " + Bag.getRemainingTiles());

        ArrayList<Player> players = new ArrayList<>();


        for (int playerIndex = 0; playerIndex < 5; playerIndex++)
        {
            players.add( new Player("Player " + playerIndex, lexController) );
        }

        TimeKeeper timeKeeper = new TimeKeeper();

        Thread timeKeeperThread = new Thread(timeKeeper);

        timeKeeperThread.setDaemon(true);//daemons are low priority service threads for user threads

        timeKeeperThread.start();

        RoundManager.setPlayers(players);

        RoundManager daemonRoundManager = new RoundManager();

        Thread roundManagerThread = new Thread(daemonRoundManager);
        roundManagerThread.start();

        for (Player player : players)
        {
            Thread playerThread = new Thread(player);
            playerThread.start();
        }
    }
}
