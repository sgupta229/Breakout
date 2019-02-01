package game;

public class Brick extends Sprite{

    public Brick(String filename, double width, double sceneHeight) {
        super(filename);

        //In reality we should use the dimensions of the scene to determine the width
        myImageView.setFitWidth(width / 7);
        myImageView.setFitHeight(myImageView.getBoundsInLocal().getHeight()*2);
    }

    public void handleCollision() {
        setX(getX() + 10000);
    }
}