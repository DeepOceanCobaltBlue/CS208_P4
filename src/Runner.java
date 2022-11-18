/*  Christopher Peters - Wrote class in accordance with UML design

 */

import javafx.scene.paint.Paint;

public class Runner extends NPC {

    public Runner() {
        super();
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(true);
        super.setFill(javafx.scene.paint.Color.BLUE);
    }

    public Runner(double centerX, double centerY, double rad, Paint inColor, int ID) {
        super(centerX, centerY, rad, inColor, ID);
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(true);
    }

}
