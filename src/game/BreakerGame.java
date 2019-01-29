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

    ImageView myBall, myPaddle;


    private Scene scene1, scene2;

    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.BLACK;
    private int WIDTH = 750;
    private int HEIGHT = 500;


    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(WIDTH, HEIGHT, BACKGROUND);
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
        myBall = new ImageView(image);
//        // x and y represent the top left corner, so center it
        myBall.setX(width / 2 - myBall.getBoundsInLocal().getWidth() / 2);
        myBall.setY(height / 2 + 50 - myBall.getBoundsInLocal().getHeight() / 2);

        Paddle p = new Paddle();
        myPaddle = p.getMyImageView();
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - 80  - myPaddle.getBoundsInLocal().getHeight() / 2);


//        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBall);
        root.getChildren().add(myPaddle);
//        // respond to input
        return scene;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
