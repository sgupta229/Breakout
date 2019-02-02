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

    private int livesLeft;
    private Text lifeCount;
    private Text levelNum = new Text();
    private Text scoreText = new Text();
    private int scoreNum;

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
        levelNum.setText("Level: " + currLevel());
        scoreText.setText("Score: " + scoreNum);
        //check for loss
        if (lostALife){
            livesLeft -= 1;
            lifeCount.setText("Lives: " + livesLeft);
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
                scoreNum += 100;
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
        Label finalScore = new Label("Your final score was: " + scoreNum);
        label1.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        finalScore.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        Button startButton = new Button("Play Again");
        startButton.setOnAction(e -> primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(label1, finalScore, startButton);
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

        var root = new  Group();
        // create one top level collection to organize the things in the scene
        // create a place to see the shapes
        stageOne = new Scene(root, width, height, background);
        // make some shapes and set their properties
        setUpText(root);

        //Should we put setPosition in the constructor?
        myBall = new Ball("ball.gif");

        var ballX = width / 2 - myBall.getWidth() / 2;
        var ballY = height - 35 - myBall.getHeight() / 2;
        myBall.setPosition(ballX, ballY);

        myPaddle = new Paddle("paddle.gif");
        var paddleX = width / 2 - myPaddle.getWidth() / 2;
        var paddleY = height - 25 - myPaddle.getHeight() / 2;
        myPaddle.setPosition(paddleX, paddleY);

        root.getChildren().add(myBall.getMyImageView());
        root.getChildren().add(myPaddle.getMyImageView());

        myBricks = generateBricks(root, width, height);

        stageOne.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        return stageOne;
    }

    private int currLevel() {
        if(primaryStage.getScene() == this.stageOne) {
            return 1;
        }
        else if(primaryStage.getScene() == this.stageTwo) {
            return 2;
        }
        else if(primaryStage.getScene() == this.stageThree) {
            return 3;
        }
        return 0;
    }

    private void setUpText(Group root) {
        livesLeft = 3;
        lifeCount = new Text("Lives: " + livesLeft);
        lifeCount.setX(5);
        lifeCount.setY(15);
        lifeCount.setFill(Color.ORANGERED);
        lifeCount.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        levelNum.setX(360);
        levelNum.setY(15);
        levelNum.setFill(Color.GREEN);
        levelNum.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        scoreText.setX(675);
        scoreText.setY(15);
        scoreText.setFill(Color.BLUEVIOLET);
        scoreText.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        root.getChildren().addAll(lifeCount, levelNum, scoreText);
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

        else if (code == KeyCode.L){
            livesLeft += 1;
            lifeCount.setText("Lives: " + livesLeft);
        }
        else if (code == KeyCode.R){
            myBall.reset(myPaddle);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
