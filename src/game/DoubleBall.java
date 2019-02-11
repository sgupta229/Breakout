package game;

public class DoubleBall extends Powerup {

    public DoubleBall (String filename) {
         super(filename);
    }

    public void retrieveBall(Ball myBall, Ball secondBall) {
        secondBall.setPosition(myBall.getX(), myBall.getY());
        secondBall.getMyImageView().setVisible(true);
        secondBall.changeSpeed(2.75);
    }

    @Override
    public void paddleCollision(Paddle myPaddle, Ball myBall, Ball secondBall) {
        if(this.getPowerType().equals("powerup_doubleball.gif")) {
            retrieveBall(myBall, secondBall);
            this.setX(1000);
        }
    }
}
