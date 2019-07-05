import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

public abstract class Missile{
    private int mID;
    private float speed;
    private float size;
    private float xLoc;
    private float yLoc;
    private Eqn equation;
    private java.awt.Paint paint = Color.BLACK;
    private Boolean destroyed = false;

    public void proxyCheck(){};

    public void travel(List<Explosion> exQueue)
    {
        if(equation == null)return;
        if(destroyed)return;
        xLoc = xLoc + speed;
        yLoc = equation.getY(xLoc);
        if(this instanceof DMissileApprox)
        {
            if(Math.abs(this.getxLoc() - (((DMissileApprox) this).getRecord().getXlocAtMidpoint() + ((DMissileApprox) this).getInterceptDiff())) < 10)
            {
                proxyCheck();
            }
        }
        if(this.getyLoc()<=75 && !destroyed) {
            setDestroyed(true);
            Explosion explosion = new Explosion(xLoc,yLoc+10);
            Main.addExplosion(explosion);
            if((this.getxLoc()>1150 && this.getxLoc() < 1150+120) || ((this.getxLoc()>785 && this.getxLoc() < 785+20))) {
                explosion.setExSize(250);
                if (this.paint.equals(Color.darkGray))
                    explosion.setExSize(350);
            }


            if(this instanceof DMissileApprox)
                System.out.println("oh uh");
        }

    }

    public void draw(Graphics2D g, int windowHeight){
        try {
            if(this.getDestroyed())return;//don't draw if it's destroyed
            double bearing = equation.getBearing(xLoc, yLoc);
            bearing = 2*Math.PI - bearing;//to "make" it appear to rotate clockwise
            if(this instanceof DMissileApprox && speed < 0)
                bearing = Math.PI + bearing;

            //get points
            float[] xPoints1 = { xLoc, xLoc + size, xLoc - size};
            float[] yPoints1 = { 2*size + yLoc, yLoc - size, yLoc - size};

            //put the shape on (0,0) before rotate
            xPoints1[0] -= xLoc;
            xPoints1[1] -= xLoc;
            xPoints1[2] -= xLoc;
            yPoints1[0] -= yLoc;
            yPoints1[1] -= yLoc;
            yPoints1[2] -= yLoc;

            //multiply points by the rotation matrix
            float[] xPoints2 = {xPoints1[0] * (float)Math.cos(bearing) + yPoints1[0] * (-1) * (float)Math.sin(bearing), xPoints1[1] * (float)Math.cos(bearing) + yPoints1[1] * (-1) * (float)Math.sin(bearing), xPoints1[2] * (float)Math.cos(bearing) + yPoints1[2] * (-1) * (float)Math.sin(bearing)};
            float[] yPoints2 = {xPoints1[0] *(float) Math.sin(bearing) + yPoints1[0] * (float)Math.cos(bearing), xPoints1[1] * (float)Math.sin(bearing) + yPoints1[1] * (float)Math.cos(bearing), xPoints1[2] * (float)Math.sin(bearing) + yPoints1[2] * (float)Math.cos(bearing)};

            //put back from (0,0) to original position
            xPoints2[0] += xLoc;
            xPoints2[1] += xLoc;
            xPoints2[2] += xLoc;
            yPoints2[0] += yLoc;
            yPoints2[1] += yLoc;
            yPoints2[2] += yLoc;

            //draw triangle
            yPoints2[0] = windowHeight-yPoints2[0];
            yPoints2[1] = windowHeight-yPoints2[1];
            yPoints2[2] = windowHeight-yPoints2[2];

            Path2D.Float path1 = new Path2D.Float();
            path1.moveTo(xPoints2[0], yPoints2[0]);
            path1.lineTo(xPoints2[1], yPoints2[1]);
            path1.lineTo(xPoints2[2], yPoints2[2]);
            path1.lineTo(xPoints2[0], yPoints2[0]);



            g.setColor((Color)paint);
            g.fill(path1);
            g.setColor(Color.BLACK);
            g.draw(path1);

        }catch (Exception ex)
        {
            System.out.println("Missile.draw(): " + ex.toString());
        }
    }

    //setters and getters
    public void setEqn(Eqn eqn){
        equation = eqn;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public float getxLoc() {
        return xLoc;
    }

    public float getyLoc() {
        return yLoc;
    }

    public void setxLoc(float xLoc) {
        this.xLoc = xLoc;
    }

    public void setyLoc(float yLoc) {
        this.yLoc = yLoc;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSize() {
        return size;
    }

    public Boolean getDestroyed() {
        return destroyed;
    }

    public void setDestroyed(Boolean destroyed) {
        this.destroyed = destroyed;
    }
}
