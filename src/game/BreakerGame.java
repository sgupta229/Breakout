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
import javafx.scene.text.*;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class BreakerGame extends Application {
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.GHOSTWHITE;
    private static final int NUM_BRICKS = 20;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;
    private Timeline gameLoop;
    private Scene myScene;
    private Group sceneNodes;
    private SpriteManager spriteManager;
    private String windowTitle;

    //dunno if we wanna store them like this
    //ImageView myBall;
    Ball myBall;
    ArrayList<Brick> myBricks;
    Paddle myPaddle;

    private Scene splashScene, stageOne, stageTwo, stageThree;
    private Stage primaryStage;

    @Override
    public void start (Stage stage) {
        primaryStage = stage;
        myScene = setupSplashScreen(WIDTH, HEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Breakout Game by Team 17");
        primaryStage.show();

        // attach "game loop" to timeline to play it
    }

    private void step(double elapsedTime) {
        updateSprites(elapsedTime);
        checkAndHandleCollisions();
    }


    private void updateSprites(double elapsedTime) {
        //myBall.setY(myBall.getY() - 100 * elapsedTime);
        myBall.incrementPos(elapsedTime);
    }

    private void checkAndHandleCollisions() {
        for (Brick s: myBricks) {
            if (isCollided(myBall, s)) {
                s.handleCollision();
//                NEED TO IMPLEMENT BALL CLASS FIRST
//                myBall.handleCollision();
            }
        }
        if (isCollided(myBall, myPaddle)){
//            NEED TO IMPLEMENT BALL CLASS FIRST
//            myBall.handleCollision();
        }
        myBall.handleCollision(myPaddle, myBricks);
    }

    //TEMPORARY UNTIL BALL IS IMPLEMENTED
    private boolean isCollided(Sprite a, Sprite b){
        return a.myImageView.getBoundsInLocal().intersects(b.myImageView.getBoundsInLocal());
    }

    //THE REAL isCollided()
//    private boolean isCollided(Sprite a, Sprite b){
//        return a.myImageView.getBoundsInLocal().intersects(b.myImageView.getBoundsInLocal());
//    }

    private Scene setupSplashScreen (int width, int height, Paint background) {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        splashScene = new Scene(vb, width, height, background);

        Label label1 = new Label("Welcome to Breakout! Try to break all the bricks before losing all your lives!");
        label1.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(label1, startButton);
        return splashScene;
    }


    private Scene setupGame (int width, int height, Paint background) {

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        stageOne = new Scene(root, width, height, background);
        // make some shapes and set their properties

        /*Temp until Ball class is implemented */
        //var image = new Image(this.getClass().getClassLoader().getResourceAsStream("ball.gif"));
        myBall = new Ball("ball.gif");
        myBall.setX(width / 2 - myBall.myImageView.getBoundsInLocal().getWidth() / 2);
        myBall.setY(height - 35 - myBall.myImageView.getBoundsInLocal().getHeight() / 2);

        myPaddle = new Paddle("paddle.gif");
        double x = width / 2 - myPaddle.getWidth() / 2;
        double y = height - 25 - myPaddle.getHeight() / 2;
        myPaddle.setInitialPosition(x, y);

        root.getChildren().add(myBall.getMyImageView());
        root.getChildren().add(myPaddle.getMyImageView());

        myBricks = generateBricks(root, width, height);

        stageOne.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        // respond to input
        return stageOne;
    }

    private ArrayList<Brick> generateBricks(Group root, double width, double height) {
        var list = new ArrayList<Brick>();

        int count = 0;
        int brickType = 5;
        int currentY = 50;
        int currentX = 0;
        while (true){
            var b = new Brick("brick" + brickType + ".gif");
            b.setInitialPosition(currentX, currentY);
            root.getChildren().add(b.getMyImageView());
            list.add(b);
            if (currentX + b.getMyImageView().getBoundsInLocal().getWidth() > width){
                if (currentY + b.getHeight() >= height - 200){
                    break;
                }
                currentX = 0;
                currentY += b.getHeight();
                if (brickType == 1) brickType = 5;
                else brickType -= 1;
            }
            else{
                currentX += b.getMyImageView().getBoundsInLocal().getWidth();
            }
            count++;
        }
        return list;
    }


    private void handleKeyInput (KeyCode code) {
        int paddleSpeed = myPaddle.getSpeed();
        if (code == KeyCode.RIGHT && !(myPaddle.getX() + myPaddle.getWidth() > myScene.getWidth())) {
            myPaddle.setX(myPaddle.getX() + paddleSpeed);
        }
        else if (code == KeyCode.LEFT && !(myPaddle.getX()< 0)) {
            myPaddle.setX(myPaddle.getX() - paddleSpeed);
        }
    }


    public static void main (String[] args) {
        launch(args);
    }
}
