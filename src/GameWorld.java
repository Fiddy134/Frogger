import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * logik och spelvärld
 */
public class GameWorld {
    private Pane root;
    private Frog player;
    private Level currentLevel;
    private List<Level> levels = new ArrayList<>();
    private AnimationTimer gameLoop;
    private Random random = new Random();
    
    // variabler
    private IntegerProperty score = new SimpleIntegerProperty(0);
    private IntegerProperty lives = new SimpleIntegerProperty(3);
    private IntegerProperty currentLevelIndex = new SimpleIntegerProperty(0);
    
    // callback när spelet är slut
    private Consumer<Integer> gameOverCallback;
    
    // Powerup-timer
    private long lastPowerupTime = 0;
    
    public GameWorld(double width, double height) {
        // root pane
        root = new Pane();
        root.setPrefSize(width, height);
        root.setStyle("-fx-background-color: black;");
        
        // Skapa spelaren (lägger inte till den ännu)
        player = new Frog(width / 2, height - 60);
        
        // Skapa banor
        levels.add(new RoadLevel(root, width, height));
        levels.add(new RiverLevel(root, width, height));
        
        // Starta med första banan
        loadLevel(0);
        
        // Lägg till spelaren EFTER banan (så den hamnar överst)
        root.getChildren().add(player.getNode());
        
        // Skapa spel loopen
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                
                double elapsedSeconds = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                
                update(elapsedSeconds);
            }
        };
    }
    
    private void loadLevel(int levelIndex) {
        // Rensa skärmen
        if (currentLevel != null) {
            root.getChildren().removeAll(currentLevel.getNodes());
        }
        
        // Sätt nuvarande bana
        currentLevelIndex.set(levelIndex);
        currentLevel = levels.get(levelIndex);
        
        // Lägg till banobjekt till root
        root.getChildren().addAll(currentLevel.getNodes());
        
        // Reseta spelarens position
        player.reset(root.getPrefWidth() / 2, root.getPrefHeight() - 60);
        
        // sätt spelar ref
	currentLevel.setPlayerReference(player);
    }
    
    public void startGame() {
        gameLoop.start();
    }
    
    public void stopGame() {
        gameLoop.stop();
    }
    
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
            case UP:
                player.moveUp();
                break;
            case S:
            case DOWN:
                player.moveDown();
                break;
            case A:
            case LEFT:
                player.moveLeft();
                break;
            case D:
            case RIGHT:
                player.moveRight();
                break;
            default:
                break;
        }
    }
    
    private void update(double deltaTime) {
        player.update(deltaTime);
        
        // Uppdatera alla spelelement
        currentLevel.update(deltaTime);
        
       
        checkCollisions();
        
        generatePowerups();
        
        checkWinCondition();
    }
    
    private void checkCollisions() {
        // Kontrollera kollision med fiender
        for (Enemy enemy : currentLevel.getEnemies()) {
            if (player.isInvincible()) {
                continue; // Skippa kollision om spelaren är odödlig
            }
            
            if (player.collidesWith(enemy)) {
                // förlora ett liv när träffad
                lives.set(lives.get() - 1);
                
                if (lives.get() <= 0) {
                    stopGame();
                    if (gameOverCallback != null) {
                        gameOverCallback.accept(score.get());
                    }
                    return;
                }
              
                player.reset(root.getPrefWidth() / 2, root.getPrefHeight() - 60);
                return;
            }
        }
        
        // Kontrollera att man tar powerups
        Iterator<PowerUp> it = currentLevel.getPowerUps().iterator();
        while (it.hasNext()) {
            PowerUp powerUp = it.next();
            if (player.collidesWith(powerUp)) {
                powerUp.applyEffect(player);
                
                // Ta bort powerup från skärmen
                root.getChildren().remove(powerUp.getNode());
                it.remove();
                
                score.set(score.get() + 50);
            }
        }
    }
    
    private void generatePowerups() {
        long currentTime = System.currentTimeMillis();
        
        // Generera en ny powerup varje 5 sekund med 10% sannolikhet
        if (currentTime - lastPowerupTime > 5000 && random.nextDouble() < 0.1) {
            lastPowerupTime = currentTime;
            
            PowerUp powerUp;
            double x = random.nextDouble() * (root.getPrefWidth() - 30);
            double y = 100 + random.nextDouble() * (root.getPrefHeight() - 200);
            
            // Slumpmässigt powerup
            if (random.nextBoolean()) {
                powerUp = new InvincibilityPowerUp(x, y);
            } else {
                powerUp = new SpeedBoostPowerUp(x, y);
            }
            
            currentLevel.addPowerUp(powerUp);
            root.getChildren().add(powerUp.getNode());
        }
    }
    
    private void checkWinCondition() {
        if (player.getY() <= 20) {
            score.set(score.get() + 100);
            
            // Ladda nästa nivå eller gå tillbaka till första
            int nextLevel = (currentLevelIndex.get() + 1) % levels.size();
            loadLevel(nextLevel);
            
            // Se till att spelaren fortfarande är synlig ovanpå den nya banan
            root.getChildren().remove(player.getNode());
            root.getChildren().add(player.getNode());
        }
    }
    
    public Parent getRoot() {
        return root;
    }
    
    public IntegerProperty scoreProperty() {
        return score;
    }
    
    public IntegerProperty livesProperty() {
        return lives;
    }
    
    public IntegerProperty levelProperty() {
        return currentLevelIndex;
    }
    
    public void setOnGameOver(Consumer<Integer> callback) {
        this.gameOverCallback = callback;
    }
}