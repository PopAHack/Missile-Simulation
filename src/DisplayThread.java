import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

//displays displayable objects on a jfxdisplay
public class DisplayThread extends Thread implements KeyListener{

    private List<Missile> missileQueue;//contains every missile that needs to be drawn
    private List<Explosion> explosionQueue;//contains every missile that needs to be drawn
    protected int animDelay = 0;

    //constructor
    public DisplayThread(List<Missile> missileQueue, List<Explosion> explosionQueue) {
        this.missileQueue = missileQueue;
        this.explosionQueue = explosionQueue;
    }

    @Override
    public void run()
    {
        System.out.println("Started Display Thread");

        JFrame frame = new JFrame("Missile Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        JPanel panel = new MyPanel(frame.getWidth(), frame.getHeight());
        panel.setBackground(new java.awt.Color(75, 142, 202));
        panel.setVisible(true);

        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);

        while(true)
            panel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public class MyPanel extends JPanel
    {

        MyPanel(int width, int height) {
            // set a preferred size for the custom panel.
            setPreferredSize(new Dimension(width, height));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //hospital
            g2d.setColor(new java.awt.Color(242, 248, 255, 255));
            g2d.fillRect(1150,getHeight() - 115, 120, 40);
            g2d.setColor(new java.awt.Color(156, 84, 143, 255));
            g2d.drawRect(1150,getHeight() - 115, 120, 40);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(1151,getHeight()-80, 119, 5);
            g2d.setColor(Color.RED);
            g2d.fillRect(1195,getHeight()-95, 25, 5);
            g2d.fillRect(1205,getHeight()-105, 5, 25);


            //animation checks
            if(explosionQueue.size() >= 1) animDelay++;
            if(animDelay == 15000) animDelay=0;

            //loop through the queue and draw everything
            for (int i = 0; i < missileQueue.size(); i++) {
                missileQueue.get(i).draw(g2d, getHeight());
                if (missileQueue.size() >= 495) {
                    missileQueue.remove(0);
                }
            }

            //Dturret
            g2d.setColor(new java.awt.Color(10, 10, 21, 255));
            g2d.fillRect(785,getHeight() - 90, 20, 15);

            //explosions
            for (int i = 0; i < explosionQueue.size(); i++) {
                explosionQueue.get(i).draw(g, getHeight());
                if(animDelay % 15 == 0) {
                    explosionQueue.get(i).incrementFrame();
                }
                if (explosionQueue.size() >= 495) {
                    explosionQueue.remove(0);
                }
            }

            //ground
            g2d.setColor(new java.awt.Color(40, 134, 19));
            g2d.fillRect(0,getHeight() - 75, getWidth(), 75);

            //Aturret
            g2d.setColor(new java.awt.Color(10, 10, 21, 255));
            g2d.fillRect(10,getHeight() - 105, 40, 30);

        }
    }
}
