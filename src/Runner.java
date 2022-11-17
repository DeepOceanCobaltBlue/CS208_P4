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

    public Runner(double centerX, double centerY, double rad, Paint inColor, int ID) {
        super(centerX, centerY, rad, inColor, ID);
        super.setSpeedX(2);
        super.setSpeedY(2);
        super.setCanTeleport(true);
    }

    @Override
    public String toString() {
        return "ID#" + getNumID();
    }
}
