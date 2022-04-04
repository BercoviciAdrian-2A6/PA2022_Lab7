package players;

import java.util.ArrayList;

public class RoundManager
{
    private static ArrayList<String> playerNameList = new ArrayList<>();
    private static volatile int activePlayerIndex = 0;

    public static void setPlayerNameList(ArrayList<Player> players)
    {
        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++)
        {
            playerNameList.add( players.get(playerIndex).getPlayerName() );
        }
    }

    public static String getActivePlayerName()
    {
        return playerNameList.get(activePlayerIndex);
    }

    public static void passTurn()
    {
        activePlayerIndex++;
        if (activePlayerIndex >= playerNameList.size())
            activePlayerIndex = 0;
    }
}
