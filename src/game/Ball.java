package game;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Ball extends Sprite {
    private double x_dir;
    private double y_dir;
    private int ball_speed;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    public Ball(String filename){
        super(filename);
        myImageView.setFitWidth(15);
        myImageView.setFitWidth(15);
        x_dir = 100 - 200 * Math.random();
        y_dir = 100;
        ball_speed = 1;
    }

    public void changeSpeed(int speed) {
        this.ball_speed = speed;
    }

    public int getSpeed() {
        return this.ball_speed;
    }

    public void updateX_bounds() {
        if(this.myImageView.getBoundsInParent().getMaxX() >= WIDTH || this.myImageView.getX() <= 0) {
            this.x_dir *= -1;
        }
    }

    public void updateY_bounds(Paddle myPaddle) {
        if(this.myImageView.getY() <= 0) {
            this.y_dir *= -1;
        }
        if(this.myImageView.getY() >= HEIGHT) {
            reset(myPaddle);
        }
    }

    public void reset(Paddle myPaddle) {
        this.myImageView.setX(WIDTH / 2 - this.myImageView.getBoundsInLocal().getWidth() / 2);
        this.myImageView.setY(HEIGHT - 35 - this.myImageView.getBoundsInLocal().getHeight() / 2);
        ball_speed = 0;
        myPaddle.setInitialPosition(WIDTH / 2 - myPaddle.getWidth() / 2, HEIGHT - 25 - myPaddle.getHeight() / 2);
    }

    public void incrementPos(double elapsedTime, Paddle myPaddle) {
        this.myImageView.setX(this.myImageView.getX() + x_dir * this.ball_speed * elapsedTime);
        this.myImageView.setY(this.myImageView.getY() - y_dir * this.ball_speed * elapsedTime);
        updateX_bounds();
        updateY_bounds(myPaddle);
    }

    public void handleCollision(Paddle myPaddle,  ArrayList<Brick> myBricks) {
        paddleCollision(myPaddle);
        brickCollision(myBricks);
    }

    //ALTER SPEED TO ACCOUNT FOR DIFFERENCE
    private void paddleCollision(Paddle myPaddle) {
        double ball_location = this.myImageView.getX()+ this.myImageView.getBoundsInLocal().getWidth() / 2;
        double paddle_increment = myPaddle.myImageView.getBoundsInLocal().getWidth() / 11;
        if (ball_location <= myPaddle.myImageView.getX() + 1 * paddle_increment) {
            x_dir = -100;
        }
        else if (ball_location >= myPaddle.myImageView.getX() + 11 * paddle_increment) {
            x_dir = 100;
        }
        else if (ball_location <= myPaddle.myImageView.getX() + 2 * paddle_increment) {
            x_dir = -80;
        }
        else if (ball_location >= myPaddle.myImageView.getX() + 10 * paddle_increment) {
            x_dir = 80;
        }
        else if (ball_location <= myPaddle.myImageView.getX() + 3 * paddle_increment) {
            x_dir = -60;
        }
        else if (ball_location >= myPaddle.myImageView.getX() + 9 * paddle_increment) {
            x_dir = 60;
        }
        else if (ball_location <= myPaddle.myImageView.getX() + 4 * paddle_increment) {
            x_dir = -40;
        }
        else if (ball_location >= myPaddle.myImageView.getX() + 8 * paddle_increment) {
            x_dir = 40;
        }
        else if (ball_location <= myPaddle.myImageView.getX() + 5 * paddle_increment) {
            x_dir = -20;
        }
        else if (ball_location >= myPaddle.myImageView.getX() + 7 * paddle_increment) {
            x_dir = 20;
        }
        else if (ball_location >= myPaddle.myImageView.getX() + 6 * paddle_increment) {
            x_dir = 0;
        }
        y_dir = 100;
    }

    private void brickCollision(ArrayList<Brick> myBricks) {

    }


}