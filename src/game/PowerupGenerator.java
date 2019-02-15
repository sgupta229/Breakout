package game;

import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The purpose of this class is to generate and place the powerups for levels. This class can be modified to output different
 * powerups per level.
 */

public class PowerupGenerator {

    /**
     * This method sets the powerups. This method is called for each level. It takes the list of bricks for the level and the root,
     * finds the powerup bricks, and randomly places 2 BiggerPaddle powerups, 1 DoubleBall powerup, and 1 FasterBall powerup behind these bricks.
     * This outputs a list of powerups, which is used to check when a powerup should start moving in the step method.
     * @param myBricks The list of bricks for a particular level
     * @param root The root of the scene to add the powerups
     * @return A list of powerups
     */
    public List<Powerup> setPowerups(List<Brick> myBricks, Group root) {
        ArrayList<Powerup> typeOfPowers = new ArrayList<>();
        ArrayList<Brick> powerupBrickList = new ArrayList<>();
        for(Brick i : myBricks) {
            if(i.getBrickType() == 9) {
                powerupBrickList.add(i);
            }
        }
        for(int i = 0; i < 2; i++) {
            typeOfPowers.add(new BiggerPaddle("powerup_paddle.gif"));
        }
        typeOfPowers.add(new FasterBall("powerup_speedball.gif"));
        typeOfPowers.add(new DoubleBall("powerup_twoball.gif"));
        ArrayList<Powerup> addPowers = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Collections.shuffle(typeOfPowers);
            Powerup currPow = typeOfPowers.get(0);
            typeOfPowers.remove(0);
            currPow.setBrick(powerupBrickList.get(i));
            Brick currBrick = currPow.getBrick();
            currPow.setX(currBrick.getX() - currPow.getMyImageView().getBoundsInLocal().getWidth() / 2 + currBrick.getMyImageView().getBoundsInLocal().getWidth()/2);
            currPow.setY(currBrick.getY() - currPow.getMyImageView().getBoundsInLocal().getHeight() / 2 + currBrick.getMyImageView().getBoundsInLocal().getHeight()/2);
            currPow.getMyImageView().setVisible(false);
            root.getChildren().add(currPow.getMyImageView());
            addPowers.add(currPow);
        }
        return addPowers;
    }
}
