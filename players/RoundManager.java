package players;

import tiles.Bag;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Daemon threads are low priority threads
 * They provide services to the user services
 */
public class RoundManager implements Runnable
{
    public static final float GAME_DURATION = 5;

    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<String> playerNameList = new ArrayList<>();
    private static volatile int activePlayerIndex = 0;
    public static float timePassed = 0;

    public static void setPlayers(ArrayList<Player> players) {
        RoundManager.players = players;
        setPlayerNameList(players);
    }

    public static void setPlayerNameList(ArrayList<Player> players)
    {
        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++)
        {
            playerNameList.add( players.get(playerIndex).getPlayerName() );
        }
    }

    public static String getActivePlayerName()
    {
        return playerNameList.get(activePlayerIndex % playerNameList.size());
    }

    public static void passTurn()
    {
        activePlayerIndex++;
        if (activePlayerIndex >= playerNameList.size())
            activePlayerIndex = 0;
    }

    public static boolean gametimeIsOver()
    {
        if (timePassed >= GAME_DURATION)
            return true;

        return false;
    }

    public static boolean gameIsOver()
    {
        if (gametimeIsOver())
            return true;

        if (Bag.getRemainingTiles() == 0)
            return true;

        return false;
    }

    @Override
    public void run()
    {
        while (true)
        {
            timePassed = TimeKeeper.getTimePassed();

            int playersStillPlaying = 0;

            for (Player player: players)
                playersStillPlaying += player.getIsStillPlaying();

            if (playersStillPlaying == 0)
                break;
        }

        System.out.println("\n==========================================\nThe game is over!");

        System.out.println("Game end trigger: ");

        if (Bag.getRemainingTiles() == 0)
        {
            System.out.println("Tile bag was emptied!");
            System.out.println("Total gametime: " + timePassed + " s");
        }
        else
        {
            System.out.println("Tile remaining in the bag: " + Bag.getRemainingTiles());
            System.out.println("Timed out after: " + GAME_DURATION);
        }

        System.out.println("Scores: ");

        Player winner = null;

        for (Player player: players)
        {
            System.out.println(player.getPlayerName() + " -> " + player.getScore());

            if (winner == null || winner.getScore() < player.getScore())
                winner = player;
        }

        System.out.println("The winner is: ");

        System.out.println(winner.getPlayerName());

        System.out.println("Congratulations!\n=============================================");
    }
}
