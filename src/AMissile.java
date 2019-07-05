import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Random;

public class AMissile extends Missile {

    //constructor
    public AMissile(int id, float x, float y, Paint paint, float size, float speed) {
        this.setxLoc(x);
        this.setyLoc(y);
        this.setPaint(paint);
        this.setSize(size);
        this.setSpeed(speed);
        setmID(id);
    }

    public void launch(int minX, int maxX, int minH, int maxH)
    {
        Eqn eqn = calcPathEqn(10, Randomiser.generateInt(minX, maxX), Randomiser.generateInt(minH, maxH));
        setEqn(eqn);
    }

    public Eqn calcPathEqn(float start, float end, float mHeight)
    {
        double a;
        double b;
        double c = 0;
        //conditions os don't divide by 0
        if(start==end)return null;
        if(end == 0) return null;

        //component and ratio building
        double m = (end-start)/2;
        double r1 = (m*m)/(end*end);
        double r2 = (start*start)/(m*m);
        double r3 = (m*r2 - start)/(end*r1-m);

        //gauss elimination
        c = (r3*(-mHeight)-mHeight*r2)/(r3*(r1-1)-r2+1);
        b = (mHeight*r2 - (r2-1)*c)/(m*r2-start);
        a = (-c-start*b)/(start*start);

        c= c+75;//ground offset

        Eqn eqn = new Eqn(a,b,c);

        return eqn;
    }
}
