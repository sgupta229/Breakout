package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BiggerPaddle extends Powerup {

    public BiggerPaddle(String filename) {
        super(filename);
    }

    public void bigPaddle(Paddle myPaddle) {
        myPaddle.getMyImageView().setFitWidth(200);
    }

    public void resetPaddleSize(Paddle myPaddle) {
        myPaddle.getMyImageView().setFitWidth(175);
    }

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
