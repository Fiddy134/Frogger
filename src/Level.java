import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        
        createBackground();
        
        createFinishLine();
        
        createLevelElements();
    }

    public void setPlayerReference(Frog player) {
    }
    
    protected abstract void createBackground();
    

    private void createFinishLine() {
        Rectangle finishLine = new Rectangle(width, 20, Color.LIMEGREEN);
        finishLine.setTranslateY(0);
        nodes.add(finishLine);
    }
    
   
    protected abstract void createLevelElements();
    
    /**
     * Uppdaterar alla element i banan samt fiender
     */
    public void update(double deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
        
        for (PowerUp powerUp : powerUps) {
            powerUp.update(deltaTime);
        }
    }
    
    //Lägger till en powerup till banan
  
    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }
    
    //Hämtar alla noder 
     
    public List<Node> getNodes() {
        return nodes;
    }
    
    //Hämtar alla fiender 
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    //Hämtar alla powerups 
     
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}