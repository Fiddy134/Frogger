import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

/**
 * Representerar själva spelskärmen
 */
public class GameScene {
    private Scene scene;
    private Main app;
    private GameWorld gameWorld;
    private ScoreManager scoreManager;
    private Label scoreLabel;
    private Label livesLabel;
    private Label levelLabel;
    private String playerName;
    
    public GameScene(Main app, ScoreManager scoreManager) {
        this.app = app;
        this.scoreManager = scoreManager;
        
        // Fråga efter spelarens namn
        askForPlayerName();
        
        // Skapa spelvärlden
        this.gameWorld = new GameWorld(800, 600);
        
        createGameScene();
        
        // Starta spelet
        gameWorld.startGame();
    }
    
    private void askForPlayerName() {
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("Spelarnamn");
        dialog.setHeaderText("Skriv in ditt namn");
        dialog.setContentText("Namn:");
        
        Optional<String> result = dialog.showAndWait();
        playerName = result.orElse("Player");
    }
    
    private void createGameScene() {
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);
        
        // HUD (Heads-Up Display) för poäng och liv
        HBox hud = new HBox(20);
        scoreLabel = new Label("Poäng: 0");
        scoreLabel.setFont(Font.font(18));
        scoreLabel.setTextFill(Color.BLACK);
        
        livesLabel = new Label("Liv: 3");
        livesLabel.setFont(Font.font(18));
        livesLabel.setTextFill(Color.BLACK);
        
        levelLabel = new Label("Nivå: 1");
        levelLabel.setFont(Font.font(18));
        levelLabel.setTextFill(Color.BLACK);
        
        hud.getChildren().addAll(scoreLabel, livesLabel, levelLabel);
        
        // Lägg till HUD och spelvärlden till root
        root.setTop(hud);
        root.setCenter(gameWorld.getRoot());
        
        // Skapa scenen
        scene = new Scene(root);
        
        // Hantera tangentbordsinput
        scene.setOnKeyPressed(this::handleKeyPress);
        
        // Uppdatera HUD när värden ändras
        gameWorld.scoreProperty().addListener((obs, oldVal, newVal) -> 
            Platform.runLater(() -> scoreLabel.setText("Poäng: " + newVal)));
        
        gameWorld.livesProperty().addListener((obs, oldVal, newVal) -> 
            Platform.runLater(() -> livesLabel.setText("Liv: " + newVal)));
        
        gameWorld.levelProperty().addListener((obs, oldVal, newVal) -> 
            Platform.runLater(() -> levelLabel.setText("Nivå: " + newVal)));
        
        // Lyssna på game over
        gameWorld.setOnGameOver(score -> Platform.runLater(() -> app.gameOver(score)));
    }
    
    private void handleKeyPress(KeyEvent event) {
        gameWorld.handleKeyPress(event);
        
        switch (event.getCode()) {
            case ESCAPE:
                app.showMenu();
                gameWorld.stopGame();
                break;
            default:
                break;
        }
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public String getPlayerName() {
        return playerName;
    }
}