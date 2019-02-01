package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite {

    public Sprite() {

    }

    public Sprite(String filename){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(filename));
        myImageView = new ImageView(image);
    }

    public void setInitialPosition(double x, double y){
        setX(x);
        setY(y);
    };

    public void setX(double x){
        myImageView.setX(x);
    }

    public void setY(double y){
        myImageView.setY(y);
    }

    protected ImageView myImageView;

    public ImageView getMyImageView() {
        return myImageView;
    }

    public double getX(){
        return myImageView.getX();
    }

    public double getY(){
        return myImageView.getY();
    }

    public double getWidth(){
        return myImageView.getBoundsInLocal().getWidth();
    }

    public double getHeight(){
        return myImageView.getBoundsInLocal().getHeight();
    }
}
