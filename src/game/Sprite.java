package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class for all Sprites including all Bricks, Balls, Powerups, and the Paddle
 */
public abstract class Sprite {

    private ImageView myImageView;

    /**
     * constructor for Sprites, creates imageView of the Sprite
     * @param filename
     */
    public Sprite(String filename){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(filename));
        myImageView = new ImageView(image);
    }

    /** sets X and Y coordinates
     *
     * @param x
     * @param y
     */
    public void setPosition(double x, double y){
        setX(x);
        setY(y);
    };

    /** checks if this Sprite has collided with another
     *
     * @param other - other Sprite
     * @return boolean
     */
    public boolean isCollided(Sprite other){
        return this.getMyImageView().getBoundsInParent().intersects(other.getMyImageView().getBoundsInParent());
    }

    /** sets X coordinate
     * @param x
     */
    public void setX(double x){
        this.myImageView.setX(x);
    }

    /** sets Y coordinate
     * @param y
     */
    public void setY(double y){
        this.myImageView.setY(y);
    }

    /** returns this Sprite's ImageView
     */
    public ImageView getMyImageView() {
        return this.myImageView;
    }

    /**
     * @return X coordinate
     */
    public double getX(){
        return this.myImageView.getX();
    }

    /**
     * @return Y coordinate
     */
    public double getY(){
        return this.myImageView.getY();
    }

    /**
     * @return width of Sprite
     */
    public double getWidth(){
        return this.myImageView.getBoundsInLocal().getWidth();
    }

    /**
     * @return height of Sprite
     */
    public double getHeight(){
        return this.myImageView.getBoundsInLocal().getHeight();
    }
}
