package game;

public class Paddle extends Sprite{
    private static final int PADDLE_WIDTH = 175;
    private static final int PADDLE_SPEED = 50;

    public Paddle(String filename){
        super(filename);
        myImageView.setFitWidth(PADDLE_WIDTH);
    }

    public static int getSpeed() {
        return PADDLE_SPEED;
    }
}
