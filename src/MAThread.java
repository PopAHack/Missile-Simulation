import java.lang.annotation.Inherited;
import java.util.List;

//handles the creation and calculations of the AMissiles.
public class MAThread extends Thread {

    //global vars
    private MA ma;
    private float spawnSpeed;
    private List<Missile> displayMissileList;
    private Randomiser rand = new Randomiser();

    //constructor
    public MAThread(List<Missile> displayMissileList, float spawnSpeed)
    {
        this.displayMissileList = displayMissileList;
        ma = new MA(displayMissileList, 25, 75, 6);
        this.spawnSpeed = spawnSpeed;
    }

    @Override
    public void run()
    {
        try {
            int counter = 0;
            while (true) {
                synchronized (this) {
                    //wait((long) (spawnSpeed * 1000));//wait spawnSpeed seconds
                    this.sleep((long)(spawnSpeed*1000));
                    counter++;
                }

                //settings
                int minX = 1100;
                int maxX = 1400;
                int minH= 400;
                int maxH = 675;
                float speed = rand.generateFloat(1f, 2.4f);

                if(counter == 15)
                {
                    counter = 0;
                    minX = 750;
                    maxX = 855;
                    minH = 50;
                    maxH = 100;
                    speed = rand.generateFloat(0.5f, 1.6f);
                }

                ma.launchMissile(minX, maxX, minH, maxH, speed);
            }
        }catch (Exception ex)
        {
            System.out.println("MAThread: " + ex.toString());
        }
    }

}
