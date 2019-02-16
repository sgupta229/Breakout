package game;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This is a utility class with one method. It made it easier to set text in the game.
 */

public class TextManager {

    /**
     * This method is called in the BreakerGame class to set up different types of text. This method sets the location,
     * color, and font of the text.
     * @param myText the words of the text
     * @param xPos the x position of the text
     * @param yPos the y position of the text
     * @param value the Paint color of the text
     * @param root the Group root of the text.
     */

    public void setText(Text myText, int xPos, int yPos, Paint value, Group root) {
        myText.setX(xPos);
        myText.setY(yPos);
        myText.setFill(value);
        myText.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        root.getChildren().add(myText);
    }
}
