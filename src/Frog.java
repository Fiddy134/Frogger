import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Frog extends GameObject {
    private static final int SIZE = 60;
    private static final double DEFAULT_MOVE_DISTANCE = 40;
    
    private double moveDistance = DEFAULT_MOVE_DISTANCE;
    private boolean invincible = false;
    private long invincibilityEndTime = 0;
    private long speedBoostEndTime = 0;
    
    public Frog(double x, double y) {
        super(x, y);
        
        Image frogImage = new Image("file:/home/vinro908/TDDE10/grodanboll.png");
        ImageView imageView = new ImageView(frogImage);
        
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        imageView.setPreserveRatio(true);
        
        node = imageView;
        updateNodePosition();
    }
    
    public void moveUp() {
        setPosition(x, Math.max(0, y - moveDistance));
    }
    
    public void moveDown() {
        setPosition(x, y + moveDistance);
    }
    
    public void moveLeft() {
        setPosition(Math.max(0, x - moveDistance), y);
    }
    
    public void moveRight() {
        setPosition(x + moveDistance, y);
    }
    
    public void reset(double x, double y) {
        setPosition(x, y);
        disableInvincibility();
        disableSpeedBoost();
    }
    
    @Override
    public void update(double deltaTime) {
        long currentTime = System.currentTimeMillis();
        
        if (invincible && currentTime > invincibilityEndTime) {
            disableInvincibility();
        }
        
        if (moveDistance > DEFAULT_MOVE_DISTANCE && currentTime > speedBoostEndTime) {
            disableSpeedBoost();
        }
    }
    
    public void enableInvincibility() {
        invincible = true;
        invincibilityEndTime = System.currentTimeMillis() + 5000;
        
        ImageView imageView = (ImageView) node;
        javafx.scene.effect.ColorAdjust golden = new javafx.scene.effect.ColorAdjust();
        golden.setBrightness(0.2);
        golden.setSaturation(0.8);
        golden.setHue(2.1);
        imageView.setEffect(golden);
    }
    
    public void disableInvincibility() {
        invincible = false;
        
        ImageView imageView = (ImageView) node;
        imageView.setEffect(null);
    }
    public void enableSpeedBoost() {
        moveDistance = DEFAULT_MOVE_DISTANCE * 2;
        speedBoostEndTime = System.currentTimeMillis() + 5000;
        
        ImageView imageView = (ImageView) node;
        javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow();
        glow.setLevel(2.0);
        imageView.setEffect(glow);
    }
    
    public void disableSpeedBoost() {
        moveDistance = DEFAULT_MOVE_DISTANCE;
        
        ImageView imageView = (ImageView) node;
        imageView.setEffect(null);
    }
    
    public boolean isInvincible() {
        return invincible;
    }
}