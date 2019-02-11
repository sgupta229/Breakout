package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class DoubleBall extends Powerup {

    public DoubleBall (String filename) {
         super(filename);
    }

    public void retrieveBall(Ball myBall, Ball secondBall) {
        secondBall.setPosition(myBall.getX(), myBall.getY());
        secondBall.getMyImageView().setVisible(true);
        secondBall.changeSpeed(3.0);
    }

    public void removeBall(Ball secondBall) {
        secondBall.setPosition(1000, 1000);
        secondBall.changeSpeed(0);
        secondBall.getMyImageView().setVisible(false);
    }

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall, Ball secondBall) {
        if(this.getPowerType().equals("powerup_doubleball.gif")) {
            retrieveBall(myBall, secondBall);
            this.setX(1000);
            PauseTransition delay = new PauseTransition(Duration.seconds(10));
            delay.setOnFinished(event -> removeBall(secondBall));
            delay.play();
        }
    }
}
