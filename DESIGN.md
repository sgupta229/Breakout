CompSci 307: Game Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/02_game/):

### Design Analysis

**High level design goals**

The design goals of our projects was to make classes easy to read and understand by the user. We wanted
to separate as many things as possible so that each class had one specific purpose rather than being a 
general class that does a lot of things.

**How to add new features**

Adding new features to this project is relatively easy:

* To add a new powerup, a new class can be created as the name of that powerup. This class should extend
the class Powerup. This class can then define the methods relevant to the features of the powerup. This new
class should Override this method:

```java
public void paddleCollision(Paddle myPaddle, Ball ball, Ball secondBall) {
        this.getMyImageView().setVisible(false);
        this.setX(1000);
    }
```

and call super. This method dictates what happens when the paddle collides with the powerup. In the PowerupGenerator
class, the user can then dictate how many of this powerup appears on each level and which brick it appears under.

* To add a new level it is also fairly simple. The user can create a new level configuration text file
in the resource folder. Once the level configuration file has been made, the user can change the MAX_LEVEL
instance variable to however many levels there are now.

**Major design choices**

**Assumptions/Decisions made**

