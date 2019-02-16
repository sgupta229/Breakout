package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * This class handles the functionality of the FasterBall powerup. This powerup is the red powerup that makes the blue ball faster for 10
 * seconds.
 */

public class FasterBall extends Powerup {

    /**
     * This constructor just calls super for the constructor in the Powerup class.
     * @param filename the filename of the image of the powerup.
     */

    public FasterBall(String filename) {
        super(filename);
    }

    /**
     * Changes the ball speed back to normal after 10 seconds have passed.
     * @param myBall the blue ball in the game.
     */

    public void resetBallSpeed(Ball myBall) {
        myBall.changeSpeed(2.75);
    }

    /**
     * Changes the ball speed to be faster.
     * @param myBall the blue ball in the game.
     */

    public void fastBall(Ball myBall) {
        myBall.changeSpeed(4.0);
    }

    /**
     * Overrides the paddleCollision method in the Powerup class. This method calls super, which simply makes the powerup
     * that was caught invisible. Then the method calls fastBall to make the ball faster and then, after 10 seconds,
     * calls resetBallSpeed to set the ball speed back to normal.
     * @param myPaddle The paddle in the game
     * @param myBall the blue ball in the game.
     * @param secondBall The pinkish ball in the game if the DoubleBall powerup is activated.
     */

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall, Ball secondBall) {
        super.paddleCollision(myPaddle, myBall, secondBall);
        if(this.getPowerType().equals("powerup_speedball.gif")) {
            fastBall(myBall);
            PauseTransition delay = new PauseTransition(Duration.seconds(10));
            delay.setOnFinished(event -> resetBallSpeed(myBall));
            delay.play();
        }
    }
}