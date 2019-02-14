package game;

/**
 * Class for generic bricks with no special abilities
 */
public class Brick extends Sprite{
    private int typeNumber;

    /** Constructor for generic Bricks
     *
     * @param filename - filename for the brick image
     * @param width - scene width, used to size up bricks relative to scene width
     */
    public Brick(String filename, double width) {
        super(filename);
        typeNumber = Integer.parseInt(filename.substring(5, 6));
        getMyImageView().setFitWidth(width / 10);
        getMyImageView().setFitHeight(getMyImageView().getBoundsInLocal().getHeight()*1.5);
    }

    /**
     *
     * @return brick "type" i.e. the number in brickX.gif that constitutes the brick's specific ImageView
     */
    public int getBrickType(){
        return this.typeNumber;
    }

    public void setBrickType(int num) {
        this.typeNumber = num;
    }

    /**
     * when a generic brick is hit by the ball we set its X to be 10,000, removing it from the screen
     */
    public void handleCollision() {
        setX(getX() + 10000);
    }
}