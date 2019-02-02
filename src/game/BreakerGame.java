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
import java.util.Random;

public class BreakerGame extends Application {
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.GHOSTWHITE;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;
    private Timeline animation;
    private Scene myScene;

    private Ball myBall;
    private ArrayList<Brick> myBricks;
    private Paddle myPaddle;

    private Group currRoot;
    private int livesLeft;
    private Label lifeCount;

    private Scene splashScene, stageOne, stageTwo, stageThree;
    private Stage primaryStage;
    private boolean lostALife = false;

    @Override
    public void start (Stage stage) {
        primaryStage = stage;
        myScene = setupSplashScreen(WIDTH, HEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Breakout Game by Team 17");
        primaryStage.show();
    }

    private void step(double elapsedTime) {
        updateSprites(elapsedTime);

        //check for loss
        if (lostALife){
            livesLeft -= 1;
            currRoot.getChildren().remove(lifeCount);
            lifeCount = new Label("Lives: " + livesLeft);
            currRoot.getChildren().add(lifeCount);
            if (livesLeft <= 0){
                primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
            }
        }

        //check for win
        if (myBricks.isEmpty()){
            primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
        }

        checkAndHandleCollisions();
    }


    private void updateSprites(double elapsedTime) {
        lostALife = myBall.incrementPos(elapsedTime, myPaddle);
    }

    private void checkAndHandleCollisions() {
        var toRemove = new ArrayList();
        for (Brick b: myBricks) {
            if (isCollided(myBall, b)) {
                myBall.brickCollision(b);
                b.handleCollision();
                toRemove.add(b);
            }
        }
        myBricks.removeAll(toRemove);
        if (isCollided(myBall, myPaddle)){
            myBall.paddleCollision(myPaddle);
        }
    }

    private boolean isCollided(Sprite a, Sprite b){
        return a.getMyImageView().getBoundsInParent().intersects(b.getMyImageView().getBoundsInParent());
    }

    //We should try to combine this and Splash screen somehow
    private Scene setupResetScreen(int width, int height, Paint background) {
        animation.stop();

        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        var scene = new Scene(vb, width, height, background);

        String str;
        if (livesLeft <= 0) str = "lost";
        else str = "win";
        Label label1 = new Label("You " + str + "! Press the button to play again!");
        label1.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        Button startButton = new Button("Play Again");
        startButton.setOnAction(e -> primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(label1, startButton);
        return scene;
    }

    private Scene setupSplashScreen (int width, int height, Paint background) {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        splashScene = new Scene(vb, width, height, background);

        Label label1 = new Label("Welcome to Breakout! Try to break all the bricks before losing all your lives! Use 'SPACE' to start/pause \nthe game" +
                ", 'F' to speed the ball up, and 'S' to slow the ball down.");
        label1.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(label1, startButton);
        return splashScene;
    }


    private Scene setupGame (int width, int height, Paint background) {

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        livesLeft = 3;
        lifeCount = new Label("Lives: " + livesLeft);

        // create one top level collection to organize the things in the scene
        currRoot = new Group();
        // create a place to see the shapes
        stageOne = new Scene(currRoot, width, height, background);
        // make some shapes and set their properties
        currRoot.getChildren().add(lifeCount);

        //Should we put setPosition in the constructor?
        myBall = new Ball("ball.gif");

        var ballX = width / 2 - myBall.getWidth() / 2;
        var ballY = height - 35 - myBall.getHeight() / 2;
        myBall.setPosition(ballX, ballY);

        myPaddle = new Paddle("paddle.gif");
        var paddleX = width / 2 - myPaddle.getWidth() / 2;
        var paddleY = height - 25 - myPaddle.getHeight() / 2;
        myPaddle.setPosition(paddleX, paddleY);

        currRoot.getChildren().add(myBall.getMyImageView());
        currRoot.getChildren().add(myPaddle.getMyImageView());

        myBricks = generateBricks(currRoot, width, height);

        stageOne.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        return stageOne;
    }

    private ArrayList<Brick> generateBricks(Group root, double width, double height) {
        var list = new ArrayList<Brick>();

        int count = 0;
        int brickType = 5;
        int currentY = 50;
        int currentX = 0;
        while (true){

            var b = new Brick("brick" + brickType + ".gif", width, height);
            b.setPosition(currentX, currentY);
            root.getChildren().add(b.getMyImageView());
            list.add(b);
            if (count == 6){
                count = 0;
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
                count++;
            }
        }
        return list;
    }


    private void handleKeyInput (KeyCode code) {

        //paddle controls
        int paddleSpeed = myPaddle.getSpeed();
        if (code == KeyCode.RIGHT && !(myPaddle.getX() + myPaddle.getWidth() > myScene.getWidth())) {
            if(myBall.getSpeed() != 0) {
                myPaddle.setX(myPaddle.getX() + paddleSpeed);
            }
        }
        else if (code == KeyCode.LEFT && !(myPaddle.getX()< 0)) {
            if(myBall.getSpeed() != 0) {
                myPaddle.setX(myPaddle.getX() - paddleSpeed);
            }
        }
        //make ball go faster
        else if (code == KeyCode.F) {
            myBall.changeSpeed(myBall.getSpeed() + 1);
        }
        //make ball go slower
        else if (code == KeyCode.S) {
            if (myBall.getSpeed() >= 2.5) {
                myBall.changeSpeed(myBall.getSpeed() -1);
            }
        }
        //freeze the ball
        else if (code == KeyCode.SPACE) {
            if(myBall.getSpeed() != 0) {
                myBall.changeSpeed(0);
            }
            else {
                myBall.changeSpeed(1.5);
            }
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
