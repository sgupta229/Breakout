package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BiggerBall extends Powerup {

    public BiggerBall(String filename) {
        super(filename);
        this.setPowerType("sizepower.gif");
    }

    public void resetBallSize(Ball myBall) {
        myBall.getMyImageView().setFitWidth(15);
        myBall.getMyImageView().setFitHeight(15);
    }

    public void bigBall(Ball myBall) {
        myBall.getMyImageView().setFitWidth(20);
        myBall.getMyImageView().setFitHeight(20);
    }

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall) {
        if(this.getPowerType().equals("sizepower.gif")) {
            bigBall(myBall);
            this.setX(1000);
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> resetBallSize(myBall));
            delay.play();
        }
    }
}