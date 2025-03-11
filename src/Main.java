import javafx.application.Application;
import javafx.stage.Stage;

/**
 * starta applikationen
 * hantera navigering mellan olika scener.
 * Fråga Magnus: Komposition vs arv här, är detta inom kursens ramar? 
 */
public class Main extends Application {
    private Stage primaryStage;
    private MenuScene menuScene;
    private GameScene gameScene;
    private HighScoreScene highScoreScene;
    private ScoreManager scoreManager;
    
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.scoreManager = new ScoreManager();
        
        menuScene = new MenuScene(this);
        highScoreScene = new HighScoreScene(this, scoreManager);
        
        primaryStage.setTitle("Chronicles of Grodan Boll");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene.getScene());
        primaryStage.show();
    }
    
    public void startNewGame() {
        gameScene = new GameScene(this, scoreManager);
        primaryStage.setScene(gameScene.getScene());
    }
    
    /**
     * huvudmenyn
     */
    public void showMenu() {
        primaryStage.setScene(menuScene.getScene());
    }
    
    public void showHighScores() {
        highScoreScene.updateScores();
        primaryStage.setScene(highScoreScene.getScene());
    }
    
    public void exitGame() {
        primaryStage.close();
    }
    
    /**
     * Registrerar ny poäng och visar highscoreskärmen
     *
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