import java.awt.*;

public class DMissileCorrective extends Missile {

    private Record record;
    private Boolean launched = false;

    //constructor
    public DMissileCorrective(int id, float x, float y, float size, float speed, Record record)
    {
        this.setxLoc(x);
        this.setyLoc(y);
        this.setPaint(Color.DARK_GRAY);
        this.setSize(size);
        this.setSpeed(speed);
        this.record = record;
    }

    public void launch()
    {
        launched = true;
    }

    public void calcEqn()
    {
        //record.getLastSlope()
        launch();
    }
}
