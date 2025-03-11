import java.io.*;
import java.util.*;

public class ScoreManager {
    private static final String HIGHSCORE_FILE = "highscores.dat";
    private List<HighScore> highScores;
    
    public ScoreManager() {
        highScores = new ArrayList<>();
        loadScores();
    }
    
    public void addScore(String name, int score) {
        highScores.add(new HighScore(name, score));
        sortScores();
    }
    
    /**
     * Sorterar poänglistan i fallande ordning
     */
    private void sortScores() {
        Collections.sort(highScores, Comparator.comparingInt(HighScore::getScore).reversed());
    }
    
    /**
     * Laddar poäng från fil
     */
    public void loadScores() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE))) {
            highScores = (List<HighScore>) in.readObject();
        } catch (FileNotFoundException e) {
            highScores = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Fel vid laddning av highscores: " + e.getMessage());
            highScores = new ArrayList<>();
        }
    }
    
    /**
     * Sparar poäng till fil
     */
    public void saveScores() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE))) {
            out.writeObject(highScores);
        } catch (IOException e) {
            System.err.println("Fel vid sparande av highscores: " + e.getMessage());
        }
    }
    
    /**
     * Hämtar listan med highscores
     * 
     */
    public List<HighScore> getHighScores() {
        return new ArrayList<>(highScores);
    }
    
    /**
     * Klass för att representera en highscore-post
     * Fråga Magnus om man bör gör en egen klass som är HighScore?
     */
    public static class HighScore implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int score;
        
        public HighScore(String name, int score) {
            this.name = name;
            this.score = score;
        }
        
        public String getName() {
            return name;
        }
        
        public int getScore() {
            return score;
        }
    }
}