package game;

import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Collections;

public class PowerupGenerator {

    private ArrayList<Powerup> typeOfPowers = new ArrayList<>();

    public ArrayList<Powerup> setPowerupsForLevel(int level, ArrayList<Brick> myBricks, Group root) {
        ArrayList<Brick> powerupBrickList = new ArrayList<>();
        for(Brick i : myBricks) {
            if(i.getBrickType() == 9) {
                powerupBrickList.add(i);
            }
        }
        if(level == 1) {
            levelOneSetup(myBricks, root);
        }
        else if (level == 2) {
            levelTwoSetup(myBricks, root);
        }
        else if (level == 3) {
            levelThreeSetup(myBricks, root);
        }

        ArrayList<Powerup> addPowers = new ArrayList<>();
        for(int i = 0; i < powerupBrickList.size(); i++) {
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

    public void levelOneSetup(ArrayList<Brick> myBricks, Group root) {
        for(int i = 0; i < 2; i++) {
            typeOfPowers.add(new BiggerPaddle("powerup_paddle.gif"));
        }
    }

    public void levelTwoSetup(ArrayList<Brick> myBricks, Group root) {
        for(int i = 0; i < 2; i++) {
            typeOfPowers.add(new BiggerPaddle("powerup_speed.gif"));
        }
        for(int i = 0; i < 2; i++) {
            typeOfPowers.add(new BiggerPaddle("powerup_paddle.gif"));
        }
    }

    public void levelThreeSetup(ArrayList<Brick> myBricks, Group root) {
        typeOfPowers.add(new FasterBall("powerup_speed.gif"));
        typeOfPowers.add(new DoubleBall("powerup_doubleball.gif"));
        for(int i = 0; i < 2; i++) {
            typeOfPowers.add(new BiggerPaddle("powerup_paddle.gif"));
        }
    }
}
