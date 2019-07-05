public class Eqn {
    private double aCoefficient;
    private double bCoefficient;
    private double cCoefficient;

    public Eqn(double a, double b, double c)
    {
        aCoefficient = a;
        bCoefficient = b;
        cCoefficient = c;
    }

    //returns the y value for a given x value
    public float getY(float x)
    {
        float returnVal =(float)(x*x*aCoefficient + x*bCoefficient + cCoefficient);
        return returnVal;
    }

    public double getBearing(float xLoc, float yLoc)
    {
        try {
            double bearing = 0;
            double slope = 2 * aCoefficient * xLoc + bCoefficient;
            //if(aCoefficient == 0)
                //System.out.println("haha");
            if (slope == 0) return Math.PI;//in the case it is facing Pi radians.

            float yInter = (float) (yLoc - slope * xLoc);
            float xInter = (float) (-yInter / slope);

            if (slope < 0)
                bearing = Math.PI - Math.atan((xInter - xLoc) / yLoc);
            else {
                bearing = Math.atan((xLoc - xInter) / yLoc);
            }
            return bearing;
        }catch (Exception ex)
        {
            System.out.println("Bearing(): " + ex.toString());
        }
        return 0;
    }

    public double getaCoefficient() {
        return aCoefficient;
    }

    public double getbCoefficient() {
        return bCoefficient;
    }

    public double getcCoefficient() {
        return cCoefficient;
    }
}