
import javafx.animation.Timeline;
import javafx.scene.*;
import javafx.scene.paint.Paint;

public class BreakerGame {
    private int framesPerSecond;
    private Timeline gameLoop;
    private Scene gameSurface;
    private Group sceneNodes;
    private SpriteManager spriteManager;
    private String windowTitle;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


    public void startGame(){

    }

    private Scene setupGame (int width, int height, Paint background) {
        return null;
    }
}
