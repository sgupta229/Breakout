CompSci 307: Game Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/02_game/):

### Design Analysis

**High level design goals**

The design goals of our projects was to make classes easy to read and understand by the user. We wanted
to separate as many things as possible so that each class had one specific purpose rather than being a 
general class that does a lot of things. This is why we have a lot of classes in a few abstract hierarchical structures.

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

* To add a new type of Brick, create the new Brick subclass, then go into BrickGenerator and add an additional if statement in the "for (int num: configList)" loop,
For example: if (num == 6) b = new ExplosiveBrick("brick" + num + ".gif", width);
You will also need to update level config files to contain the number for the new brick (the number in "brickX.gif")

**Major design choices**

Our most important design choice was to make every object in the game a descendent of the Sprite class. This turned out to be very useful, as they all shared
methods such as getX()/getY() and it made collision detection straightforward because we just compared two Sprite objects
against each other.

We ended up handling sprite management within BreakerGame as opposed to making some kind of SpriteManager class. This did contribute
to BreakerGame's general bloat, but essentially it was faster to implement it the way it is now and because we had so much work to do 
on the project as a whole, we didn't want to get bogged down in such a basic part of the program. 
Basically pro: it was very quick to implement, con: it was a little less elegant probably than taking those functions into a new class


**Assumptions/Decisions made**

We decided to hard code the number of bricks that can fit in a row in the Brick constructor (It's 10 bricks). This is less modular
than some other possible ways of determining the size of bricks (we even considered making bricks of random width, but decided against
it due to time constraints)

We also decided to implement Bricks' deaths by setting their X value way offscreen rather than actually deleting them from the scene. 
This is a little cheeky but it's impossible for the ball to ever interact with them when they're offscreen and it's actually probably slightly
more efficient than however one would actually delete them (I'm not sure though because we never actually figured out the other way to do it)