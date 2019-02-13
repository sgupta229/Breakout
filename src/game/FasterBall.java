package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class FasterBall extends Powerup {

    public FasterBall(String filename) {
        super(filename);
    }

    public void resetBallSpeed(Ball myBall) {
        myBall.changeSpeed(2.75);
    }

    public void fastBall(Ball myBall) {
        myBall.changeSpeed(4.0);
    }

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