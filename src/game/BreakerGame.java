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
import java.util.List;
import java.util.Scanner;

public class BreakerGame extends Application {
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.GHOSTWHITE;
    private static final int WIDTH = 750;
    private static final int HEIGHT = 500;
    private static final int LIVES_AT_START = 3;
    private static final int MAX_LEVEL = 3;
    private Timeline animation;

    private Ball myBall;
    private Ball secondBall;
    private Ball[] myBallArray;
    private List<Brick> myBricks;
    private Paddle myPaddle;
    private List<Powerup> myPowerups = new ArrayList<>();

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

    /** Standard start method
     *
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        primaryStage = stage;
        var myScene = setupSplashScreen(WIDTH, HEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Breakout Game by Team 17");
        primaryStage.show();
    }

    /**
     * This method is responsible for setting up the splash screen.
     * @param width The width of the splash screen
     * @param height The height of the splash screen
     * @param background The background color of the splash screen
     * @return the splash screen scene
     */

    private Scene setupSplashScreen (int width, int height, Paint background) {
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        var splashScene = new Scene(vb, width, height, background);
        currentLevel = 1;
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

    /**
     * Similar to the setupSplashScreen method, this method sets up the reset screen. This method sets the screen up for the game
     * if the player wins or loses.
     * @param width the width of the screen
     * @param height the height of the screen
     * @param background the color of the screen
     * @return The reset screen scene.
     */

    //We should try to combine this and Splash screen somehow
    private Scene setupResetScreen(int width, int height, Paint background) {
        if (isTest){

        }
        animation.stop();

        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        var scene = new Scene(vb, width, height, background);

        String str;

        if (livesLeft <= 0) str = "lost :( ";
        else str = "win :D! ";

        String testAddOn;
        if (isTest) {
            testAddOn = " (WIN TEST SUCCESSFUL)";
        }
        else {
            testAddOn = "";
        }

            Label label1 = new Label("You " + str + "Your final score was " + scoreNum + "!" + testAddOn);


        Label finalScore = new Label("Press the button to play again!");

        highScoreUpdater.setCurrentScore(scoreNum);
        highScoreUpdater.addNewScoreToFile();

        scoreNum = 0;

        label1.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        finalScore.setFont(Font.font("Amble CN", FontWeight.BOLD, 15));
        Button startButton = new Button("Play Again");
        startButton.setOnAction(e -> primaryStage.setScene(setupSplashScreen(WIDTH, HEIGHT, BACKGROUND)));

        vb.getChildren().addAll(label1, finalScore, startButton);

        return scene;
    }

    /**
     * A critical method for the whole program. This method sets up each level of the game. It starts the animation and adds all
     * appropriate ImageViews to the scene including the ball, paddle, bricks, and powerups. This method also updates the high score to
     * be displayed during this game.
     * @param width The width of the screen
     * @param height the height of the screen
     * @param background the background color of the screen
     * @return the scene of the current level.
     */

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

        bricksLeft = 0;
        livesLeft = LIVES_AT_START;
        setUpText(root);

        myBall = new Ball("ball.gif", width, height);
        var ballX = width / 2 - myBall.getWidth() / 2;
        var ballY = height - 35 - myBall.getHeight() / 2;
        myBall.setPosition(ballX, ballY);

        initSecondBall();

        myPaddle = new Paddle("paddle.gif");
        var paddleX = width / 2 - myPaddle.getWidth() / 2;
        var paddleY = height - 25 - myPaddle.getHeight() / 2;
        myPaddle.setPosition(paddleX, paddleY);

        root.getChildren().add(myBall.getMyImageView());
        root.getChildren().add(secondBall.getMyImageView());
        root.getChildren().add(myPaddle.getMyImageView());

        var gen = new BrickGenerator();
        gen.generateBricks(root, width,"lvl" + currentLevel +"_config.txt");
        bricksLeft = gen.getNumBricks();
        myBricks = gen.getMyBricks();

        PowerupGenerator powerupGenerator = new PowerupGenerator();
        myPowerups = powerupGenerator.setPowerups(myBricks, root);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        highScoreUpdater.updateHighScore();

        return scene;
    }

    /**
     * This method is responsible for everything that should be checked at a step by step increment. This updates which bricks have been
     * hit, which powerups have been released/caught, and the movement of the ball. This method is also responsible for updating the level
     * text, score text, and high score text of the game. We also keep track of how many lives are left in this method.
     * @param elapsedTime the time increments of the game
     */

    private void step(double elapsedTime) {
        if (isTest){
            numSteps++;
            checkTest(testType);
        }
        updateSprites(elapsedTime);
        mouseHandle();

        for(Powerup i : myPowerups) {
            i.checkBrickHit(myBricks, myBall, secondBall);
            i.incrementPos(elapsedTime);
        }

        levelNum.setText("Level: " + currentLevel);
        scoreText.setText("Score: " + scoreNum);
        highScoreText.setText("High Score: " + highScoreUpdater.getHighscore());

        //check for loss
        if (lostALife){
            livesLeft -= 1;
            lifeCount.setText("Lives: " + livesLeft);
            if (livesLeft < 0){
                primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
            }
        }
        //check for win
        if (bricksLeft == 0){
            if (currentLevel == MAX_LEVEL)
                primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
            else {
                animation.stop();
                currentLevel++;
                primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND));
            }
        }
        checkAndHandleCollisions();
    }

    /**
     * This method is responsible for the handling the movement of the paddle using the mouse.
     */

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

    /**
     * This method is called by the step method. This method is responsible for updating the sprites such as the ball location and the
     * second ball location. This method also keeps track of how many lives have been lost.
     * @param elapsedTime
     */

    private void updateSprites(double elapsedTime) {
        lostALife = myBall.checkMissedBall(myPaddle);
        myBall.incrementPos(elapsedTime, myPaddle);
        secondBall.incrementPos(elapsedTime, myPaddle);
    }

    /**
     * This method is also called by the step method. This method checks for collisions between the both balls and each brick
     * to determine whether or not that bricks needs to be removed. The method also handles the collisions between the the paddle and the balls
     * and the paddle and the powerups.
     */

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

    /**
     * This is a small utility function that instantiates a TextManager object and sets up the text in the game.
     * @param root the root to which the Text should be added to.
     */

    private void setUpText(Group root) {
        lifeCount = new Text("Lives: " + livesLeft);
        TextManager textManager = new TextManager();
        textManager.setText(this.lifeCount, 5, 15, Color.ORANGERED, root);
        textManager.setText(this.levelNum, 360, 15, Color.GREEN, root);
        textManager.setText(this.scoreText, 677, 15, Color.BLUEVIOLET, root);
        textManager.setText(this.highScoreText, 643, 30, Color.DARKSALMON, root);
    }

    /**
     * This method handles all the key input from the user. The user can press a variety of keyboard keys to get the game to do something
     * F and S can change the speed of the ball. Space pauses the game. L adds a life. R resets the ball and paddle location.
     * M restarts the game. , . and / each set up a different text depending on the level. Digits 1, 2, 3 allow the user to directly jump to
     * those levels.
     */

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
                secondBall.changeSpeed(0);
            }
            else {
                myBall.changeSpeed(2.75);
                secondBall.changeSpeed(2.75);
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
            if (currentLevel ==1) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_corner_bounce.txt"));
            if (currentLevel ==2) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_powerup_paddle.txt"));
            if (currentLevel ==3) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_powerup_twoball.txt"));
        }
        else if (code == KeyCode.PERIOD){
            setupForTestScene();
            if (currentLevel ==1) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_brick_destroy.txt"));
            if (currentLevel ==2) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_brick_multihit.txt"));
            if (currentLevel ==3) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_brick_indestructible.txt"));


        }
        else if (code == KeyCode.SLASH){
            setupForTestScene();
            if (currentLevel ==1) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_lose_life.txt"));
            if (currentLevel ==2) primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_powerup_speedball.txt"));

            //tests the final win screen
            if (currentLevel ==3) {
                bricksLeft=1;
                primaryStage.setScene(setupForTest(WIDTH, HEIGHT, BACKGROUND, "test_brick_destroy.txt"));
            }
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

    /**
     * A small utility method for tests. This removes all the unecessary bricks.
     */

    private void setupForTestScene() {
        myBricks.clear();
        bricksLeft = 2;
    }

    /**
     * This method is responsible for setting up a test.s
     * @param width The width of the test screen
     * @param height the height of a test screen
     * @param background the background color of a test screen
     * @param testFile the test configuration file
     * @return returns the scene of the test.
     */

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
            mouseX = config.get(7);
        }

        if (config.get(9) > 0) {
            Brick b;
            if (config.get(9) == 3){
                b = new IndestructibleBrick("brick3.gif", WIDTH);
            }
            else if (config.get(9) > 6 && config.get(9) < 9){
                b = new MultihitBrick("brick" + config.get(9) + ".gif", WIDTH);
            }
            else
                b = new Brick("brick" + config.get(9) + ".gif", WIDTH);

            b.setPosition(config.get(10), config.get(11));
            myBricks.add(b);
            root.getChildren().add(b.getMyImageView());

            if (config.get(12) > 0){
                Powerup p;
                if (config.get(12) == 2){
                    p = new FasterBall("powerup_speedball.gif");

                }
                else if (config.get(12) == 3){
                    p = new DoubleBall("powerup_twoball.gif");
                    initSecondBall();
                    root.getChildren().add(secondBall.getMyImageView());
                }
                else
                    p = new BiggerPaddle("powerup_paddle.gif");
                p.setPosition(config.get(10), config.get(11));
                p.getMyImageView().setVisible(false);
                root.getChildren().add(p.getMyImageView());
                p.setBrick(b);
                myPowerups.clear();
                myPowerups.add(p);
            }
        }



        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    /**
     * This method initializes the second ball. This ball is not visible nor a part of the game until the DoubleBall powerup is
     * activated.
     */

    private void initSecondBall() {
        secondBall = new Ball("ballTwo.gif", WIDTH, HEIGHT);
        secondBall.setPosition(1000, 1000);
        secondBall.getMyImageView().setVisible(false);
        myBallArray = new Ball[] {myBall, secondBall};
    }

    /**
     * This method is responsible for checking to see if a test passed or failed.
     * @param testType Tells the method which test is being tested for.
     */

    //check for win is in SetupResetScreen()
    private void checkTest(String testType) {
        if (testType.equals("test_corner_bounce.txt")){
            if (myBall.getXDirection()>0 && myBall.getX() == 100 && myBall.getY() == 100){
                Platform.exit();
                System.out.println("Corner Bounce Test successful!");
            }
        }
        else if (testType.equals("test_brick_destroy.txt") || testType.equals("test_brick_multihit.txt")){
            if (myBricks.size() == 0 && !(bricksLeft==0)){
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
        if (numSteps > 100 && !testType.contains("powerup")){
            if (testType.equals("test_brick_indestructible.txt")){
                if (myBricks.size() ==1){
                    Platform.exit();
                    System.out.println("Indestructible Brick Test successful!");
                }
            }
            else{
                Platform.exit();
                System.out.println("test failed");
            }
        }
        else if (numSteps > 225){
            if (testType.equals("test_powerup_paddle.txt")){
                if (myPaddle.getWidth()==200){
                    Platform.exit();
                    System.out.println("Paddle Powerup Test successful!");
                }
            }
            else if (testType.equals("test_powerup_speedball.txt")){
                if (myBall.getSpeed()==4){
                    Platform.exit();
                    System.out.println("Speed Powerup Test successful!");
                }
            }
            else if (testType.equals("test_powerup_twoball.txt")){
                if (secondBall.getMyImageView().isVisible()){
                    Platform.exit();
                    System.out.println("Doubleball Powerup Test successful!");
                }
            }
            else {
                Platform.exit();
                System.out.println("test failed");
            }
        }
    }

    /** Reads configuration files into List of Integers
     *
     * @param filename - file to read
     * @return List<Integer>
     */
    public List<Integer> readConfigFile(String filename) {
        var input = new Scanner(this.getClass().getClassLoader().getResourceAsStream(filename));
        input.useDelimiter(" |\\n");
        ArrayList<Integer> results = new ArrayList<>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    /** perfunctory main method
     */
    public static void main (String[] args) {
        launch(args);
    }
}
