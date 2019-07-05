import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomiser
{
    public static int generateInt(int min,int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public float generateFloat(float min, float max)
    {
        float randfloat = (float)(Math.random())*(max-min)+min;
        return randfloat;
    }




//    public static float generateFloat(float min, float max) {
//        if (min >= max)
//            throw new IllegalArgumentException("max must be greater than min");
//        float result = ThreadLocalRandom.current().nextFloat() * (max - min) + min;
//        if (result >= max) // correct for rounding
//            result = Float.intBitsToFloat(Float.floatToIntBits(max) - 1);
//
//        return result;
//    }
}