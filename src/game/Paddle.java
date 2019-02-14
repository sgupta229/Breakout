package game;

/**
 * Paddle Sprite that is controlled through BreakerGame (specifically mouseHandle())
 */
public class Paddle extends Sprite{
    private static final int PADDLE_WIDTH = 175;

    /**
     * constructs paddle and changes the width to something more appropriate than paddle.gif's default
     * @param filename
     */
    public Paddle(String filename){
        super(filename);
        getMyImageView().setFitWidth(PADDLE_WIDTH);
    }
}
