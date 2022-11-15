import javafx.scene.paint.Paint;

public abstract class NPC extends GameItem {
    private int speedX;
    private int speedY;
    private boolean canTeleport;

    public NPC() {
        super();
        this.speedX = 1;
        this.speedY = 1;
        this.canTeleport = false;
    }

    public NPC(double startX, double startY, double rad, Paint inColor, int ID) {
        // public GameItem(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor, ID);
        this.speedX = 1;
        this.speedY = 1;
        this.canTeleport = false;

    }
    public NPC(double startX, double startY, double rad, Paint inColor, int ID, int speedX, int speedY, boolean tele) {
        // public GameItem(double startX, double startY, double rad, Paint inColor, int ID) {
        super(startX, startY, rad, inColor, ID);
        this.speedX = speedX;
        this.speedY = speedY;
        this.canTeleport = tele;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speed) {
        this.speedX = speed;
    }
    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speed) {
        this.speedY = speed;
    }

    public boolean getCanTeleport() {
        return canTeleport;
    }

    public void setCanTeleport(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }
}
