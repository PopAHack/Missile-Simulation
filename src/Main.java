import com.sun.jmx.remote.internal.ArrayQueue;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    //global vars:
    private static List<Missile> displayMissileList = new ArrayQueue<>(500);
    private static List<Explosion> explosionQueue = new ArrayQueue<>(500);
    public static void main(String[] args)
    {
        try {
            System.out.println("Creating threads... ");

            DisplayThread displayThread = new DisplayThread(displayMissileList, explosionQueue);
            displayThread.start();

            Ticker ticker = new Ticker(displayMissileList);
            ticker.start();

            MAThread maThread = new MAThread(displayMissileList,0.5f); //seconds between spawns
            maThread.start();

            MDThread mdThread = new MDThread(displayMissileList, ticker);
            mdThread.start();

            int prevCounter = ticker.getCounter();
            while(true)
            {
                int curTick = ticker.getCounter();
                if(curTick != prevCounter)
                {
                    for (Missile missile : displayMissileList) {
                        missile.travel(explosionQueue);
                    }
                    prevCounter = curTick;
                }
            }
        }catch (Exception ex)
        {
            System.out.println("Main: " + ex.toString());
            System.out.println(ex.getStackTrace().toString());
        }
    }

    public static void addExplosion(Explosion explosion)
    {
        explosionQueue.add(explosion);
    }
}

