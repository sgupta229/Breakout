package game;

public class Brick extends Sprite{
    protected int typeNumber;
    private double sceneWidth;

    public Brick(String filename, double width) {
        super(filename);
        sceneWidth = width;
        typeNumber = Integer.parseInt(filename.substring(5, 6));
        getMyImageView().setFitWidth(width / 10);
        getMyImageView().setFitHeight(getMyImageView().getBoundsInLocal().getHeight()*1.5);
    }

    public double getSceneWidth() {
        return sceneWidth;
    }

    public int getBrickType(){
        return typeNumber;
    }

    protected void setBrickType(int t){
        typeNumber = t;
    }

    public void handleCollision() {
        setX(getX() + 10000);
    }
}