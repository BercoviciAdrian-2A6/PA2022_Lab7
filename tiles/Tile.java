package tiles;

public class Tile
{
    private char linkedChar;
    private int points;

    public Tile (char linkedChar, int points)
    {
        this.linkedChar = linkedChar;
        this.points = points;
    }

    @Override
    public String toString()
    {
        return linkedChar + " - " + points;
    }

    public char getLinkedChar() {
        return linkedChar;
    }

    public int getPoints() {
        return points;
    }
}
