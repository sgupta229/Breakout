package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends Sprite{

    public Brick(String filename) {
        super(filename);
        myImageView.setFitWidth(myImageView.getBoundsInLocal().getWidth()*1.5);
        myImageView.setFitHeight(myImageView.getBoundsInLocal().getHeight()*1.5);
    }

    @Override
    public void handleCollision() {
        setX(getX() + 10000);
    }
}