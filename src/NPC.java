import java.awt.Rectangle;
import java.awt.Color;

public abstract class NPC extends GameItem {

    private int speed;
    private boolean canTeleport;

    public NPC(double rad, int num, Rectangle b, Color inColor) {
        super(rad, num, b, inColor);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getCanTeleport() {
        return canTeleport;
    }

    public void setCanTeleport(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }
}
