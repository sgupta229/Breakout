package game;

public class Ball extends Sprite {
    private double x_dir;
    private double y_dir;
    private double ball_speed;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    public Ball(String filename){
        super(filename);
        myImageView.setFitWidth(15);
        myImageView.setFitWidth(15);
        x_dir = 100 - 200 * Math.random();
        y_dir = 100;
        ball_speed = 1.5;
    }

    public void changeSpeed(double speed) {
        this.ball_speed = speed;
    }

    public double getSpeed() {
        return this.ball_speed;
    }

    public void updateX_bounds() {
        if(this.myImageView.getBoundsInParent().getMaxX() >= WIDTH || this.myImageView.getX() <= 0) {
            this.x_dir *= -1;
        }
    }

    public boolean updateY_bounds(Paddle myPaddle) {
        if(getY() <= 0) {
            this.y_dir *= -1;
        }
        if(getY() >= HEIGHT) {
            reset(myPaddle);
            return true;
        }
        return false;
    }

    public void reset(Paddle myPaddle) {
        setPosition(WIDTH / 2 - this.getWidth() / 2, HEIGHT - 35 - this.getHeight() / 2);
        ball_speed = 0;
        myPaddle.setPosition(WIDTH / 2 - myPaddle.getWidth() / 2, HEIGHT - 25 - myPaddle.getHeight() / 2);
    }

    public boolean incrementPos(double elapsedTime, Paddle myPaddle) {
        setPosition(this.getX() + x_dir * this.ball_speed * elapsedTime,
                this.getY() - y_dir * this.ball_speed * elapsedTime);
        updateX_bounds();
        return updateY_bounds(myPaddle);
    }

    //ALTER SPEED TO ACCOUNT FOR DIFFERENCE
    public void paddleCollision(Paddle myPaddle) {
        double ball_location = this.getX() + this.getWidth() / 2;
        double paddle_increment = myPaddle.getWidth() / 11;
        if (ball_location <= myPaddle.getX() + 1 * paddle_increment) {
            x_dir = -100;
        }
        else if (ball_location >= myPaddle.getX() + 11 * paddle_increment) {

            x_dir = 100;
        }
        else if (ball_location <= myPaddle.getX() + 2 * paddle_increment) {
            x_dir = -80;
        }
        else if (ball_location >= myPaddle.getX() + 10 * paddle_increment) {
            x_dir = 80;
        }
        else if (ball_location <= myPaddle.getX() + 3 * paddle_increment) {
            x_dir = -60;
        }
        else if (ball_location >= myPaddle.getX() + 9 * paddle_increment) {
            x_dir = 60;
        }
        else if (ball_location <= myPaddle.getX() + 4 * paddle_increment) {
            x_dir = -40;
        }
        else if (ball_location >= myPaddle.getX() + 8 * paddle_increment) {
            x_dir = 40;
        }
        else if (ball_location <= myPaddle.getX() + 5 * paddle_increment) {
            x_dir = -20;
        }
        else if (ball_location >= myPaddle.getX() + 7 * paddle_increment) {
            x_dir = 20;
        }
        else if (ball_location == myPaddle.getX() + 6 * paddle_increment) {
            x_dir = 0;
        }
        y_dir = 100;
    }

    public void brickCollision(Brick brick) {
        if((this.getX()+ this.getWidth() / 2) >= brick.getMyImageView().getBoundsInParent().getMaxX()) {
            this.x_dir *= -1;
        }
        else if((this.getX()+ this.getWidth() / 2) <= brick.getMyImageView().getBoundsInParent().getMinX()) {
            this.x_dir *= -1;
        }
        else if((this.getY()+ this.getHeight() / 2) >= brick.getMyImageView().getBoundsInParent().getMinY()) {
            this.y_dir *= -1;
        }
        else if((this.getY()+ this.getHeight() / 2) <= brick.getMyImageView().getBoundsInParent().getMaxY()) {
            this.y_dir *= -1;
        }
    }


}