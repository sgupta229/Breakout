package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * This powerup makes the paddle longer when caught. This class handles the methods and functionality of that powerup.
 */

public class BiggerPaddle extends Powerup {

    /**
     * The constructor simply calls super for the constructor in the Powerup class.
     * @param filename the file that holds the image of the powerups
     */

    public BiggerPaddle(String filename) {
        super(filename);
    }

    /**
     * Method that makes the paddle bigger
     * @param myPaddle the paddle in the game
     */

    public void bigPaddle(Paddle myPaddle) {
        myPaddle.getMyImageView().setFitWidth(200);
    }

    /**
     * Resets the paddle size back to normal after the powerup duration is over.
     * @param myPaddle the paddle in the game.
     */

    public void resetPaddleSize(Paddle myPaddle) {
        myPaddle.getMyImageView().setFitWidth(175);
    }

    /**
     * Overrides the paddleCollision method in the Powerup class. Calls super first, which simply makes the powerup disappear if caught,
     * and then makes the paddle longer for 10 seconds.
     * @param myPaddle The paddle in the game
     * @param myBall the blue ball in the game.
     * @param secondBall The pinkish ball in the game if the DoubleBall powerup is activated.
     */

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall, Ball secondBall) {
        super.paddleCollision(myPaddle, myBall, secondBall);
        if(this.getPowerType().equals("powerup_paddle.gif")) {
            bigPaddle(myPaddle);
            PauseTransition delay = new PauseTransition(Duration.seconds(10));
            delay.setOnFinished(event -> resetPaddleSize(myPaddle));
            delay.play();
        }
    }
}
