import java.util.List;

public class CollisionThread extends Thread {
    //global vars
    private List<Missile> missileList;
    private List<Explosion> explosionList;
    //constructor
     public CollisionThread(List<Missile> missileList, List<Explosion> explosionList )
    {
        this.missileList = missileList;
        this.explosionList = explosionList;
    }

    @Override
    public void run()
    {
        while(true)
        {
            for(int i = 0; i < missileList.size(); i++)
            {
                for (int j = 0; j < missileList.size(); j++) {
                    //if they're the same type
                    if(missileList.get(i) instanceof DMissileApprox && missileList.get(j) instanceof DMissileApprox)continue;
                    if(missileList.get(i) instanceof AMissile && missileList.get(j) instanceof AMissile)continue;

                    double radius = 10;

                    double dist = (missileList.get(i).getxLoc() - missileList.get(j).getxLoc())*(missileList.get(i).getxLoc() - missileList.get(j).getxLoc());
                    dist = Math.sqrt(dist + ((missileList.get(i).getyLoc() - missileList.get(j).getyLoc())*(missileList.get(i).getyLoc() - missileList.get(j).getyLoc())));

                    if(dist < 2*radius)
                    {
                        System.out.println("Collision detected");
                        if(missileList.get(i) instanceof DMissileApprox)
                        {
                            if(((DMissileApprox) missileList.get(i)).getRecord().getmID() == missileList.get(j).getmID()) {
                                Explosion explosion = new Explosion(missileList.get(j).getxLoc(), missileList.get(j).getyLoc());
                                explosionList.add(explosion);
                                missileList.remove(i);
                                missileList.remove(j);
                                i--;
                                j--;
                            }
                        }
                        else if(missileList.get(j) instanceof DMissileApprox)
                        {
                            if(((DMissileApprox) missileList.get(j)).getRecord().getmID() == missileList.get(i).getmID())
                            {
                                Explosion explosion = new Explosion(missileList.get(i).getxLoc(), missileList.get(i).getyLoc());
                                explosionList.add(explosion);
                                missileList.remove(i);
                                missileList.remove(j);
                                i--;
                                j--;
                            }
                        }
                    }
                }
            }
        }
    }
}
