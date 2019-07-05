import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MA {
    //global vars:
    private static List<Missile> displayMissileList;
    private static List<AMissile> aMissilesLaunchedList = new ArrayList<>();
    private static int nextID = 0;
    private static float xLoc;
    private static float yLoc;
    private static float missileSize;
    private static java.awt.Paint curPaint = Color.darkGray;

    //constructor
    public MA(List<Missile> displayMissileList, float x, float y, float initMSZ)
    {
        this.displayMissileList = displayMissileList;
        this.xLoc = x;
        this.yLoc = y;
        this.missileSize = initMSZ;
    }

    //public methods

    //cycles through colors
    private java.awt.Paint getNextPaint()
    {
        Paint returnPaint = curPaint;
        if(curPaint == Color.darkGray)returnPaint = new Color(171, 16, 6);
        else if(curPaint.equals(new Color(171, 16, 6)))returnPaint = Color.GRAY;
        else if(curPaint == Color.GRAY)returnPaint = new Color(74, 20, 99);
        else if(curPaint.equals(new Color(74, 20, 99)))returnPaint = Color.ORANGE;
        else if(curPaint == java.awt.Color.ORANGE)returnPaint = Color.darkGray;
        curPaint = returnPaint;

        return curPaint;
    }

    public void launchMissile(int minX, int maxX, int minHeight, int maxHeight, float missileSpeed)
    {
        AMissile missile = new AMissile(getNextID(), xLoc, yLoc, getNextPaint(), missileSize, missileSpeed);
        missile.launch(minX, maxX, minHeight, maxHeight);
        displayMissileList.add(missile);
        aMissilesLaunchedList.add(missile);
    }
    //getters and setters
    public static List<AMissile> getAMissilesLaunchedList() {
        return aMissilesLaunchedList;
    }

    private int getNextID()
    {
        nextID++;
        return nextID;
    }
}
