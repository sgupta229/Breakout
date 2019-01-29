package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends Sprite{
    private static final String FILE = "brick1.gif";

    public Brick() {
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(FILE));
        myImageView = new ImageView(image);
    }
}