import javafx.scene.paint.Paint;

public class Teleporter extends GameItem {

    public Teleporter() {
        super();
    }
    public Teleporter(double centerX, double centerY, double rad, Paint inColor) {
        super(centerX, centerY, rad, inColor);
        super.setNumID(0);
    }

    public Teleporter(double centerX, double centerY, double rad, Paint inColor, int ID) {
        super(centerX, centerY, rad, inColor);
        super.setNumID(ID);
    }
}
