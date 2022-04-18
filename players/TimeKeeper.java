package players;

public class TimeKeeper implements Runnable
{
    private static volatile float timePassed = 0;

    @Override
    public void run()
    {
        long startNano = System.nanoTime();
        long currentNano;

        while (true)
        {
            currentNano = System.nanoTime();

            timePassed =  (currentNano - startNano) / 1000000000.0f;
        }
    }

    public static float getTimePassed() {
        return timePassed;
    }
}
