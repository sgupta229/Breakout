package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends Sprite{

    public Brick(String filename) {
        super(filename);

        //In reality we should use the dimensions of the scene to determine the width
        myImageView.setFitWidth(myImageView.getBoundsInLocal().getWidth()*1.5);
        myImageView.setFitHeight(myImageView.getBoundsInLocal().getHeight()*2);
    }

    public void handleCollision() {
        setX(getX() + 10000);
    }
}