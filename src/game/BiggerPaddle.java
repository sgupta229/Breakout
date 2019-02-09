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
        if(this.getPowerType().equals("pointspower.gif")) {
            bigPaddle(myPaddle);
            this.setX(1000);
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> resetPaddleSize(myPaddle));
            delay.play();
        }
    }
}
