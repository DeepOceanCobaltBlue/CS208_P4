import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Runner extends NPC implements ActionListener {

    public Runner() {
        super(5, 0, new Rectangle(0, 0, 10, 10), Color.blue);
    }

    public Runner(double rad, int num, Rectangle b, Color inColor) {
        super(rad, num, b, inColor);
        super.setCanTeleport(true);
        super.setSpeed(3);
    }

    public void teleport() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if collision with teleporter
        // if(e.getSource(Class.equals(Teleporter))) {
        //     move to next room
        // teleport();

        // if collision with tagger
        // if(e.getSource(Class.equals(Tagger))) {
        //      move to "out" table and delete from game window

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        // makes animation less choppy looking
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (int) (this.getX() - this.getRadius());
        int y = (int) (this.getY() - this.getRadius());
        int diameter = (int) (this.getRadius() * 2);
        g2.fillOval(x, y, diameter, diameter);
    }
}
