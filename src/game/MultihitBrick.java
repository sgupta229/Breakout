package game;

import javafx.scene.image.Image;

/** Creates brick that requires multiple hits to destroy
 */
public class MultihitBrick extends Brick {


    /** Constructor
     *
     * @param filename - see Brick class
     * @param sceneWidth - see Brick class
     */
    public MultihitBrick(String filename, double sceneWidth){
        super(filename, sceneWidth);
    }

    /**
     * if brick is lowest type of multi-hit brick, destroy brick. Else downgrade brick to lower state that requires fewer hits
     */
    @Override
    public void handleCollision() {
        if (getBrickType() == 6)
            super.handleCollision();
        else downgrade();
    }

    private void downgrade() {
        setBrickType(getBrickType()-1);
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("brick" + getBrickType() + ".gif"));
        getMyImageView().setImage(image);
    }
}
