CompSci 307: Game Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/02_game/):

Design Review
=======

### Status

* I think most of the code is easy to understand and readable. I think we did a good job with naming our methods.
All of the methods have meaningful names that should generally make it clear to the user what the method
is accomplishing. I also think all of our classes are of manageable size. The classes are not too long
or cumbersome, and they should be relatively easy for a user to look through and get the gist of what 
they are doing.

```java
public void checkBrickHit(List<Brick> myBricks, Ball myBall, Ball secondBall) {
        if(myBricks.contains(this.getBrick()) == false) {
            if(myBall.getSpeed() != 0 || secondBall.getSpeed() != 0) {
                this.setY_vel(50);
            }
            else {
                this.setY_vel(0);
            }
            this.getMyImageView().setVisible(true);
        }
    }

    public void incrementPos(double elapsedTime) {
        this.setY(this.getY() + this.getY_vel() * elapsedTime);
    }


    public void paddleCollision(Paddle myPaddle, Ball ball, Ball secondBall) {
        this.getMyImageView().setVisible(false);
        this.setX(1000);
    }
```

The code above is taken from the Powerup class. These methods are primarily the main methods in the class. 
These methods are not only short, but I feel they are easy to understand what they do. checkBrickHit checks
to see if a powerup's brick (the brick a powerup is under) was hit, incrementPos moves the powerup across the screen, 
and paddleCollision checks to see if the paddle and the powerup collided. 

```java
public class MultihitBrick extends Brick {

    public MultihitBrick(String filename, double sceneWidth){
        super(filename, sceneWidth);
    }

    @Override
    public void handleCollision() {
        if (typeNumber == 6)
            super.handleCollision();
        else downgrade();
    }

    private void downgrade() {
        typeNumber--;
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("brick" + getBrickType() + ".gif"));
        getMyImageView().setImage(image);
    }
}
```

Above is some of the code that Will wrote. Even without having written this code, I feel like looking at this
class and understanding its purpose/how it works is easy. MultihitBrick implies that this class
dictates the property of a brick that takes multiple hits to break. There is a handleCollision method, 
which determines what happens when the ball hits this brick. Then there is a downgrade method, which "downgrades"
a brick's level after being hit.

* I feel like our code is very flexible. When adding new features, it was very easy to simply add a new class
and implement the new feature. For example, for adding powerups we simply added a new class that extends the basic
powerup class. When implementing bricks we just created a new class that extended brick. I feel like we put good
thought into what the inherent properties of certain objects are (such as bricks or powerups), so implementing new ones
was not difficult. Similarly, adding a high score counter or a feature like that was also not hard. I simply created 
a new class and then instantiated it in our main class BreakerGame. One feature that was difficult to implement was 
DoubleBall. Normally, adding powerups was easy. Since this class added a new object to the root though, we had to change
parts of BreakerGame. This was not as flexible as we would've liked, and there is probably a better way to add other
objects to the root than having to go back and change stuff in the main method. 

* Dependencies are pretty clear. We marked methods private when they were only used within the class that they were
made in. When a method is marked public, it is pretty clear as to why it is. For example, for the Ball methods:

```java
public void paddleCollision(Paddle myPaddle) {
        double ball_location = this.getX() + this.getWidth() / 2;
        double paddle_increment = myPaddle.getWidth() / 10;
        if (ball_location <= myPaddle.getX() + 1 * paddle_increment) {
            x_dir = -100;
        }
        else if (ball_location >= myPaddle.getX() + 10 * paddle_increment) {

            x_dir = 100;
        }
        else if (ball_location <= myPaddle.getX() + 2 * paddle_increment) {
            x_dir = -80;
        }
        else if (ball_location >= myPaddle.getX() + 9 * paddle_increment) {
            x_dir = 80;
        }
        else if (ball_location <= myPaddle.getX() + 3 * paddle_increment) {
            x_dir = -60;
        }
        else if (ball_location >= myPaddle.getX() + 8 * paddle_increment) {
            x_dir = 60;
        }
        else if (ball_location <= myPaddle.getX() + 4 * paddle_increment) {
            x_dir = -40;
        }
        else if (ball_location >= myPaddle.getX() + 7 * paddle_increment) {
            x_dir = 40;
        }
        else if (ball_location <= myPaddle.getX() + 5 * paddle_increment) {
            x_dir = -20;
        }
        else if (ball_location >= myPaddle.getX() + 6 * paddle_increment) {
            x_dir = 20;
        }
        else if (ball_location == myPaddle.getX() + 6 * paddle_increment) {
            x_dir = 0;
        }
        y_dir = -100;
    }

    public void brickCollision(Brick brick) {
        if((this.getX()+ this.getWidth() / 2) <= brick.getMyImageView().getBoundsInParent().getMinX()) {
            this.x_dir *= -1;
        }
        else if ((this.getX()+ this.getWidth() / 2) >= brick.getMyImageView().getBoundsInParent().getMaxX()) {
            this.x_dir *= -1;
        }
        if((this.getY()+ this.getHeight() / 2) >= brick.getMyImageView().getBoundsInParent().getMaxY()) {
            this.y_dir *= -1;
        }
        else if((this.getY()+ this.getHeight() / 2) <= brick.getMyImageView().getBoundsInParent().getMinY()) {
            this.y_dir *= -1;
        }
    }
```

it makes sense that these methods are public, as they need to be checked in the step function to see if the ball collided
with the paddle or a brick. We did not really use static calls, order of method calls, and specific sub class types. 
We tried to utilize polymorophism so that ArrayLists and such were parameratized with Powerup or Brick so we could put
whatever sub class we wanted to. The same applies for reference variables. 

### Overall Design

* To add a new level it's fairly simple. Simply make a new configuration file of where the bricks should go in the resource
folder (follow the convention in the other level configuration files). 

```java
if (bricksLeft == 0){
            if (currentLevel == 3)
                primaryStage.setScene(setupResetScreen(WIDTH, HEIGHT, BACKGROUND));
            else {
                animation.stop();
                currentLevel++;
                primaryStage.setScene(setupGame(WIDTH, HEIGHT, BACKGROUND));
            }
        }
```

This if statement is in the step method. Change currentLevel == 3 to currentLevel == 4. Then the game will go to level
4 and then quit.

* I am fairly happy with the design of this program. I feel like we made almost as many classes as we should have. Each class
is responsible for a certain type of object or feature. The thought process we had was whatever can be made into a separate
class should be. The Ball is its own object, a Brick is its own object, a Paddle is it's own object, etc. So we made
all of these their own classes. Furthermore, specific powerups are their own objects, specific bricks are their own objects, etc.
so these were also their own classes. In the BreakerGame class we instantiate these objects and then use their methods to 
have them interact with each other. We also made utility classes like HighScoreUpdater and PowerupGenerator. These
are classes that serve specific functions in the game such as maintaining the high score and setting up where 
the powerups are. While generating the powerups could have gone in the Powerup class, we tried to make classes as digestable as
possible while still being useful. The BreakerGame class is what combines all these classes.

* Our code is designed the way it is to make it as modular and readable as possible. We hope that if someone new were
to make additions to this game they would be able to understand how it works and be able to implement new features without
refactoring the code. Since each independent object does its own thing, another person should be able to add classes
and see where to instantiate them in the BreakerClass (main class) since classes shouldn't be too long or too hard to understand.
One issue we wrestled with when coming up with the final design was the BreakerGame class. Since the BreakerGame class
ties everything together, we wanted to make sure the BreakerGame class was refactored as best as possible. We did not 
want to have extra code in that class since it is already long. We tried to refactor as best as possible to take everything
out and make it its own class/subclass/method elsewhere.

### Your Design

* One abstraction I made was the Powerup class. I thought it was good for design.

```java
public abstract class Powerup extends Sprite {

    private double y_vel = 0;
    private Brick myBrick;
    private String powerType;

    public Powerup(String filename) {
        super(filename);
        getMyImageView().setFitWidth(20);
        getMyImageView().setFitHeight(20);
        this.setPowerType(filename);
    }

    public void setY_vel(double setYVel) {
        this.y_vel = setYVel;
    }

    public double getY_vel() {
        return this.y_vel;
    }

    public void setBrick(Brick newBrick) {
        this.myBrick = newBrick;
    }

    public Brick getBrick() {
        return this.myBrick;
    }

    public String getPowerType() {
        return this.powerType;
    }
    
    public void setPowerType(String typePower) {
        this.powerType = typePower;
    }

    public void checkBrickHit(List<Brick> myBricks, Ball myBall, Ball secondBall) {
        if(myBricks.contains(this.getBrick()) == false) {
            if(myBall.getSpeed() != 0 || secondBall.getSpeed() != 0) {
                this.setY_vel(50);
            }
            else {
                this.setY_vel(0);
            }
            this.getMyImageView().setVisible(true);
        }
    }

    public void incrementPos(double elapsedTime) {
        this.setY(this.getY() + this.getY_vel() * elapsedTime);
    }


    public void paddleCollision(Paddle myPaddle, Ball ball, Ball secondBall) {
        this.getMyImageView().setVisible(false);
        this.setX(1000);
    }
}
```

This class is abstract, so it can't be instantiated, but it does a good job of defining all the characteristics of a
poweurp. Moreover, the paddleCollision class just does what all powerups should do while colliding with a paddle. The
powerup should disappear. All the classes that extend powerup override this class and add more functionality. In the BreakerGame
class we make an ArrayList<Powerup> that holds all the powerups. Using polymorphism we can put whatever powerups we want
in that ArrayList to keep track of which powerups are still in the game. The three subclasses are DoubleBall, FasterBall, and 
BiggerPaddle. These all extend Powerup and override the paddleCollision class (while calling super to still get its 
functionality), to achieve the desired powerup effect. This was very easy for design purposes, as changing up the 
powerups/adding new ones was easy. In conjunction with the PowerupGenerator, adding a new powerup and then generating it
within levels is not difficult.

* The Design Checklist:

    * Communication
        * I feel like we do not need to fix most of the subparts of communication. We were particular about naming
        our methods, variables, and classes so that the user would have a good understanding of what they do. If not, the
        user should be able to skim through the method and class and get an understanding of what it does. We tried to 
        declare variables where necessary and name them so the user knows their purpose. We do not have any warnings
        from the Java compiler as well.
    * Modularity
        * I feel that we do not need to fix much of the modularity of our code either. We only had private instance variables. 
        We did not have any static methods or variables either. We made sure classes are responsible for all of their data.
        The main class does not store any particular values for certain objects such as paddle position, powerup characterisitc, etc.
        One thing we could've possibly made better is keeping track of bricks. This could've possibly been a characteristic of the Brick
        class. Currently the main class keeps track of which bricks are still in the game. The same for powerups. This could possible
        be abstracted and have separate classes that are keeping track of which bricks have been hit and which powerups have been taken rather
        than having ArrayLists in the main class as global variables. Lastly, for the hierarchies I created, I ensured
        that the superclass only had methods that were relevant to ALL subclasses. If I had a method that was only relevant
        to some classes, I tried to make the method more general so it applied to all. 
    * Flexibility
        * I feel like this aspect of our design needs the most improvement. We do not have duplicate code, but all of our classes do not only 
        have one purpose. The BreakerGame method handles a lot. In particular, the BreakerGame class handles all the testing cases, 
        which makes it a lot longer and difficult to read through. Moreover, there were some more things we could have probably 
        separated into their own classes, such as a class that sets up the splash screen, end scene, and levels. If we had more time, 
        I'd definitely want to work on making the BreakerGame class smaller. This was a class that should've been discussed in a lot more detail,
        because once we got too far in the game it became difficult to refactor. We tried separating out the testing methods and make
        a tester class but we ran into too many bugs with not enough time.
        
* One feature that I implemented was the high score feature [HighScoreUpdater Commit](https://coursework.cs.duke.edu/compsci307_2019spring/game_team17/commit/58ebac17ff332c93174391a3b93d48e3542d1eaf)

This commit represents changes I made to the main class and when I finished implementing the new class to keep track of high scores. Since this
method is fairly short I implemented it in one commit. I made a few bug fixes in future commits. When designing this feature, I knew
that I wanted to make it a separate class. I think the design is fine, as it functions independent of everything else and is easy to update 
without touching other code. This code does not have any dependecies or assumptions. I simply instantiate this class in the BreakerGame
class and call methods on the object in the setupGame and resetGame methods to update the highscore and then store the 
high score. It is simple to add and remove this feature from the game. 


### Alternate Designs

One design decision that was made was to put the testing methods inside the main class. All of the testing functionality of
this assignment is in the main class. Another design decision was to include the Sprite parent class. This Sprite parent
class was an important decision made in the beginning because all objects that are ImageViews extend this class. An alternate design
that was considered was to put the testing methods in their own class/classes. This is clearly preferred, as one of the main
reason that the BreakerGame class is so long is because the testing methods are in the class. There aren't really any pros
to having them in the BreakerGame class other than the fact that it is easier to implement. One problem we encountered
when trying to make the tests into their own class was the need for a lot of the instance variables in the BreakerGame class.
Other than making a lot of getter and setter methods, we weren't sure how to refactor the code. This is the main reason
we left the methods in the main class. As for the Sprite class, I do not think there was a need for this class. The Ball, Paddle,
Brick, and Powerup classes could've all just had ImageView properties. I do not think this parent class adds much utility to
the code.