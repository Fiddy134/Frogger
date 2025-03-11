import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpeedBoostPowerUp extends PowerUp {
    
    public SpeedBoostPowerUp(double x, double y) {
        super(x, y);
        
        Circle circle = new Circle(SIZE, Color.CYAN);
        node = circle;
        updateNodePosition();
    }
    
    @Override
    public void applyEffect(Frog player) {
        player.enableSpeedBoost();
    }
}