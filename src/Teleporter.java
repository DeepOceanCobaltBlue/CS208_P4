import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Teleporter extends GameItem implements ActionListener {

    public  Teleporter() {
        super(1, 0, new Rectangle(0, 0, 1, 1), Color.BLACK);
    }

    public Teleporter(double rad, int num, Rectangle b, Color inColor) {
        super(rad, num, b, inColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
