package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Powerup extends Sprite {
    private double y_vel;
    private Timer timer;
    private Brick brick;
    private ArrayList<Powerup> powerList;

    public Powerup(String filename) {
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

    public void setBrick(Brick newBrick) {
        this.brick = brick;
    }

    public Brick getBrick() {
        return this.brick;
    }

    public void sizeBall(Ball myBall) {
        myBall.getMyImageView().setFitWidth(25);
        myBall.getMyImageView().setFitHeight(25);
    }

    public void setPowerUps(ArrayList<Brick> myBricks) {
        Collections.shuffle(myBricks);
        powerList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Powerup currPow = new Powerup("pointspower.gif");
            currPow.setBrick(myBricks.get(0));
            currPow.getMyImageView().setX(currPow.getBrick().getX());
            currPow.getMyImageView().setY(currPow.getBrick().getY());
            currPow.getMyImageView().setVisible(false);


        }
    }



}
