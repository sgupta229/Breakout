package game;

import java.util.ArrayList;
import java.util.Timer;

public class Powerups extends Sprite {
    private double y_vel;
    private Timer timer;
    private Brick brick;

    public Powerups(String filename) {
        super(filename);
        myImageView.setFitWidth(20);
        myImageView.setFitWidth(20);
    }

    private void setY_vel(double setYVel) {
        this.y_vel = setYVel;
    }

    public void bigPaddle(Paddle myPaddle) {
        myPaddle.myImageView.setFitWidth(200);
    }

    public void slowBall(Ball myBall) {
        myBall.changeSpeed(2);
    }

    public void setPowerUps(ArrayList<Brick> myBricks) {

    }



}
