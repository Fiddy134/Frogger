public abstract class Enemy extends GameObject {
    protected double speed;
    
    public Enemy(double x, double y) {
        super(x, y);
    }

    public void setTarget(Frog target) {
    }    
    
    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}