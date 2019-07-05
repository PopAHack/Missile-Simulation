import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Explosion {

    private float xLoc;
    private float yLoc;
    private int frame = 1;
    private List<BufferedImage> animation = new ArrayList<>();
    private int exSize = 35;

    public Explosion(float x, float y)
    {
        try {
            this.xLoc = x;
            this.yLoc = y;
            animation.add(ImageIO.read(new File("Sprite/Explosion1.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion2.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion3.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion4.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion5.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion6.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion7.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion8.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion9.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion10.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion11.png")));
            animation.add(ImageIO.read(new File("Sprite/Explosion12.png")));

        }catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void draw(Graphics g, int height)
    {
        try {
            if (frame <= 11) {
                g.drawImage(animation.get(frame-1), (int) xLoc - exSize/2, height - (int) yLoc - exSize/2, exSize,exSize, null);
            }
        }catch (Exception ex)
        {
            System.out.println("Explosion.draw(): " + ex.toString());
        }
    }

    public void incrementFrame()
    {
        frame++;
    }

    public void setExSize(int exSize) {
        this.exSize = exSize;
    }
}
