package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle extends Sprite {
    private static final String FILE = "paddle.gif";
    private static int WIDTH = 175;

    public Paddle(){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(FILE));
        myImageView = new ImageView(image);
        myImageView.setFitWidth(WIDTH);
    }

}
