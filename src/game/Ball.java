package game;

/**
 * <h1>Ball Class</h1>
 *<p>The purpose of this class is to manage all the properties of the ball. This includes the movement of the ball
 * and collision with other objects.</p>
 */

public class Ball extends Sprite {

    /**
     *All instances of a ball have an x-direction and a y-direction. This dictates which direction in 2D the ball is moving.
     * Balls also have a ball_speed, which can change for powerups. A ball also has the sceneWidth and sceneHeight to ensure
     * the ball does not go beyond those bounds.
     */
    private double x_dir;
    private double y_dir;
    private double ball_speed;
    public double sceneWidth;
    public double sceneHeight;

    /**
     * The ball constructor takes in a file name, the scene width, and scene height
     * @param filename the image to be used for the ball
     * @param w the width of the bounds
     * @param h the height of the bounds
     */

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

    /**
     * setter method to change the speed of the ball
     * @param speed new speed of the ball
     */

    public void changeSpeed(double speed) {
        this.ball_speed = speed;
    }

    /**
     * method to change the speed AND velocity of the ball
     * @param speed new speed of the ball
     * @param vX new X velocity of the ball
     * @param vY new Y velocity of the ball
     */

    public void changeSpeedAndVelocity(double speed, int vX, int vY) {
        this.ball_speed = speed;
        this.x_dir = vX;
        this.y_dir = vY;
    }

    /**
     * the getSpeed method of the ball
     * @return the current speed of the ball
     */

    public double getSpeed() {
        return this.ball_speed;
    }

    /**
     * get current x direction of the ball
     * @return the x direction
     */

    public double getXDirection(){
        return x_dir;
    }

    /**
     * get current y-direction of the ball
     * @return the current y-direction of the ball
     */

    public double getYDirection(){
        return y_dir;
    }

    /**
     * changes the x-direction of the ball if it hits the left wall or right wall bounds of the games
     */

    public void updateX_bounds() {
        if(this.getMyImageView().getBoundsInParent().getMaxX() >= sceneWidth || this.getMyImageView().getBoundsInParent().getMinX() <= 0) {
            this.x_dir *= -1;
        }
    }

    /**
     * Returns a boolean determining whether or not the the player misses the ball with the paddle.
     * Returns true if the player misses the paddle and and false if the player does not miss the ball.
     * @param myPaddle the paddle in the game
     * @return a boolean
     */

    public boolean checkMissedBall(Paddle myPaddle) {
        if(getMyImageView().getBoundsInParent().getMinY() >= sceneHeight) {
            reset(myPaddle);
            return true;
        }
        return false;
    }

    /**
     * if the ball hits the top of the screen, change the y-direction
     */

    public void updateY_bounds() {
        if(this.getY() <= 0) {
            this.y_dir *= -1;
        }
    }

    /**
     * this method resets the paddle and ball locations if the players misses the ball. Also sets the ball x and y directions randomly
     * @param myPaddle the paddle in the game
     */

    public void reset(Paddle myPaddle) {
        setPosition(sceneWidth / 2 - this.getWidth() / 2, sceneHeight - 40 - this.getHeight() / 2);
        this.x_dir = 100 - 200 * Math.random();
        this.y_dir = -100;
        this.ball_speed = 0;
        myPaddle.setPosition(sceneWidth / 2 - myPaddle.getWidth() / 2, sceneHeight - 25 - myPaddle.getHeight() / 2);
    }

    /**
     * Increments the position of the ball acording to the ball speed.
     * @param elapsedTime The time increments
     * @param myPaddle Not used; should not be a parameter for this method
     */

    public void incrementPos(double elapsedTime, Paddle myPaddle) {
        setPosition(this.getX() + x_dir * this.ball_speed * elapsedTime,
                this.getY() + y_dir * this.ball_speed * elapsedTime);
        updateX_bounds();
        updateY_bounds();
    }

    /**
     * This method is called in the BreakerGame class if the ball collides with the paddle. This method, depending on where the ball
     * hits the paddle, dictates which direction the ball goes.
     * @param myPaddle the paddle in the game
     */

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

    /**
     * This method is called in the BreakerGame class to handle the collisions between a ball and a brick.
     * @param brick the brick to check the collision with
     */

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