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

    public Tagger(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor, ID);
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(false);
    }


}
