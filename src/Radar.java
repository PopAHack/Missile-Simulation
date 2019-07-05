import java.util.ArrayList;
import java.util.List;

public class Radar {

    public static List<Scan> scan()
    {
        List<Scan> scanList = new ArrayList<Scan>();
        //code
        List<AMissile> aMissilesList = MA.getAMissilesLaunchedList();
        for(int i = 0; i < aMissilesList.size(); i++)
        {
            Scan newScan = new Scan(aMissilesList.get(i).getmID(), aMissilesList.get(i).getxLoc(), aMissilesList.get(i).getyLoc());
            scanList.add(newScan);
        }
        return scanList;
    }
}
