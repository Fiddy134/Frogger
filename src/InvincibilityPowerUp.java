import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class InvincibilityPowerUp extends PowerUp {
    
    public InvincibilityPowerUp(double x, double y) {
        super(x, y);
        
        Circle circle = new Circle(SIZE, Color.GOLD);
        node = circle;
        updateNodePosition();
    }
    
    @Override
    public void applyEffect(Frog player) {
        player.enableInvincibility();
    }
}