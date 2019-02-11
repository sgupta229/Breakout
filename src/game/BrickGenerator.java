package game;

import javafx.scene.Group;

import java.util.ArrayList;

public class BrickGenerator extends BreakerGame{
    private ArrayList<Brick> myBricks;
    private int numBricks;

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
    public ArrayList<Brick> getMyBricks() {
        return myBricks;
    }

    public int getNumBricks() {
        return numBricks;
    }
}
