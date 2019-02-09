package game;

public class IndestructibleBrick extends Brick {
    public IndestructibleBrick(String filename, double sceneWidth){
        super(filename, sceneWidth);
    }

    @Override
    public void handleCollision() { }
}
