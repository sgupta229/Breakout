package game;

/**
 * <h1>Ball Class</h1>
 *<p>The purpose of this class is to manage all the properties of the ball. This includes the movement of the ball
 * and collision with other objects.</p>
 */

public class Ball extends Sprite {

    /**
     *
     */
    private double x_dir;
    private double y_dir;
    private double ball_speed;
    public double sceneWidth;
    public double sceneHeight;

    public Ball(String filename, double w, double h){
        super(filename);
        getMyImageView().setFitWidth(15);
        getMyImageView().setFitWidth(15);
        sceneWidth = w;
        sceneHeight = h;
        x_dir = 100 - 200 * Math.random();
        y_dir = -100;
        ball_speed = 0;
    }

    public void changeSpeed(double speed) {
        this.ball_speed = speed;
    }

    public void changeSpeedAndVelocity(double speed, int vX, int vY) {
        this.ball_speed = speed;
        this.x_dir = vX;
        this.y_dir = vY;
    }

    public double getSpeed() {
        return this.ball_speed;
    }

    public double getXDirection(){
        return x_dir;
    }

    public double getYDirection(){
        return y_dir;
    }

    public void updateX_bounds() {
        if(this.getMyImageView().getBoundsInParent().getMaxX() >= sceneWidth || this.getMyImageView().getBoundsInParent().getMinX() <= 0) {
            this.x_dir *= -1;
        }
    }

    public boolean checkMissedBall(Paddle myPaddle) {
        if(getMyImageView().getBoundsInParent().getMinY() >= sceneHeight) {
            reset(myPaddle);
            return true;
        }
        return false;
    }


    public void updateY_bounds() {
        if(this.getY() <= 0) {
            this.y_dir *= -1;
        }
    }

    public void reset(Paddle myPaddle) {
        setPosition(sceneWidth / 2 - this.getWidth() / 2, sceneHeight - 40 - this.getHeight() / 2);
        this.x_dir = 100 - 200 * Math.random();
        this.y_dir = -100;
        this.ball_speed = 0;
        myPaddle.setPosition(sceneWidth / 2 - myPaddle.getWidth() / 2, sceneHeight - 25 - myPaddle.getHeight() / 2);
    }

    public void incrementPos(double elapsedTime, Paddle myPaddle) {
        setPosition(this.getX() + x_dir * this.ball_speed * elapsedTime,
                this.getY() + y_dir * this.ball_speed * elapsedTime);
        updateX_bounds();
        updateY_bounds();
    }

    //ALTER SPEED TO ACCOUNT FOR DIFFERENCE
    public void paddleCollision(Paddle myPaddle) {
        double ball_location = this.getX() + this.getWidth() / 2;
        double paddle_increment = myPaddle.getWidth() / 10;
        if (ball_location <= myPaddle.getX() + 1 * paddle_increment) {
            x_dir = -100;
        }
        else if (ball_location >= myPaddle.getX() + 10 * paddle_increment) {

            x_dir = 100;
        }
        else if (ball_location <= myPaddle.getX() + 2 * paddle_increment) {
            x_dir = -80;
        }
        else if (ball_location >= myPaddle.getX() + 9 * paddle_increment) {
            x_dir = 80;
        }
        else if (ball_location <= myPaddle.getX() + 3 * paddle_increment) {
            x_dir = -60;
        }
        else if (ball_location >= myPaddle.getX() + 8 * paddle_increment) {
            x_dir = 60;
        }
        else if (ball_location <= myPaddle.getX() + 4 * paddle_increment) {
            x_dir = -40;
        }
        else if (ball_location >= myPaddle.getX() + 7 * paddle_increment) {
            x_dir = 40;
        }
        else if (ball_location <= myPaddle.getX() + 5 * paddle_increment) {
            x_dir = -20;
        }
        else if (ball_location >= myPaddle.getX() + 6 * paddle_increment) {
            x_dir = 20;
        }
        else if (ball_location == myPaddle.getX() + 6 * paddle_increment) {
            x_dir = 0;
        }
        y_dir = -100;
    }

    public void brickCollision(Brick brick) {
        if((this.getX()+ this.getWidth() / 2) <= brick.getMyImageView().getBoundsInParent().getMinX()) {
            this.x_dir *= -1;
        }
        else if ((this.getX()+ this.getWidth() / 2) >= brick.getMyImageView().getBoundsInParent().getMaxX()) {
            this.x_dir *= -1;
        }
        if((this.getY()+ this.getHeight() / 2) >= brick.getMyImageView().getBoundsInParent().getMaxY()) {
            this.y_dir *= -1;
        }
        else if((this.getY()+ this.getHeight() / 2) <= brick.getMyImageView().getBoundsInParent().getMinY()) {
            this.y_dir *= -1;
        }
    }

}