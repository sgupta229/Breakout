CompSci 307: Game Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/02_game/):

Design Review
=======

### Status

```java
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
```


* Step() is decently readable, with a mix both of good helper methods (like updateSprites) and blocks of code that should
have been factored into one or more helper methods (like the life loss and win condition checks--I actually thought
that I did refactor them into a new method checkLossAndWin() but that may have been lost on a merge at some point or I
may have decided against doing it for some reason)

* This code is very flexible with adding new levels (see Overall Design for a description how to do so), as you only have to 
create the new config file and change the MAX_LEVEL constant in BreakerGame. The code is still flexible but somewhat less so with 
adding new bricks as you have to go into BrickGenerator and add an additional if statement in the "for (int num: configList)" loop,
and depending on what the new Brick does you may have to change quite a bit more, potentially even in checkAndHandleCollision()

```java
public void generateBricks(Group root, double width, String lvlConfigFile) {
        var brickList = new ArrayList<Brick>();
        var configList = readConfigFile(lvlConfigFile);
        numBricks = 0;
        var b = new Brick("brick1.gif", width);
        var brickWidth = b.getWidth();
        var brickHeight = b.getHeight();
        double currentX = 0;
        double currentY = brickHeight;
        for (int num: configList){
            if (num != 0){
                if (num == 3){
                    b = new IndestructibleBrick("brick" + num + ".gif", width);
                }
                else if (num > 5 && num < 9) {
                    b = new MultihitBrick("brick" + num + ".gif", width);
                }
                else {
                    b = new Brick("brick" + num + ".gif", width);
                }
                b.setPosition(currentX, currentY);
                root.getChildren().addAll(b.getMyImageView());
                brickList.add(b);
                if (!(b instanceof IndestructibleBrick)) {
                    numBricks++;
                }
            }

            if (currentX + brickWidth >= width){
                currentX = 0;
                currentY += brickHeight;
            }
            else currentX += brickWidth;
        }
        myBricks = brickList;
    }
```


* As far as I know, all dependencies are clear, no back-channel goings on. BreakerGame explicitly calls several of the subclasses
of Sprite out of necessity/efficiency (there's no point in checking collision between bricks so we just use myBall and then compare from there). 

### Overall Design

* To add a new level, create a configuration file in the resources folder with the format lvlXconfig.txt, where X is the number of the level. 
The config file follows the format of a grid (13 rows, 10 columns) of brick type numbers separated by a single whitespace. 
If a number is 0, there is no brick there, if it is any other number then it is a Brick. Specific numbers correspond 
to brick types in this way:

1, 2, 5, 6 - Generic one-hit bricks
3 - Indestructible Bricks
7 - Multi-hit brick (two hits)
8 - Multi-hit brick (three hits)
9 - Power-up brick

Then change the constant MAX_LEVEL in BreakerGame from its default at 3, adding one for each additional level.


* In general, BreakerGame is the main method where all the game's actors interact. BreakerGame gets data about the actors
(all subclasses of the Sprite abstract class) from the classes themselves and uses that data to run the game. BreakerGame
takes user input, updates Sprite positions, checks collisions between the Ball(s) and other Sprites, gets rid of any
bricks that have been hit, and then checks for loss/win conditions. Cheat Keys and Testing is also handled inside 
BreakerGame. We made attempts to refactor testing into its own class, or make some sort of general SceneConstructor, but
every time we tried we broke the game and couldn't figure out why.

* Overall I'd say the project is structured well, with numerous inheritance hierarchies between various subclasses of 
Sprite. The choice to make every object in the game a descendent of the Sprite class was very useful, as they all shared
methods such as getX()/getY() and it made collision detection straightforward because we just compared two Sprite objects
against each other.


### Your Design

*I brought the idea for an overall Sprite class to the team, although it was not wholly my idea (I borrowed from here: https://carlfx.wordpress.com/2012/04/09/javafx-2-gametutorial-part-2/)

One design contribution that was completely my own was the decision to structure bricks with a generic concrete class and
several more specific subclasses. This allowed Bricks of different types to be stored in the same ArrayList (myBricks) 
while also allowing a lot of flexibility in brick functionality.


*Some of the class names are a little confusing, e.g. BiggerPaddle extends Powerup, not Paddle. So those could have been cleaned up.
I'm sure there are some magic values floating around that could have been cleaned up as well. In testing there's a lot of 
boolean work that could have been changed by perhaps different subclasses of a general Test class, but like I said earlier,
every time we tried to refactor testing out of BreakerGame we broke the game and we didn't have time to fully work out those
issues.


*[This commit](https://coursework.cs.duke.edu/compsci307_2019spring/game_team17/commit/e50403df80722b4241ad57762f40f697f5700b51) details 
my work on implementing Brick and Paddle, so it represents new development. At that stage, I used a method,
generateBricks() instead of a separate class, BrickGenerator, which was simply a useful first pass implementation. Also this
was before I had implemented level config files so generateBricks() was almost nothing like what its final method would be.
However, I thought it was important to make sure that Bricks interacted with the game properly first before delving into 
level configuration files.


*As far as dependencies, obviously generateBricks is heavily dependent on the Brick class, but this is quite a normal
type of dependency, nothing too concerning that I can see


### Alternate Designs

*As I've said, we ultimately decided to put all testing methods into BreakerGame, which was not our first choice, but we had difficult refactoring testing out of BreakerGame.
There's nothing incredibly wrong with the way testing is now, but it does contribute to a general bloat with BreakerGame's length.

*Currently collision checks are handled in BreakerGame. Earlier we considered making some sort of SpriteManager class to handle 
collision checks and cleanups of dead Sprites, but essentially it was faster to implement it the way it is now and because
we had so much work to do on the project as a whole, we didn't want to get bogged down in such a basic part of the program 
