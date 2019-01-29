package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    private static final String FILE = "paddle.gif";
    private static int WIDTH = 175;

    private ImageView myImageView;

    public Paddle(){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(FILE));
        myImageView = new ImageView(image);
        myImageView.setFitWidth(WIDTH);
    }

    public ImageView getMyImageView() {
        return myImageView;
    }
}
