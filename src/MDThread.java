import java.util.List;

//handles the creation and calculations of the DMissiles.
public class MDThread extends Thread {
    private MD md;
    private List<Missile> missileList;
    private Ticker ticker;

    public MDThread(List<Missile> missileList, Ticker ticker)
    {
        this.missileList = missileList;
        this.ticker = ticker;
    }

    @Override
    public void run() {
        md = new MD(800,75, 1.8f, 4, missileList, ticker);

        int prevCounter = ticker.getCounter();
        while(true)
        {
            int curTick = ticker.getCounter();
            if(curTick != prevCounter)
            {
                md.poll(curTick);
                prevCounter = curTick;
            }
        }
    }
}
