import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.List;

public class HighScoreScene {
    private Scene scene;
    private Main app;
    private ScoreManager scoreManager;
    private VBox scoresBox;
    
    public HighScoreScene(Main app, ScoreManager scoreManager) {
        this.app = app;
        this.scoreManager = scoreManager;
        createHighScoreScene();
    }
    
    private void createHighScoreScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(800, 600);
        
        Text titleText = new Text("HIGHSCORES");
        titleText.setFont(Font.font(40));
        
        // Container för poänglistan
        scoresBox = new VBox(10);
        scoresBox.setAlignment(Pos.CENTER);
        
        Button backButton = new Button("Back to menu");
        backButton.setPrefWidth(200);
        backButton.setPrefHeight(40);
        backButton.setFont(Font.font(16));
        backButton.setOnAction(event -> app.showMenu());
        
        root.getChildren().addAll(titleText, scoresBox, backButton);
        
        scene = new Scene(root);
        
        updateScores();
    }
    
    public void updateScores() {
        scoresBox.getChildren().clear();
        
        List<ScoreManager.HighScore> highScores = scoreManager.getHighScores();
        
        if (highScores.isEmpty()) {
            Text noScoresText = new Text("Inga highscores än!");
            noScoresText.setFont(Font.font(20));
            scoresBox.getChildren().add(noScoresText);
        } else {
            // Lägg till header
            Text headerText = new Text(String.format("%-20s %10s", "NAMN", "POÄNG"));
            headerText.setFont(Font.font("Monospaced", 20));
            scoresBox.getChildren().add(headerText);
            
            // Lägg till alla highscores
            for (int i = 0; i < Math.min(10, highScores.size()); i++) {
                ScoreManager.HighScore score = highScores.get(i);
                Text scoreText = new Text(String.format("%-20s %10d", score.getName(), score.getScore()));
                scoreText.setFont(Font.font("Monospaced", 18));
                scoresBox.getChildren().add(scoreText);
            }
        }
    }
    
    public Scene getScene() {
        return scene;
    }
}