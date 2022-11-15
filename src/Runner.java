import javafx.scene.paint.Paint;

public class Runner extends NPC {

    public Runner() {
        super();
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(true);
        super.setFill(javafx.scene.paint.Color.BLUE);
    }
    public Runner(double rad, Paint color, int ID) {
        super(25, 25, rad, color, ID);
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(true);
        super.setFill(javafx.scene.paint.Color.BLUE);
    }

    public Runner(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor, ID);
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(true);
    }


}
