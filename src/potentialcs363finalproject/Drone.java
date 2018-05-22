/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import static java.awt.Color.*;
import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 *
 * @author Admin
 */
import java.util.Random;

public class Drone extends AbstractDrone {

    protected Point2D.Double direction;
    protected boolean stop;
    protected boolean full;
    protected boolean gathering;
    protected boolean following;
    protected boolean returning;
    protected boolean delivering;
    protected boolean retreating = false;
    protected boolean dead = false;
    protected int retreatCounter;
    protected int returnCounter;
    protected int hunger = 0;
    public int nuX, nuY;

    public Drone() {
        super();
    }

    public Drone(Point2D.Double s, Point2D.Double l, Point2D.Double h, Point2D.Double wb) {
        this();
        this.size = s;
        this.location = l;
        this.homeLocation = h;
        this.WorldBounds = wb;
        direction = genDirection();
        this.color = black;
    }

    @Override
    public boolean step(int storedFood) {
        if (dead) {
            stop();
        } else if (full == false) {
            if (following == true) {
                followPheromones(nuX, nuY);//we'll change to follow later
            } else {
                search();
            }
        } else if (returning == true) {
            returnHome();
            //stop();
        } else {
            System.out.println("we fucked up");
        }
        return hungerCheck(storedFood);
    }

    @Override
    public void paint(Graphics g) {
        if (!dead) {
            g.setColor(this.color);
            g.fillOval((int) this.location.getX() - ((int) this.size.getX() / 2),
                    (int) this.location.getY() - ((int) this.size.getY() / 2),
                    (int) this.size.getX(), (int) this.size.getY());
        }
    }

    private Point2D.Double genDirection() {
        Point2D.Double returnMe;

        Random dice = new Random();
        int degrees = dice.nextInt(361);
        //System.out.println("degrees = " + degrees);
        double radians = Math.toRadians(degrees);

        returnMe = new Point2D.Double(Math.cos(radians), Math.sin(radians));
        //System.out.println("direction = " + direction);
        return returnMe;
    }

    public void search() {
        double nuX;
        double nuY;
        this.color = black;

        if (retreatCounter == 1) {
            //if we've retreated the minimum number of steps, 
            //stop moving backwards
            //delete the log of total retreaded steps
            //turn to some random direction
            retreating = false;
            retreatCounter = 0;
            this.direction = genDirection();
        }

        if (retreating) {
            //if we hit a wall recently, follow your path backwards
            //log the number of steps we take backwards aswell
            nuX = this.location.getX() - this.direction.getX();
            nuY = this.location.getY() - this.direction.getY();
            retreatCounter++;
        } else {
            //if we haven't hit a wall recently, continue on your path
            nuX = this.location.getX() + this.direction.getX();
            nuY = this.location.getY() + this.direction.getY();
        }
        if ((int) nuX == 0 || (int) nuX == WorldBounds.getX()) {
            //if we hit the WorldBorder(X Value) start retreating
            retreating = true;
            //retreatCounter = 0;
        }
        if ((int) nuY == 0 || (int) nuY == WorldBounds.getY()) {
            //if we hit the WorldBorder(Y value) start retreating
            retreating = true;
            //retreatCounter = 0;
        }

        //after accounting for all factors, update position 
        this.location.setLocation(nuX, nuY);
    }

    private void returnHome() {
        this.color = red;
        setDirectionHome();
        double nuX = this.location.getX() + this.direction.getX();
        double nuY = this.location.getY() - this.direction.getY();

        this.location.setLocation(nuX, nuY);
        //System.out.println(location);
    }

    public void stop() {
        this.color = blue;
    }

    private void setDirectionHome() {
        boolean upper = false;
        boolean right = false;
        double theta = calcTheta() * 100;
        double degrees = 0;
        int quadrant = 0;

        //Figure out angle theta
        //Is Home in Upper Left, Lower Left, Upper Right, or Lower Right?
        //if Upper: angle = 90 - theta
        //If Left: angle + 90
        //If Right: angle 
        //if Lower: angle
        //If Left: angle + 180
        //If Right: angle + 270
        if (homeLocation.getY() <= this.location.getY()) {
            upper = true;
            //System.out.println("Upper was true");
        }
        if (homeLocation.getX() >= this.location.getX()) {
            right = true;
            //System.out.println("Right was true");
        }

        //determining the home locations quadrant relative to the drone location
        if (upper && !right) {
            quadrant = 1;
            //System.out.println("We calculate the correct quad");
        } else if (!upper && !right) {
            quadrant = 2;
        } else if (!upper && right) {
            quadrant = 3;
        } else {
            quadrant = 0;
        }

        switch (quadrant) {
            case 0:
                //System.out.println("theta = " + theta);
                degrees = theta;
                break;
            case 1:
                //System.out.println("Quad 2");
                //System.out.println("theta = " + theta);
                degrees = 180 - theta;
                break;
            case 2:
                //System.out.println("Quad 3");
                //System.out.println("theta = " + theta);
                degrees = 180 + theta;
                break;
            case 3:
                // System.out.println("Quad 4");
                //System.out.println("theta = " + theta);
                degrees = 360 - theta;
                //System.out.println("degrees = " + degrees);
                break;
            default:
                //System.out.println("theta = " + theta);
                degrees = 90 - theta;
                break;
        }

        double radians = Math.toRadians(degrees);
        //System.out.println("degrees = " + degrees);
        direction = new Point2D.Double(Math.cos(radians), Math.sin(radians));
        //System.out.println("direction = " + direction);

    }

    public void beginSearching() {
        //this.color = black;
        returnCounter = 100000;
        full = false;
        returning = false;
        following = false;
        this.direction = genDirection();
        //stop = true;
    }

    public void gather() {
        //this.color = red;
        full = true;
        hunger = 0;
        following = false;
        gathering = false;//should ignor efor now
    }

    public void returnToColony() {
        //this.color = red;
        returning = true;
        returnCounter = 100000;
        following = false;
        //System.out.println("We're gonna return so we set following to false");
        setDirectionHome();
    }

    public void follow(int nuXPos, int nuYPos) {
        //this.color = green;
        following = true;
        this.nuX = nuXPos;
        this.nuY = nuYPos;
    }

    public void divert() {
        //this.color = green;
        returnCounter = 100000;
        full = false;
        returning = false;
        following = false;
        this.direction = genDirection();
        //stop = true;
    }

    public Point2D.Double getLocation() {
        return this.location;
    }

    public double calcTheta() {
        int opp = Math.abs((int) (homeLocation.getY() - this.location.getY()));
        int adj = Math.abs((int) (homeLocation.getX() - this.location.getX()));;

        double theta = Math.atan2(opp, adj);
        //System.out.println("theta = " + theta);
        return theta;

    }

    private void followPheromones(int nuXPos, int nuYPos) {
        this.color = green;
        this.location.setLocation(nuXPos, nuYPos);
    }

    public void doNothing() {
        //System.out.println("We're doing nothing");
    }

    public void decReturnCounter() {
        this.returnCounter -= 100;
    }

    void setNuPos(int nuXPos, int nuYPos) {
        this.nuX = nuXPos;
        this.nuY = nuYPos;
    }

    private boolean hungerCheck(int food) {
        hunger++;
        if (hunger > 10000) {
            if (food == 0) {
                
                //System.out.println("We Died");
                dead = true;
            } else{
                hunger = 0;
                //System.out.println("We took food from the colony");
                return true;//we needed to take food from the colony
            }
        }
        if(hunger>9500){
            this.color = blue;
        }
        return false;//we didn't need to take food
    }

}
