import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public abstract class GameItem extends Circle {
    private int numID;
    public GameItem() {
        super();
    }
    public GameItem(double startX, double startY, double rad, Paint inColor) {
        super(startX, startY, rad, inColor);
        this.numID = 0;
    }

    public GameItem(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor);
        this.numID = ID;
    }

    public int getNumID() {
        return numID;
    }
    public void setNumID(int num) {
        numID = num;
    }
}
