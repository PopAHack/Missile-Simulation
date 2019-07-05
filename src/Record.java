import java.util.ArrayList;
import java.util.List;

public class Record {
    private List<Float> coordinatesX = new ArrayList<>();
    private List<Float> coordinatesY = new ArrayList<>();
    private List<Integer> counterList = new ArrayList<>();
    private int mID;//ID of missile being recorded
    private int numEntries = 0;
    private Ticker ticker;
    private double minPoint = -1;

    //constructor
    public Record(int id, float initX, float initY, Ticker ticker)
    {
        this.mID = id;
        coordinatesX.add(initX);
        coordinatesY.add(initY);
        numEntries++;
        this.ticker = ticker;
        counterList.add(ticker.getCounter());
    }


    //returns the amount of ticks remaining until the recorded missile is at its max height
    public float ticksUntilMax(DMissileApprox missile)
    {
        if(numEntries <3)return -1;

        return ((getXlocAtMidpoint() + missile.getInterceptDiff()) - coordinatesX.get(coordinatesX.size()-1)) / getSpeed();
    }

    private float getSpeed()//finds x movement per tick
    {
        if(numEntries<2)return -1;//error

        int counter1 = counterList.get(0);
        int counter2 = counterList.get(1);
        int diff;

        if(counter2<counter1)//at some point we looped back to 0
            diff = 1000000-counter1 + counter2;//1000000 is the highest value the counter can be
        else
            diff = counter2-counter1;

        return (coordinatesX.get(1) - coordinatesX.get(0)) / diff;  //distance traveled over time taken
    }

    //returns the approximated value of the measured function at the given point x
    //uses interpolation approximation
    public float interpolateAt(float x)
    {
        if(numEntries < 3)return 0;//error
        double result = coordinatesY.get(0)*L0(x) + coordinatesY.get(1)*L1(x) + coordinatesY.get(2)*L2(x);
        return (float) result;
    }

    private double L0(float x)
    {
        double value;

        value = ((x - coordinatesX.get(1))*(x-coordinatesX.get(2)))/((coordinatesX.get(0) - coordinatesX.get(1))*(coordinatesX.get(0)-coordinatesX.get(2)));

        return value;
    }

    private double L1(float x)
    {
        double value;

        value = ((x - coordinatesX.get(0))*(x-coordinatesX.get(2)))/((coordinatesX.get(1) - coordinatesX.get(0))*(coordinatesX.get(1)-coordinatesX.get(2)));

        return value;
    }

    private double L2(float x)
    {
        double value;

        value = ((x - coordinatesX.get(0))*(x-coordinatesX.get(1)))/((coordinatesX.get(2) - coordinatesX.get(0))*(coordinatesX.get(2)-coordinatesX.get(1)));

        return value;
    }

    //takes the derivative of the Interpolation approx and sets it equal to zero.
    //solves for x
    public float getXlocAtMidpoint()
    {
        if(numEntries<3)return 0;//can't do it

        if(minPoint != -1) return (float)minPoint;//if we have already computed it

        double pt1 = coordinatesY.get(0)*( (-coordinatesX.get(1) - coordinatesX.get(2)) / ((coordinatesX.get(0) - coordinatesX.get(1)) * (coordinatesX.get(0) - coordinatesX.get(2))) );

        double pt2 = coordinatesY.get(1)*( (-coordinatesX.get(0) - coordinatesX.get(2)) / ((coordinatesX.get(1) - coordinatesX.get(0)) * (coordinatesX.get(1) - coordinatesX.get(2))) );

        double pt3 = coordinatesY.get(2)*( (-coordinatesX.get(0) - coordinatesX.get(1)) / ((coordinatesX.get(2) - coordinatesX.get(0)) * (coordinatesX.get(2) - coordinatesX.get(1))) );

        double pt4 = (2*coordinatesY.get(0)) / ((coordinatesX.get(0) - coordinatesX.get(1)) * (coordinatesX.get(0) - coordinatesX.get(2)));

        double pt5 = (2*coordinatesY.get(1)) / ((coordinatesX.get(1) - coordinatesX.get(0)) * (coordinatesX.get(1) - coordinatesX.get(2)));

        double pt6 = (2*coordinatesY.get(2)) / ((coordinatesX.get(2) - coordinatesX.get(0)) * (coordinatesX.get(2) - coordinatesX.get(1)));

        double result = (pt1 + pt2 +pt3)/(-pt4 - pt5 - pt6);

        minPoint = result;

        return (float)result;
    }

    public void addXY(float x, float y)
    {
        coordinatesX.add(x);
        coordinatesY.add(y);
        numEntries++;
        counterList.add(ticker.getCounter());
    }

    public int getmID() {
        return mID;
    }

    public int getNumEntries() {
        return numEntries;
    }
}
