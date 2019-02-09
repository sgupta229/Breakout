package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class FasterBall extends Powerup {

    public FasterBall(String filename) {
        super(filename);
    }

    public void resetBallSpeed(Ball myBall) {
        myBall.changeSpeed(3.0);
    }

    public void fastBall(Ball myBall) {
        myBall.changeSpeed(4.5);
    }

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall, Ball secondBall) {
        if(this.getPowerType().equals("sizepower.gif")) {
            fastBall(myBall);
            this.setX(1000);
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> resetBallSpeed(myBall));
            delay.play();
        }
    }
}