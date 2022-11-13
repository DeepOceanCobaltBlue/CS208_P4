import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;

public abstract class GameItem extends JPanel {
    private double radius;
    private int numID;
    private Rectangle bounds;
    private Rectangle collisionBox;
    private Color color;

    public GameItem(double rad, int num, Rectangle b, Color inColor) {
        radius = rad;
        numID = num;
        bounds = new Rectangle(b);
        setCollisionBox();
        color = inColor;
    }

    private void setCollisionBox() {
        Rectangle temp = new Rectangle(bounds);
        temp.width += 10;
        temp.height += 10;
        collisionBox = new Rectangle(temp);
    }

    public double getRadius() {
        return radius;
    }

    public int getNumID() {
        return numID;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Color getColor() {
        return color;
    }
}
