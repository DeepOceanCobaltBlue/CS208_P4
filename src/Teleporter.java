import javafx.scene.paint.Paint;

public class Teleporter extends GameItem {

    public Teleporter() {
        super();
    }
    public Teleporter(double startX, double startY, double rad, Paint inColor) {
        super(startX, startY, rad, inColor);
        super.setNumID(0);
    }

    public Teleporter(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor);
        super.setNumID(ID);
    }
}
