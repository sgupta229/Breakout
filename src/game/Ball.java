package game;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Ball extends Sprite {
    public static final String BALL_IMAGE = "ball.gif";
    private double x_dir;
    private double y_dir;
    private int ball_speed;
    private ImageView ballImage;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;


    public Ball(String filename){
        super(filename);
        myImageView.setFitWidth(15);
        myImageView.setFitWidth(15);
        x_dir = 1;
        y_dir = 1;
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

    private void updateX_bounds() {
        if(this.ballImage.getBoundsInParent().getMaxX() >= WIDTH || this.ballImage.getX() <= 0) {
            changeX_dir();
        }
    }

    private void updateY_bounds() {
        if(this.ballImage.getBoundsInParent().getMaxY() >= HEIGHT || this.ballImage.getY() <= 0) {
            changeY_dir();
        }
    }

    public void incrementPos(double elapsedTime) {
        this.ballImage.setX(this.ballImage.getX() + x_dir * this.ball_speed * elapsedTime);
        this.ballImage.setY(this.ballImage.getY() - y_dir * this.ball_speed * elapsedTime);
    }

    public void handleCollision(double elapsedTime, Paddle myPaddle, Timeline animation, Stage s, ArrayList<Brick> myBricks) {
        updateX_bounds();
        updateY_bounds();
        paddleCollision(myPaddle);
        brickCollision(myBricks);
        incrementPos(elapsedTime);
    }

    private void paddleCollision(Paddle myPaddle) {
        double ball_location = this.ballImage.getX() + this.ballImage.getBoundsInLocal().getWidth() / 2;
        double paddle_location = myPaddle.myImageView.getBoundsInLocal().getWidth() / 8;

        if (this.ballImage.getBoundsInParent().intersects(myPaddle.myImageView.getBoundsInParent())) {

        }

    }

    private void brickCollision(ArrayList<Brick> myBricks) {

    }


}