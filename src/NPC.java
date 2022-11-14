import javafx.scene.paint.Paint;

public abstract class NPC extends GameItem {
    private int speed;
    private boolean canTeleport;

    public NPC() {
        super();
        this.speed = 1;
        this.canTeleport = false;
    }

    public NPC(double startX, double startY, double rad, Paint inColor, int ID) {
        // public GameItem(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor, ID);
        this.speed = 1;
        this.canTeleport = false;

    }
    public NPC(double startX, double startY, double rad, Paint inColor, int ID, int speed, boolean tele) {
        // public GameItem(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor, ID);
        this.speed = speed;
        this.canTeleport = tele;
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
