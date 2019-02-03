package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Powerup extends Sprite {
    private double y_vel = 0;
    private Timer timer;
    private Brick myBrick;
    private ArrayList<Powerup> powerList;
    public static final int BRICK_WIDTH = 94;
    //current power types
        //pointspower.gif == big paddle
        //sizepower.gif == big ball
    private String powerType;

    public Powerup(String filename) {
        super(filename);
        myImageView.setFitWidth(25);
        myImageView.setFitWidth(20);
    }

    public void setY_vel(double setYVel) {
        this.y_vel = setYVel;
    }

    public double getY_vel() {
        return this.y_vel;
    }

    public void bigPaddle(Paddle myPaddle) {
        myPaddle.getMyImageView().setFitWidth(200);
    }

    public void setBrick(Brick newBrick) {
        this.myBrick = newBrick;
    }

    public Brick getBrick() {
        return this.myBrick;
    }

    public String getPowerType() {
        return this.powerType;
    }

    public void setPowerType(String typePower) {
        this.powerType = typePower;
    }

    public ArrayList<Powerup> getPowerList() {
        return this.powerList;
    }

    public void sizeBall(Ball myBall) {
        myBall.getMyImageView().setFitWidth(25);
        myBall.getMyImageView().setFitHeight(25);
    }

    public void checkBrickHit(double elapsedTime, ArrayList<Brick> myBricks, Ball myBall) {
        if(myBricks.contains(this.getBrick()) == false) {
            if(myBall.getSpeed() != 0) {
                this.setY_vel(50);
            }
            else {
                this.setY_vel(0);
            }
            this.myImageView.setVisible(true);
        }
    }

    public void incrementPos(double elapsedTime) {
        this.setY(this.getY() + this.getY_vel() * elapsedTime);
    }

    public void paddleCollision(Paddle myPaddle, Ball myBall) {
        if(this.getPowerType().equals("pointspower.gif")) {
            bigPaddle(myPaddle);
            this.setX(1000);
        }
        if(this.getPowerType().equals("sizepower.gif")) {
            sizeBall(myBall);
            this.setX(1000);
        }
    }
}
