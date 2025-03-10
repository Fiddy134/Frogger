import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Flodbana med ormar som fiender
 */
public class RiverLevel extends Level {
    
    public RiverLevel(Pane root, double width, double height) {
        super(root, width, height);
    }
    
    @Override
    protected void createBackground() {
        // Skapa vattenbakgrund
        Rectangle background = new Rectangle(width, height, Color.DARKBLUE);
        nodes.add(background);
        
        // Skapa flytande plattformar
        for (int y = 60; y < height - 60; y += 100) {
            for (int x = 0; x < width; x += 120) {
                Rectangle platform = new Rectangle(80, 30, Color.BROWN);
                platform.setTranslateX(x + (y % 200 == 0 ? 40 : 0)); // Förskjutna rader
                platform.setTranslateY(y);
                nodes.add(platform);
            }
        }
    }
    
    @Override
    protected void createLevelElements() {
        // Skapa ormar som fiender
        for (int i = 0; i < 3; i++) {
            double x = 100 + i * 200;
            double y = 100 + i * 100;
            
            Snake snake = new Snake(x, y, null); // Kommer att sätta player-referensen senare
            enemies.add(snake);
            nodes.add(snake.getNode());
        }
    }
    
    /**
     * Sätter spelarreferensen för ormarna
     */
    @Override
    public void setPlayerReference(Frog player) {
        
        // Uppdatera alla ormar med spelarreferensen
        for (Enemy enemy : enemies) {
	    enemy.setTarget(player);
        }
    }
}