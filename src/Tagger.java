/*  Christopher Peters - Wrote class in accordance with UML design

 */
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

public class Tagger extends NPC {

    public Tagger() {
        super();
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(false);
        super.setFill(Color.GREEN);
    }

    public Tagger(double centerX, double centerY, double rad, Paint inColor, int ID) {
        super(centerX, centerY, rad, inColor, ID);
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(false);
    }

}
