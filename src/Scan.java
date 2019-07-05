public class Scan {
    private int mID;
    private float xLoc;
    private float yLoc;

    public Scan(int id, float x, float y)
    {
        mID = id;
        xLoc = x;
        yLoc = y;
    }

    public int getmID() {
        return mID;
    }

    public float getxLoc() {
        return xLoc;
    }

    public float getyLoc() {
        return yLoc;
    }
}
