/*  Christopher Peters - Wrote class in accordance with UML design
    Josue Florian - Implemented toString(), equals(), and hashCode() methods
    The overridden hashCode() calculates a hashCode from the key's numID and color.
 */
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public abstract class GameItem extends Circle {
    private int numID;
    private String color;
    public GameItem() {
        super();
    }
    public GameItem(double centerX, double centerY, double rad, Paint inColor) {
        super(centerX, centerY, rad, inColor);
        this.numID = 0;
        this.color = inColor.toString();
    }

    public GameItem(double centerX, double centerY, double rad, Paint inColor, int ID) {
        super(centerX, centerY, rad, inColor);
        this.numID = ID;
        this.color = inColor.toString();
    }

    public int getNumID() {
        return numID;
    }
    public void setNumID(int num) {
        numID = num;
    }

    @Override
    public String toString() {
        return "GameItem ID: " + this.numID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(obj.getClass() == this.getClass()) {
            GameItem gameItem = (GameItem) obj;
            return (this.numID == gameItem.numID) && (this.color.equals(gameItem.color));
        }
        return false;
    }

    @Override
    public int hashCode() {
        //modulo hash
        int result = 7;
        result = 23 * result + this.numID;

        //multiplicative string hash
        int prime = 3;
        for(int i = 0; i < this.color.length(); i++) {
            char c =  this.color.charAt(i);
            result = (result * prime) + c + this.numID;
        }
        return Math.abs(result);
    }
}
