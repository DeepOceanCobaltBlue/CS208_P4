/*  Christopher Peters - Wrote class in accordance with UML design

 */
import javafx.scene.paint.Paint;

public class Teleporter extends GameItem {

    public Teleporter() {
        super();
    }

    public Teleporter(double centerX, double centerY, double rad, Paint inColor, int ID) {
        super(centerX, centerY, rad, inColor);
        super.setNumID(ID);
    }

    @Override
    public String toString() {
        return ( super.toString() + " | Teleporter |");
    }
}
