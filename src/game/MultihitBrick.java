package game;

import javafx.scene.image.Image;

public class MultihitBrick extends Brick {

    public MultihitBrick(String filename, double sceneWidth){
        super(filename, sceneWidth);
    }

    @Override
    public void handleCollision() {
        if (typeNumber == 6)
            super.handleCollision();
        else downgrade();
    }

    private void downgrade() {
        typeNumber--;
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("brick" + getBrickType() + ".gif"));
        getMyImageView().setImage(image);
    }
}
