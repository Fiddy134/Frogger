public abstract class PowerUp extends GameObject {
    protected static final int SIZE = 15;
    
    public PowerUp(double x, double y) {
        super(x, y);
    }
 
    public abstract void applyEffect(Frog player);
    
    @Override
    public void update(double deltaTime) {
       
    }
}