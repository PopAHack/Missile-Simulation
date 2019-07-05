import java.util.ArrayList;
import java.util.List;

public class MD {
    private float xLoc;
    private float yLoc;
    private float mSpeed;
    private List<Record> recordList = new ArrayList<Record>();
    private List<DMissileApprox> dmaWaitingList = new ArrayList<>();
    private List<Missile> missileQueue;
    private int curID = 0;
    private float mSize;
    private Ticker ticker;

    //constructor
    public MD(float xLoc, float yLoc, float mSpeed, float mSize, List<Missile> missileQueue, Ticker ticker) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.mSpeed = mSpeed;
        this.mSize = mSize;
        this.missileQueue = missileQueue;
        this.ticker = ticker;
    }

    public Missile findMissile(int id)
    {
        for (Missile missile : missileQueue)
        {
            if(missile.getmID() == id && missile instanceof AMissile)
                return missile;
        }
        return null;
    }

    public void poll(int curTick) {

        if(dmaWaitingList.size()>=495)
            dmaWaitingList.remove(0);

        if (curTick % 50 == 0) {
            List<Scan> scan = Radar.scan();
            for (int j = 0; j < recordList.size(); j++) {
                for (int i = 0; i < scan.size(); i++) {
                    if (scan.get(i) != null && scan.get(i).getmID() == recordList.get(j).getmID()) {
                        recordList.get(j).addXY(scan.get(i).getxLoc(), scan.get(i).getyLoc());
                        scan.set(i, null);
                        break;
                    }
                }
            }

            //scan found a new missile
            //create a log of it and assign a missile to it
            for (int i = 0; i < scan.size(); i++) {
                if (scan.get(i) instanceof Scan) {
                    Record rec = new Record(scan.get(i).getmID(), scan.get(i).getxLoc(), scan.get(i).getyLoc(), ticker);
                    recordList.add(rec);
                    createMissile(rec);
                }
            }
        }

        //see if our missiles are ready to launch yet
        for (int i = 0; i < dmaWaitingList.size(); i++) {
            if (dmaWaitingList.get(i).getLaunched()) {
                if (dmaWaitingList.get(i).tick()) {
                    missileQueue.add(dmaWaitingList.get(i));
                    dmaWaitingList.remove(i);
                    i--;
                }
            } else if (dmaWaitingList.get(i).getRecord().getNumEntries() == 3) {
                dmaWaitingList.get(i).setInterceptDiff();
                Eqn eqn = calcLinPath(dmaWaitingList.get(i));
                if (dmaWaitingList.get(i).launch(eqn)) {
                    missileQueue.add(dmaWaitingList.get(i));
                    dmaWaitingList.remove(i);
                    i--;
                }
            }
        }
    }

    private Eqn calcLinPath(DMissileApprox dma) {
        //calculates the linear eqn between the record max coords and the initDMA coords

        double a = 0;
        float x = dma.getRecord().getXlocAtMidpoint() + dma.getInterceptDiff();

        if(x-dma.getxLoc() == 0)return null;//can't divide by 0

        if(x > dma.getxLoc()) {//a
            double m = (dma.getRecord().interpolateAt(x) - dma.getyLoc()) / ( x - dma.getxLoc());
            double c = dma.getyLoc() - (dma.getxLoc() * m);
            Eqn linEqn = new Eqn(a, m, c);//+75 for ground offset
            if(m<0)
                System.out.println("das not good");
            return linEqn;
        }
        else//b
        {
            double m = (dma.getyLoc() - dma.getRecord().interpolateAt(x)) / (dma.getxLoc()-x);
            double c = dma.getyLoc() - (dma.getxLoc() * m);
            Eqn linEqn = new Eqn(a, m, c);//+75 for ground offset
            if(m>0)
                System.out.println("das not good");
            return linEqn;
        }
    }

    private void createMissile(Record record) {
        DMissileApprox newDMA = new DMissileApprox(curID++, xLoc, yLoc, mSize, mSpeed, record, this);
        dmaWaitingList.add(newDMA);
    }
}
