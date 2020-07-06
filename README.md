game
====

### Download Demo Video

![Second Level Demo](BreakoutGameDemo.mov?raw=true)

This project implements the game of Breakout.

Name: Will Francis, Sahil Gupta

### Timeline

Start Date: 1/28

Finish Date: 2/12

Hours Spent: 20

### Resources Used

Code from: https://stackoverflow.com/questions/18597939/handle-mouse-event-anywhere-with-javafx
Loose inspiration of project structure (especially the sprite class) from here: https://carlfx.wordpress.com/2012/04/09/javafx-2-gametutorial-part-2/


### Running the Program

Main class: BreakerGame

Data files needed: The files in the resources folder

Key/Mouse inputs: Drag the mouse to control the paddle, click buttons on splash screens to start/restart game

Cheat keys:
F - Speed up ball
S - Slow down ball
Space - Freeze/Unfreeze the game
M - Reset game to splash screen
L - Add a life
R - Reset ball and paddle
, - Testing key 
    When level = 1 - Tests corner bounce
    When level = 2 - Tests BiggerPaddle powerup
    When level = 3 - Tests DoubleBall powerup
. - Testing key
    When level = 1 - Tests brick destroy
    When level = 2 - Tests multi-hit brick destroy
    When level = 3 - Tests indestructible brick
/ - Testing key
    When level = 1 - Tests losing a life
    When level = 2 - Tests SpeedBall powerup
    When level = 3 - Tests win condition

Known Bugs: 
*Some buggy behavior with Ball bouncing, especially re: indestructible bricks. Occasionally the ball will reverse course when it should reflect off the brick normally. This behavior will sometimes result in infinite loops (esp in level 3)
*Ball will occasionally get stuck crawling along a wall. No fix except to let the ball die.

### Notes
Level 2 becomes more difficult with the introduction of multi-hit bricks and more spacing, which requires better aiming
Level 3 becomes more difficult with the introduction of a layer of indestructible bricks, which makes the upper portion of bricks much trickier to hit

Our three types of Bricks (besides the generic kind) are: Powerup Bricks, Indestructible Bricks, Multi-hit bricks
