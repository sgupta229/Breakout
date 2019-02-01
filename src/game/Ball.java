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

    //Switches X direction
    public void changeX_dir() {
        this.x_dir *= -1;
    }

    //Switches Y direction
    public void changeY_dir() {
        this.y_dir *= -1;
    }

    private void updateX_bounds() {
        if(this.myImageView.getBoundsInParent().getMaxX() >= WIDTH || this.myImageView.getX() <= 0) {
            changeX_dir();
        }
    }

    private void updateY_bounds() {
        if(this.myImageView.getBoundsInParent().getMaxY() >= HEIGHT || this.myImageView.getY() <= 0) {
            changeY_dir();
        }
    }

    public void incrementPos(double elapsedTime) {
        this.myImageView.setX(this.myImageView.getX() + x_dir * this.ball_speed * elapsedTime);
        this.myImageView.setY(this.myImageView.getY() - y_dir * this.ball_speed * elapsedTime);
    }

    public void handleCollision(Paddle myPaddle,  ArrayList<Brick> myBricks) {
        updateX_bounds();
        updateY_bounds();
        paddleCollision(myPaddle);
        brickCollision(myBricks);
    }

    private void paddleCollision(Paddle myPaddle) {
        double ball_location = this.myImageView.getX() + this.myImageView.getBoundsInLocal().getWidth() / 2;
        double paddle_location = myPaddle.myImageView.getBoundsInLocal().getWidth() / 8;

        if (this.myImageView.getBoundsInParent().intersects(myPaddle.myImageView.getBoundsInParent())) {
            if (ball_location <= myPaddle.getX() + paddle_location) {
                x_dir = -1.10;
                y_dir = 1;
            }

        }
    }

    private void brickCollision(ArrayList<Brick> myBricks) {

    }


}