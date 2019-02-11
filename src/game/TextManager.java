package game;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TextManager {
    public void setText(Text myText, int xPos, int yPos, Paint value, Group root) {
        myText.setX(xPos);
        myText.setY(yPos);
        myText.setFill(value);
        myText.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        root.getChildren().add(myText);
    }
}
