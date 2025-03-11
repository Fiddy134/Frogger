import javafx.animation.FadeTransition;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class WinScreen {
    private static final String WIN_TEXT = "YOU WIN";
    private Pane parent;
    private HBox container;
    
    public WinScreen(Pane parent) {
        this.parent = parent;
        container = new HBox();
        container.setTranslateX(300);
        container.setTranslateY(250);
    }
    
    public void show() {
        parent.getChildren().add(container);
        
        // Skapa animerad text
        for (int i = 0; i < WIN_TEXT.toCharArray().length; i++) {
            char letter = WIN_TEXT.charAt(i);
            Text text = new Text(String.valueOf(letter));
            text.setFont(Font.font(48));
            text.setOpacity(0);
            container.getChildren().add(text);
            
            // Skapa fade-in animation
            FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.15));
            ft.play();
        }
    }
}