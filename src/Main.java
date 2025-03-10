import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Huvudklassen för Frogger-spelet. Ansvarar för att starta applikationen
 * och hantera navigering mellan olika scener.
 */
public class Main extends Application {
    private Stage primaryStage;
    private MenuScene menuScene;
    private GameScene gameScene;
    private HighScoreScene highScoreScene;
    private ScoreManager scoreManager;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.scoreManager = new ScoreManager();
        
        // Skapa scener
        menuScene = new MenuScene(this);
        highScoreScene = new HighScoreScene(this, scoreManager);
        
        // Konfigurera primärfönstret
        primaryStage.setTitle("Chronicles of Grodan Boll");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene.getScene());
        primaryStage.show();
    }
    
    /**
     * Startar ett nytt spel
     */
    public void startNewGame() {
        gameScene = new GameScene(this, scoreManager);
        primaryStage.setScene(gameScene.getScene());
    }
    
    /**
     * Visar huvudmenyn
     */
    public void showMenu() {
        primaryStage.setScene(menuScene.getScene());
    }
    
    /**
     * Visar highscore-listan
     */
    public void showHighScores() {
        highScoreScene.updateScores();
        primaryStage.setScene(highScoreScene.getScene());
    }
    
    /**
     * Avslutar spelet
     */
    public void exitGame() {
        primaryStage.close();
    }
    
    /**
     * Registrerar en ny poäng och visar highscore-skärmen
     * @param score Spelarens poäng
     */
    public void gameOver(int score) {
        String playerName = gameScene.getPlayerName();
        scoreManager.addScore(playerName, score);
        scoreManager.saveScores();
        showHighScores();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}