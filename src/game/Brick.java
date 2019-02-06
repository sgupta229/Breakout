package game;

public class Brick extends Sprite{
    private int typeNumber;

    public Brick(String filename, double sceneWidth, double sceneHeight) {
        super(filename);
        typeNumber = Integer.parseInt(filename.substring(5, 6));
        //In reality we should use the dimensions of the scene to determine the width
        getMyImageView().setFitWidth(sceneWidth / 8);
        getMyImageView().setFitHeight(getMyImageView().getBoundsInLocal().getHeight()*2);
    }

    public int getBrickType(){
        return typeNumber;
    }

    public void handleCollision() {
        setX(getX() + 10000);
    }
}