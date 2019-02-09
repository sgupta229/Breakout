package game;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.ArrayList;


public abstract class Powerup extends Sprite {
    private double y_vel = 0;
    private Brick myBrick;
    //current power types
        //pointspower.gif == big paddle
        //sizepower.gif == big ball
    private String powerType;

    public Powerup(String filename) {
        super(filename);
        getMyImageView().setFitWidth(20);
        getMyImageView().setFitHeight(20);
    }

    public void setY_vel(double setYVel) {
        this.y_vel = setYVel;
    }

    public double getY_vel() {
        return this.y_vel;
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

    public void checkBrickHit(double elapsedTime, ArrayList<Brick> myBricks, Ball myBall) {
        if(myBricks.contains(this.getBrick()) == false) {
            if(myBall.getSpeed() != 0) {
                this.setY_vel(50);
            }
            else {
                this.setY_vel(0);
            }
            this.getMyImageView().setVisible(true);
        }
    }

    public void incrementPos(double elapsedTime) {
        this.setY(this.getY() + this.getY_vel() * elapsedTime);
    }

//    public void paddleCollision(Paddle myPaddle, Ball myBall) {
////        if(this.getPowerType().equals("pointspower.gif")) {
////            bigPaddle(myPaddle);
////            this.setX(1000);
////            PauseTransition delay = new PauseTransition(Duration.seconds(5));
////            delay.setOnFinished(event -> resetPaddleSize(myPaddle));
////            delay.play();
////        }
////        if(this.getPowerType().equals("sizepower.gif")) {
////            sizeBall(myBall);
////            this.setX(1000);
////            PauseTransition delay = new PauseTransition(Duration.seconds(3));
////            delay.setOnFinished( event -> resetBallSize(myBall));
////            delay.play();
////        }
////    }

    public abstract void paddleCollision(Paddle myPaddle, Ball myBall);
}
