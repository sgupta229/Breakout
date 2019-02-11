package game;

import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Collections;

public class PowerupGenerator {

    public ArrayList<Powerup> setPowerups(ArrayList<Brick> myBricks, Group root) {
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
