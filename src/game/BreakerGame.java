package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.GHOSTWHITE;
    private int WIDTH = 750;
    private int HEIGHT = 500;
    private Timeline gameLoop;
    private Scene myScene;
    private Group sceneNodes;
    private SpriteManager spriteManager;
    private String windowTitle;

    //dunno if we wanna store them like this
    ImageView myBall, myPaddle, myBrick;
    Paddle p = new Paddle();




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
        updateSprites(elapsedTime);
        checkAndHandleCollisions();
    }


    private void updateSprites(double elapsedTime) {
        myBall.setY(myBall.getY() - 100 * elapsedTime);
    }

    private void checkAndHandleCollisions() {

    }


    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties

        /*Temp until Ball class is implemented */
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("ball.gif"));
        myBall = new ImageView(image);
        myBall.setX(width / 2 - myBall.getBoundsInLocal().getWidth() / 2);
        myBall.setY(height - 35 - myBall.getBoundsInLocal().getHeight() / 2);

        myPaddle = p.getMyImageView();
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - 15- myPaddle.getBoundsInLocal().getHeight() / 2);

        /* Probably implement as a list of ImageViews? Separate method that returns a list of Bricks from data? IDK*/
        Brick b = new Brick();
        myBrick = b.getMyImageView();
        myBrick.setX(width / 2 - myBrick.getBoundsInLocal().getWidth() / 2);
        myBrick.setY(height / 2 - myBrick.getBoundsInLocal().getHeight() / 2);

//        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBall);
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBrick);

//        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        return scene;
    }


    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + p.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - p.getSpeed());
        }
    }


    public static void main (String[] args) {
        launch(args);
    }
}
