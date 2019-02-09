package game;

public class Brick extends Sprite{
    private int typeNumber;

    public Brick(String filename, double sceneWidth) {
        super(filename);
        typeNumber = Integer.parseInt(filename.substring(5, 6));
        getMyImageView().setFitWidth(sceneWidth / 10);
        getMyImageView().setFitHeight(getMyImageView().getBoundsInLocal().getHeight()*1.5);
    }

    public int getBrickType(){
        return typeNumber;
    }

    public void handleCollision() {
        setX(getX() + 10000);
    }
}