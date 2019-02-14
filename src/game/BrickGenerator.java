package game;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class BrickGenerator extends BreakerGame{
    private List<Brick> myBricks;
    private int numBricks;

    /**generates a list of all bricks according to a given config file, adds them to the game, and calculates the number of bricks
     * necessary destroy to win (so All Bricks - Indestructible Bricks)
     *
     * @param root - the root node
     * @param width - width of the scene
     * @param lvlConfigFile - brick layout file for a particular level
     */
    public void generateBricks(Group root, double width, String lvlConfigFile) {
        var brickList = new ArrayList<Brick>();
        var configList = readConfigFile(lvlConfigFile);

        numBricks = 0;
        var b = new Brick("brick1.gif", width);
        var brickWidth = b.getWidth();
        var brickHeight = b.getHeight();
        double currentX = 0;
        double currentY = brickHeight;
        for (int num: configList){
            if (num != 0){
                if (num == 3){
                    b = new IndestructibleBrick("brick" + num + ".gif", width);
                }
                else if (num > 5 && num < 9) {
                    b = new MultihitBrick("brick" + num + ".gif", width);
                }
                else {
                    b = new Brick("brick" + num + ".gif", width);
                }
                b.setPosition(currentX, currentY);
                root.getChildren().addAll(b.getMyImageView());
                brickList.add(b);
                if (!(b instanceof IndestructibleBrick)) {
                    numBricks++;
                }
            }

            if (currentX + brickWidth >= width){
                currentX = 0;
                currentY += brickHeight;
            }
            else currentX += brickWidth;
        }
        myBricks = brickList;
    }

    /**
     * returns list of bricks generated in generateBricks()
     */
    public List<Brick> getMyBricks() {
        return myBricks;
    }

    /**
     * @return number of bricks that need to be destroyed in order to win the level
     */
    public int getNumBricks() {
        return numBricks;
    }
}
