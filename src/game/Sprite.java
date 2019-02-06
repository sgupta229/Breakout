package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite {

    private ImageView myImageView;

    public Sprite(String filename){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(filename));
        myImageView = new ImageView(image);
    }

    public void setPosition(double x, double y){
        setX(x);
        setY(y);
    };

    public void setX(double x){
        this.myImageView.setX(x);
    }

    public void setY(double y){
        this.myImageView.setY(y);
    }

    public ImageView getMyImageView() {
        return this.myImageView;
    }

    public double getX(){
        return this.myImageView.getX();
    }

    public double getY(){
        return this.myImageView.getY();
    }

    public double getWidth(){
        return this.myImageView.getBoundsInLocal().getWidth();
    }

    public double getHeight(){
        return this.myImageView.getBoundsInLocal().getHeight();
    }
}
