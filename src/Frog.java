import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.FillTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/**
 * Representerar spelarens grodkaraktär
 */
public class Frog extends GameObject {
    private static final int SIZE = 60;
    private static final double DEFAULT_MOVE_DISTANCE = 40;
    
    private double moveDistance = DEFAULT_MOVE_DISTANCE;
    private boolean invincible = false;
    private boolean speedboost = false;
    private long invincibilityEndTime = 0;
    private long speedBoostEndTime = 0;
    
    public Frog(double x, double y) {
        super(x, y);
        
        Image frogImage = new Image("file:/home/vinro908/TDDE10/grodanboll.png");
        ImageView imageView = new ImageView(frogImage);
        
        // Ställ in storleken
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        imageView.setPreserveRatio(true);
        
       // Rectangle rect = new Rectangle(SIZE, SIZE, Color.GREEN);
        //node = rect;
        
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
        
        // Kontrollera om invincibility har löpt ut
        if (invincible && currentTime > invincibilityEndTime) {
            disableInvincibility();
        }
        
        // Kontrollera om speed boost har löpt ut
        if (moveDistance > DEFAULT_MOVE_DISTANCE && currentTime > speedBoostEndTime) {
            disableSpeedBoost();
        }
    }
    
    /**
     * Aktiverar odödlighet i 5 sekunder
     */
    public void enableInvincibility() {
        invincible = true;
        invincibilityEndTime = System.currentTimeMillis() + 5000;
        
        // Visuell effekt
        ImageView imageView = (ImageView) node;
        javafx.scene.effect.ColorAdjust golden = new javafx.scene.effect.ColorAdjust();
        golden.setBrightness(0.2);
        golden.setSaturation(0.8);
        golden.setHue(2.1);
        imageView.setEffect(golden);
    }
    
    /**
     * Inaktiverar odödlighet
     */
    public void disableInvincibility() {
        invincible = false;
        
        // Återställ utseende
        ImageView imageView = (ImageView) node;
        imageView.setEffect(null);
    }
    
    /**
     * Aktiverar hastighetsboost i 5 sekunder
     */
    public void enableSpeedBoost() {
        moveDistance = DEFAULT_MOVE_DISTANCE * 2;
        speedBoostEndTime = System.currentTimeMillis() + 5000;
        
        // Visuell effekt
        ImageView imageView = (ImageView) node;
        javafx.scene.effect.Glow glow = new javafx.scene.effect.Glow();
        glow.setLevel(2.0);
        imageView.setEffect(glow);
    }
    
    /**
     * Inaktiverar hastighetsboost
     */
    public void disableSpeedBoost() {
        moveDistance = DEFAULT_MOVE_DISTANCE;
        
        // Återställ utseende
        ImageView imageView = (ImageView) node;
        imageView.setEffect(null);
    }
    
    /**
     * Kontrollerar om spelaren är odödlig
     */
    public boolean isInvincible() {
        return invincible;
    }
}