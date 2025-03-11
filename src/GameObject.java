import javafx.scene.Node;

public abstract class GameObject {
    protected Node node;
    protected double x;
    protected double y;
    
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Node getNode() {
        return node;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        updateNodePosition();
    }
    
    public boolean collidesWith(GameObject other) {
        return node.getBoundsInParent().intersects(other.getNode().getBoundsInParent());
    }
    
    protected void updateNodePosition() {
        node.setTranslateX(x);
        node.setTranslateY(y);
    }
    
    public abstract void update(double deltaTime);
}