package game;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Powerup Class</h1>
 * <p> The Powerup class is an abstract class that is used to define the properties and characteristics of the powerups in the game.
 * The class extends the Sprite class to obtain its functionality. This class is abstract because only specific types of powerups can
 * be instantiated (FasterBall, DoubleBall, and BiggerPaddle</p>
 */

public abstract class Powerup extends Sprite {
    /**
     * How fast the powerup is moving. All powerups start with a y-velocity of 0 until the brick they are in has been destroyed
     */
    private double y_vel = 0;
    /**
     * Each powerup has a myBrick instance variable, which represents which brick the powerup is in.
     */
    private Brick myBrick;
    /**
     * Each powerup has a powerType. This can either be 'powerup_paddle.gif' which is BiggerPaddle powerup, 'powerup_speedball.gif' which is the FasterBall
     * powerup, and 'powerup_twoball.gif', which is the DoubleBall powerup.
     */
    private String powerType;

    /**
     * The Powerup constructor takes in the .gif file the powerup should look like. Calling super, the sprite class sets the ImageView to that
     * file. The constructor sets the height, weight, and power type of the powerup as well.
     * @param filename The .gif file of what the powerup should look like.
     */
    public Powerup(String filename) {
        super(filename);
        getMyImageView().setFitWidth(20);
        getMyImageView().setFitHeight(20);
        this.setPowerType(filename);
    }

    /**
     *Setter method to set the Y speed of the powerup
     * @param setYVel
     */

    public void setY_vel(double setYVel) {
        this.y_vel = setYVel;
    }

    /**
     * Getter method to return the Y speed of a powerup
     * @return y_vel property of the powerup
     */

    public double getY_vel() {
        return this.y_vel;
    }

    /**
     * Setter method that sets which brick the powerup will be behind.
     * @param newBrick
     */

    public void setBrick(Brick newBrick) {
        this.myBrick = newBrick;
    }

    /**
     * Returns which brick the powerup is in.
     * @return Brick that the powerup is in
     */

    public Brick getBrick() {
        return this.myBrick;
    }

    /**
     * Gets what the type of the powerup is.
     * @return powerup type
     */

    public String getPowerType() {
        return this.powerType;
    }

    /**
     * Sets the powerup type of the powerup.
     * @param typePower A .gif string which not only represent the image of the powerup but what type it is.
     */

    public void setPowerType(String typePower) {
        this.powerType = typePower;
    }

    /**
     * Checks to see if a powerup's brick was hit. If it was, change the y-velocity to 50 so the powerup starts moving and
     * set the ImageView visibility to true.
     * @param myBricks
     * @param myBall
     * @param secondBall
     */

    public void checkBrickHit(List<Brick> myBricks, Ball myBall, Ball secondBall) {
        if(myBricks.contains(this.getBrick()) == false) {
            if(myBall.getSpeed() != 0 || secondBall.getSpeed() != 0) {
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


    public abstract void paddleCollision(Paddle myPaddle, Ball ball, Ball secondBall);
}
