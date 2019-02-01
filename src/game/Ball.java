package game;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Ball extends BreakerGame {
    public static final String BALL_IMAGE = "ball.gif";
    private double x_dir;
    private double y_dir;
    private int ball_speed;
    private Ball myBall;
    private ImageView ballImage;

    public Ball(int speed, ImageView image) {
        x_dir = 1;
        y_dir = 1;
        ball_speed = speed;
        ballImage = image;
    }



    public void changeSpeed(int speed) {
        this.ball_speed = speed;
    }

    public double getX_dir() {
        return this.x_dir;
    }

    public double getY_dir() {
        return this.y_dir;
    }

    public ImageView getBallImage() {
        return ballImage;
    }

    //Switches X direction
    public void changeX_dir() {
        this.x_dir *= -1;
    }

    //Switches Y direction
    public void changeY_dir() {
        this.y_dir *= -1;
    }

    private void updateX_dir() {
        if(this.ballImage.getBoundsInParent().getMaxX() >= WIDTH || this.ballImage.getX() <= 0) {
            changeX_dir();
        }
    }



    public Ball updateFinalPos(double elapsedTime, ImageView paddle, Timeline animation, Stage s, ArrayList<Brick> myBricks,
                          int speed) {
        updateX_dir();
        return this;
    }

}