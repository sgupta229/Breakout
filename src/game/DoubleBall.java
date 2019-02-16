package game;

/**
 * This class is for the DoubleBall powerup, which is the green powerup in the game that, when caught, makes another ball appear for an infinite
 * amount of time (until the player loses the ball). This class handles adding the ball to the game.
 */

public class DoubleBall extends Powerup {

    /**
     * The constructor for this class just calles the constructor in the Powerup class.
     * @param filename the filename for the image of the powerup
     */

    public DoubleBall (String filename) {
         super(filename);
    }

    /**
     * This method is responsible for retrieving the second ball. If the double ball powerup is activated, this method makes the second ball
     * visible and puts it in the frame of the game.
     * @param myBall the primary ball
     * @param secondBall the new ball after getting the powerup
     */

    public void retrieveBall(Ball myBall, Ball secondBall) {
        secondBall.setPosition(myBall.getX(), myBall.getY());
        secondBall.getMyImageView().setVisible(true);
        secondBall.changeSpeed(2.75);
    }

    /**
     * This method handles what happens when the double ball powerup collides with the paddle. This method calls the retrieveBall
     * method.
     * @param myPaddle The paddle in the game
     * @param myBall the primary blue ball
     * @param secondBall The pinkish ball in the game if the DoubleBall powerup is activated.
     */

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall, Ball secondBall) {
        super.paddleCollision(myPaddle, myBall, secondBall);
        if(this.getPowerType().equals("powerup_twoball.gif")) {
            retrieveBall(myBall, secondBall);
        }
    }
}
