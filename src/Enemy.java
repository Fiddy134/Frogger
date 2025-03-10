/**
 * Basklass för alla fiender i spelet
 */
public abstract class Enemy extends GameObject {
    protected double speed;
    
    public Enemy(double x, double y) {
        super(x, y);
    }

    public void setTarget(Frog target) {
    }    
    
    /**
     * Returnerar fiendens hastighet
     * @return Hastigheten i pixlar per sekund
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * Sätter fiendens hastighet
     * @param speed Hastigheten i pixlar per sekund
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}