import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tagger extends NPC implements ActionListener {

    public Tagger() {
        super(5, 0, new Rectangle(0, 0, 10, 10), Color.GREEN);
    }

    public Tagger(double rad, int num, Rectangle b, Color inColor) {
        super(rad, num, b, inColor);
        super.setCanTeleport(false);
        super.setSpeed(4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if collision with runner
        // trigger runner methods
        // TODO does tagger need an action listener?

    }
}
