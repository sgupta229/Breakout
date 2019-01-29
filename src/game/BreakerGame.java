package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BreakerGame extends Application {
    private int framesPerSecond;
    private Timeline gameLoop;
    private Scene myScene;
    private Group sceneNodes;
    private SpriteManager spriteManager;
    private String windowTitle;

    ImageView myBouncer;

    private Scene scene1, scene2;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(500, 500, Color.WHEAT);
        stage.setScene(myScene);
        stage.setTitle("Breaker by Team 17");
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }


    private void step(double elapsedTime) {
    }

    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("ball.gif"));
        myBouncer = new ImageView(image);
//        // x and y represent the top left corner, so center it
        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(height / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
//        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBouncer);
//        // respond to input
        return scene;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
