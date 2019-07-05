import java.awt.*;

public class DMissileApprox extends Missile {

    private Boolean launched = false;
    private Record record;
    private float ticksLeft;
    private MD md;
    private float interceptDiff;

    //constructor
    public DMissileApprox(int id, float x, float y, float size, float speed, Record record, MD md)
    {
        this.setxLoc(x);
        this.setyLoc(y);
        this.setPaint(Color.RED);
        this.setSize(size);
        this.record = record;
        this.setmID(id);
        this.md = md;
        this.setSpeed(speed);
    }

    public void setInterceptDiff()
    {
        //settings
        int min = -25;
        int max = 300;
        int range = 70;

        Randomiser rand = new Randomiser();

        //if the missile will hit before the intercept point, choose a better max
        if(record.interpolateAt(record.getXlocAtMidpoint() + max) <= 0 ) {
            max = 50;
            min = 15;
        }


        interceptDiff = rand.generateFloat(min, max);
        while(Math.abs(getxLoc() - (record.getXlocAtMidpoint() + interceptDiff)) < range)
            interceptDiff = rand.generateFloat(min, max);

        if(record.getXlocAtMidpoint() + interceptDiff > getxLoc()) {
            this.setSpeed(getSpeed());//positive, as we are going forwards along the path
        }
        else
            this.setSpeed(-getSpeed());//needs to be negative, as we are going backwards along the path
    }

    @Override
    public void proxyCheck()
    {
        Missile missile = md.findMissile(record.getmID());

        double radius = getSize()*2 + missile.getSize()*3;
        double a = missile.getxLoc() - this.getxLoc();
        double b = missile.getyLoc() - this.getyLoc();
        double dist = Math.sqrt(a*a + b*b);
        if(dist < radius)
        {
            Explosion explosion = new Explosion(missile.getxLoc(), missile.getyLoc());
            Main.addExplosion(explosion);
            missile.setDestroyed(true);
            this.setDestroyed(true);
        }
    }

    public Boolean launch(Eqn eqn)
    {
        launched = true;
        setEqn(eqn);
        //setInterceptDiff();

        float ticksToMaxDMA = ticksToIntercept(record.getXlocAtMidpoint() + getInterceptDiff());
        float ticksToMaxAM = record.ticksUntilMax(this);

        if(ticksToMaxAM - ticksToMaxDMA <= 0)return true;

        ticksLeft = ticksToMaxAM - ticksToMaxDMA;

        return false;
    }

    public Boolean tick()
    {
        if(ticksLeft <=0)return true;
        ticksLeft--;
        return false;
    }

    private float ticksToIntercept(float xMax)
    {
        return Math.abs(getxLoc() - xMax) / Math.abs(getSpeed());
    }

    public Record getRecord()
    {
        return record;
    }

    public Boolean getLaunched()
    {
        return launched;
    }

    public float getInterceptDiff() {
        return interceptDiff;
    }
}
