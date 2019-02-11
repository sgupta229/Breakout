package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BreakerGame extends Application {
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.GHOSTWHITE;
    private static final int WIDTH = 750;
    private static final int HEIGHT = 500;
    private static final int LIVES_AT_START = 3;
    private Timeline animation;

    private Ball myBall;
    private Ball secondBall;
    private Ball[] myBallArray;
    private ArrayList<Brick> myBricks;
    private Paddle myPaddle;
    private ArrayList<Powerup> myPowerups;

    private HighScoreUpdater highScoreUpdater = new HighScoreUpdater();
    private int livesLeft;
    private Text lifeCount;
    private Text levelNum = new Text();
    private Text scoreText = new Text();
    private int scoreNum;
    private Text highScoreText = new Text();

    private Stage primaryStage;
    private boolean lostALife = false;
    private boolean isTest = false;
    private String testType;
    private int numSteps;
    private double mouseX;
    private int currentLevel = 1;

    private int bricksLeft;



    @Override
    public void start (Stage stage) {
        primaryStage = stage;
        var myScene = setupSplashScreen(WIDTH, HEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Breakout Game by Team 17");
        primaryStage.show();
    }

    private Scene setupSplashScreen (int width, int height, Paint background) {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        var splashScene = new Scene(vb, width, height, background);

        Label gameIntro = new Label("Welcome to Breakout! Click 'Start Game' and then press SPACE to start playing!");
        Label instructions = new Label("Use the mouse to control the paddle. The paddle will not move if the mouse is outside of the window.");
        Label cheatCodes  = new Label("Here are some cheat codes: SPACE - pause, R - reset ball and paddle, M - reset game, F - faster ball, S - slower ball");
        gameIntro.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        instructions.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        instructions.setStyle("-fx-border-color:red; -fx-padding:3px;");
        cheatCodes.setFont(Font.font("Amble CN", FontWeight.BOLD, 12.5));
        cheatCodes.setStyle("-fx-border-color:green; -fx-padding:3px;");
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(gameIntro, instructions, cheatCodes, startButton);
        return splashScene;
    }

    //We should try to combine this and Splash screen somehow
    private Scene setupResetScreen(int width, int height, Paint background) {
        animation.stop();

        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        var scene = new Scene(vb, width, height, background);

        String str;

        if (livesLeft <= 0) str = "lost :( ";
        else str = "win :D! ";
        Label label1 = new Label("You " + str + "Your final score was " + scoreNum + "!");

        Label finalScore = new Label("Press the button to play again!");

        highScoreUpdater.setCurrentScore(scoreNum);
        highScoreUpdater.addNewScoreToFile();

        scoreNum = 0;

        label1.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        finalScore.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        Button startButton = new Button("Play Again");
        startButton.setOnAction(e -> primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(label1, finalScore, startButton);

        return scene;
    }

    private Scene setupGame (int width, int height, Paint background) {

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        var root = new  Group();
        var scene = new Scene(root, width, height, background);

        //adapted from https://stackoverflow.com/questions/18597939/handle-mouse-event-anywhere-with-javafx
        scene.setOnMouseMoved(new EventHandler<>() {
            @Override public void handle(MouseEvent event) {
                mouseX = event.getSceneX();
            }
        });

        livesLeft = LIVES_AT_START;
        setUpText(root);

        myBall = new Ball("ball.gif", width, height);
        var ballX = width / 2 - myBall.getWidth() / 2;
        var ballY = height - 35 - myBall.getHeight() / 2;
        myBall.setPosition(ballX, ballY);

        secondBall = new Ball("ballTwo.gif", width, height);
        secondBall.setPosition(1000, 1000);
        secondBall.getMyImageView().setVisible(false);

        myBallArray = new Ball[] {myBall, secondBall};

        myPaddle = new Paddle("paddle.gif");
        var paddleX = width / 2 - myPaddle.getWidth() / 2;
        var paddleY = height - 25 - myPaddle.getHeight() / 2;
        myPaddle.setPosition(paddleX, paddleY);

        root.getChildren().add(myBall.getMyImageView());
        root.getChildren().add(secondBall.getMyImageView());
        root.getChildren().add(myPaddle.getMyImageView());

        var gen = new BrickGenerator();
        gen.generateBricks(root, width, "lvl" + currentLevel +"_config.txt");
        bricksLeft = gen.getNumBricks();
        myBricks = gen.getMyBricks();

        myPowerups = setPowerups(myBricks, root);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        highScoreUpdater.updateHighScore();

        return scene;
    }

    private void step(double elapsedTime) {
        if (isTest){
            numSteps++;
            checkTest(testType);
        }

        updateSprites(elapsedTime);
        mouseHandle();

        levelNum.setText("Level: " + currentLevel);
        scoreText.setText("Score: " + scoreNum);
        highScoreText.setText("High Score: " + highScoreUpdater.getHighscore());

        checkLossAndWin();
        checkAndHandleCollisions();
    }

    private void checkLossAndWin() {
        if (lostALife){
            livesLeft -= 1;
            lifeCount.setText("Lives: " + livesLeft);
            if (livesLeft <= 0){
                primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
            }
        }
        if (bricksLeft == 0){
            if (currentLevel == 3)
                primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
            else {
                animation.stop();
                currentLevel++;
                primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND));
            }
        }
    }

    private void mouseHandle(){
        if(myBall.getSpeed() == 0) {
            myPaddle.setPosition(WIDTH / 2 - myPaddle.getWidth() / 2, HEIGHT - 25 - myPaddle.getHeight() / 2);
        }
        else if (mouseX + myPaddle.getWidth() / 2 >= WIDTH) {
            mouseX = WIDTH - myPaddle.getWidth() / 2;
        }
        else if (mouseX - myPaddle.getWidth() / 2 <= 0){
            myPaddle.setX(0);
        }
        else {
            myPaddle.setX(mouseX - myPaddle.getWidth() / 2);
        }
    }

    private void updateSprites(double elapsedTime) {
        lostALife = myBall.checkMissedBall(myPaddle);
        myBall.incrementPos(elapsedTime, myPaddle);
        secondBall.incrementPos(elapsedTime, myPaddle);
        for(Powerup i : myPowerups) {
            i.checkBrickHit(elapsedTime, myBricks, myBall, secondBall);
            i.incrementPos(elapsedTime);
        }
    }

    private void checkAndHandleCollisions() {
        var toRemove = new ArrayList();

        for (Ball ball: myBallArray) {
            for (Brick brick : myBricks) {
                if (ball.isCollided(brick)) {
                    ball.brickCollision(brick);
                    if (!(brick instanceof IndestructibleBrick)) {
                        scoreNum += brick.getBrickType();
                        if (brick.getBrickType() <= 6 || brick.getBrickType() == 9) {
                            toRemove.add(brick);
                            bricksLeft--;
                        }
                        brick.handleCollision();
                    }
                    break;
                }
            }
            myBricks.removeAll(toRemove);
            if (ball.isCollided(myPaddle)) {
                ball.paddleCollision(myPaddle);
            }
        }
        for (Powerup p : myPowerups) {
            if(p.isCollided(myPaddle)) {
                p.paddleCollision(myPaddle, myBall, secondBall);
            }
        }
    }

    private void setUpText(Group root) {
        lifeCount = new Text("Lives: " + livesLeft);
        lifeCount.setX(5);
        lifeCount.setY(15);
        lifeCount.setFill(Color.ORANGERED);
        lifeCount.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        levelNum.setX(360);
        levelNum.setY(15);
        levelNum.setFill(Color.GREEN);
        levelNum.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        scoreText.setX(677);
        scoreText.setY(15);
        scoreText.setFill(Color.BLUEVIOLET);
        scoreText.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        highScoreText.setX(643);
        highScoreText.setY(30);
        highScoreText.setFill(Color.DARKSALMON);
        highScoreText.setFont(Font.font("times", FontWeight.BOLD, FontPosture.REGULAR, 15));
        root.getChildren().addAll(lifeCount, levelNum, scoreText, highScoreText);
    }


    private ArrayList<Powerup> setPowerups(ArrayList<Brick> myBricks, Group root) {
        ArrayList<Brick> brickTens = new ArrayList<>();
        for(Brick i : myBricks) {
            if(i.getBrickType() == 9) {
                brickTens.add(i);
            }
        }
        ArrayList<Powerup> typeOfPowers = new ArrayList<>();
        typeOfPowers.add(new FasterBall("sizepower.gif"));
        typeOfPowers.add(new DoubleBall("extraballpower.gif"));
        for(int i = 0; i < 2; i++) {
            typeOfPowers.add(new BiggerPaddle("pointspower.gif"));
        }
        ArrayList<Powerup> addPowers = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Collections.shuffle(typeOfPowers);
            Powerup currPow = typeOfPowers.get(0);
            typeOfPowers.remove(0);
            currPow.setBrick(brickTens.get(i));
            Brick currBrick = currPow.getBrick();
            currPow.setX(currBrick.getX() - currPow.getMyImageView().getBoundsInLocal().getWidth() / 2 + currBrick.getMyImageView().getBoundsInLocal().getWidth()/2);
            currPow.setY(currBrick.getY() - currPow.getMyImageView().getBoundsInLocal().getHeight() / 2 + currBrick.getMyImageView().getBoundsInLocal().getHeight()/2);
            currPow.getMyImageView().setVisible(false);
            root.getChildren().add(currPow.getMyImageView());
            addPowers.add(currPow);
        }
        return addPowers;
    }

    private void handleKeyInput (KeyCode code) {

        //make ball go faster
        if (code == KeyCode.F) {
            if(myBall.getSpeed() != 0) {
                myBall.changeSpeed(myBall.getSpeed() + 1);
            }
        }
        //make ball go slower
        else if (code == KeyCode.S) {
            if (myBall.getSpeed() >= 1.0) {
                myBall.changeSpeed(myBall.getSpeed() -1);
            }
        }
        //freeze/unfreeze the game
        else if (code == KeyCode.SPACE) {
            if(myBall.getSpeed() != 0) {
                myBall.changeSpeed(0);
                if(secondBall.getMyImageView().isVisible()) {
                    secondBall.changeSpeed(0);
                }
            }
            else {
                myBall.changeSpeed(2.75);
                if(secondBall.getMyImageView().isVisible()) {
                    secondBall.changeSpeed(2.75);
                }
            }
        }

        else if (code == KeyCode.L){
            livesLeft += 1;
            lifeCount.setText("Lives: " + livesLeft);
        }
        else if (code == KeyCode.R){
            myBall.reset(myPaddle);
        }

        else if (code == KeyCode.M){
            animation.stop();
            scoreNum = 0;
            currentLevel = 1;
            primaryStage.setScene(setupSplashScreen(WIDTH, HEIGHT, BACKGROUND));
        }
        else if (code == KeyCode.COMMA){
            setupForTestScene();
            primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_corner_bounce.txt"));
        }
        else if (code == KeyCode.PERIOD){
            setupForTestScene();
            primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_brick_destroy.txt"));

        }
        else if (code == KeyCode.SLASH){
            setupForTestScene();
            primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_lose_life.txt"));
        }

        //starts level according to digit input
        else if (code == KeyCode.DIGIT1){
            animation.stop();
            currentLevel = 1;
            primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND));
        }
        else if (code == KeyCode.DIGIT2){
            animation.stop();
            currentLevel = 2;
            primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND));
        }
        else if (code == KeyCode.DIGIT3){
            animation.stop();
            currentLevel = 3;
            primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND));
        }
    }

    private void setupForTestScene() {
        myBricks.clear();
        var b = new Brick("brick1.gif", 1);
        b.setPosition(10000, 0);
        myBricks.add(b);
    }

    private Scene setupForTest(int width, int height, Paint background, String testFile){
        livesLeft = LIVES_AT_START;
        numSteps = 0;
        isTest = true;
        testType = testFile;
        var config = readConfigFile(testFile);

        var root = new Group();
        var scene = new Scene(root, width, height, background);
        setUpText(root);

        if (config.get(0) == 1) {
            myBall = new Ball("ball.gif", width, height);
            myBall.setPosition(config.get(1), config.get(2));
            myBall.changeSpeedAndVelocity(config.get(3), config.get(4), config.get(5));
            root.getChildren().add(myBall.getMyImageView());
            myBallArray = new Ball[] {myBall};
        }

        if (config.get(6) == 1) {
            myPaddle = new Paddle("paddle.gif");
            myPaddle.setPosition(config.get(7), config.get(8));
            root.getChildren().add(myPaddle.getMyImageView());
        }

        if (config.get(9) == 1) {
            var b = new Brick("brick1.gif", WIDTH);
            b.setPosition(config.get(10), config.get(11));
            myBricks.add(b);
            root.getChildren().add(b.getMyImageView());
        }
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private void checkTest(String testType) {
        if (testType.equals("test_corner_bounce.txt")){
            if (myBall.getXDirection()>0 && myBall.getX() == 100 && myBall.getY() == 100){
                Platform.exit();
                System.out.println("Corner Bounce Test successful!");
            }
        }
        else if (testType.equals("test_brick_destroy.txt")){
            if (myBricks.size() == 1){
                Platform.exit();
                System.out.println("Brick Destroy Test successful!");
            }
        }
        else if (testType.equals("test_lose_life.txt")){
            if (livesLeft == 2){
                Platform.exit();
                System.out.println("Lose Life Test successful!");
            }

        }
        if (numSteps > 63){
            Platform.exit();
            System.out.println("test failed");
        }

    }

    public ArrayList<Integer> readConfigFile(String filename) {
        var input = new Scanner(this.getClass().getClassLoader().getResourceAsStream(filename));
        input.useDelimiter(" |\\n");
        ArrayList<Integer> results = new ArrayList<>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
