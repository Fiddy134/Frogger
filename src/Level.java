import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Basklass för alla banor i spelet
 */
public abstract class Level {
    protected Pane root;
    protected double width;
    protected double height;
    protected List<Node> nodes = new ArrayList<>();
    protected List<Enemy> enemies = new ArrayList<>();
    protected List<PowerUp> powerUps = new ArrayList<>();
    protected Random random = new Random();
    
    public Level(Pane root, double width, double height) {
	this.root = root;
        this.width = width;
        this.height = height;
        
        // Skapa bakgrunden
        createBackground();
        
        // Skapa målområdet
        createFinishLine();
        
        // Skapa specifika banelement
        createLevelElements();
    }

    public void setPlayerReference(Frog player) {
    }
    
    /**
     * Skapar bakgrunden för banan
     */
    protected abstract void createBackground();
    
    /**
     * Skapar mållinjen högst upp på banan
     */
    private void createFinishLine() {
        Rectangle finishLine = new Rectangle(width, 20, Color.LIMEGREEN);
        finishLine.setTranslateY(0);
        nodes.add(finishLine);
    }
    
    /**
     * Skapar banespecifika element som hinder och fiender
     */
    protected abstract void createLevelElements();
    
    /**
     * Uppdaterar alla element i banan
     */
    public void update(double deltaTime) {
        // Uppdatera alla fiender
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
        
        // Uppdatera alla powerups
        for (PowerUp powerUp : powerUps) {
            powerUp.update(deltaTime);
        }
    }
    
    /**
     * Lägger till en powerup till banan
     */
    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }
    
    /**
     * Hämtar alla noder för rendering
     */
    public List<Node> getNodes() {
        return nodes;
    }
    
    /**
     * Hämtar alla fiender i banan
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    /**
     * Hämtar alla powerups i banan
     */
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}