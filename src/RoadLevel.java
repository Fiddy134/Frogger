import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

/**
 * Vägbana med bilar som fiender
 */
public class RoadLevel extends Level {
    
    public RoadLevel(Pane root, double width, double height) {
        super(root, width, height);
    }
    
    @Override
    protected void createBackground() {
        // Skapa asfaltsbakgrund
        Rectangle background = new Rectangle(width, height, Color.DARKGRAY);
        nodes.add(background);
        
        // Skapa vita linjer
        for (int y = 60; y < height - 60; y += 80) {
            for (int x = 0; x < width; x += 80) {
                Rectangle line = new Rectangle(40, 10, Color.WHITE);
                line.setTranslateX(x);
                line.setTranslateY(y);
                nodes.add(line);
            }
        }
    }
    
    @Override
    protected void createLevelElements() {
        // Skapa ett antal vägfiler med bilar i olika riktningar
        for (int i = 0; i < 5; i++) {
            double y = 80 + i * 80;
            boolean movingRight = i % 2 == 0;
            
            // Skapa 3-5 bilar per fil
            int numCars = 3 + random.nextInt(3);
            for (int j = 0; j < numCars; j++) {
                double x;
                if (movingRight) {
                    x = random.nextDouble() * width - 100;
                } else {
                    x = random.nextDouble() * width + 100;
                }
                
                Car car = new Car(x, y, movingRight);
                enemies.add(car);
                nodes.add(car.getNode());
            }
        }
    }
}