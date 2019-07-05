import java.io.InputStreamReader;
import java.util.List;

public class Ticker extends Thread {

    public Ticker(List<Missile> missileQueue)
    {
        this.missileList =  missileQueue;
    }

    //global vars
    private List<Missile> missileList;
    private Integer counter = 0;

    @Override
    public void run()
    {
        try {
            while(true) {
                //loop the counter
                if (counter >= 1000000)
                    counter = 0;
                else
                    counter++;

                synchronized (this) {
                    //wait((long) 25);//wait 25 milliseconds (40 ticks per second)
                    this.sleep((long)25);
                }
            }
        }catch (Exception ex)
        {
            System.out.println("Ticker: " + ex.toString());
        }
    }

    public int getCounter()
    {
       return counter;
    }
}
