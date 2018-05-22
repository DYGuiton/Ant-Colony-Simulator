/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Admin
 */
public class Colony extends AbstractColony {

    public Colony() {
        super();
    }

    public Colony(Point2D.Double l, Point2D.Double d, Point2D.Double wb, int colonySize) {
        this();
        this.location = l;
        this.dimensions = d;
        this.WorldBounds = wb;
        this.currentPopulation = colonySize;
        genWorkers();
    }

    @Override
    public void addDrone(AbstractDrone nuDrone) {
        theDroneList.add(nuDrone);
    }

    @Override
    public void birthDrone() {
        Point2D.Double size = new Point2D.Double(5, 10);
        Point2D.Double loc = new Point2D.Double(this.location.getX(), this.location.getY());
        Drone nuDrone = new Drone(size, loc, this.location, this.WorldBounds);
        addDrone(nuDrone);
    }

    @Override
    public void step() {
        for (AbstractDrone nextDrone : theDroneList) {
            if (nextDrone.step(food)) {//if the next drone needs food
                if (food > 0) {//and food stored food is greater than 0
                    food--;//subtract food from the colony
                }
            }
            if (((int) nextDrone.location.getX() > (int) WorldBounds.getX()) || ((int) nextDrone.location.getY() > (int) WorldBounds.getY())) {
                System.out.println("Hey I got Out and here is my current location:\n"
                        + "(" + nextDrone.location.getX() + ", " + nextDrone.location.getY());
            }
        }
        if ((food != 0) && ((food % 100) == 0)) {
            birthDrone();
            //System.out.println("Birthed a Drone");
        }
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.black);
        for (AbstractDrone nextDrone : theDroneList) {
            nextDrone.paint(g);
        }

        g.setColor(Color.PINK);
        g.draw3DRect((int) this.location.getX() - ((int) this.dimensions.getX() / 2),
                (int) this.location.getY() - ((int) this.dimensions.getY() / 2),
                (int) this.dimensions.getX(), (int) this.dimensions.getY(), true);
    }

    private void genWorkers() {
        for (int i = 0; i < this.currentPopulation; i++) {
            Point2D.Double size = new Point2D.Double(5, 5);
            Point2D.Double loc = new Point2D.Double(this.location.getX(), this.location.getY());
            Drone nuDrone = new Drone(size, loc, this.location, this.WorldBounds);
            addDrone(nuDrone);
            //System.out.println("i = " + i);
        }
    }

    public void storeFood() {
        this.food++;
    }

    public int getStoredFood() {
        return this.food;
    }

}
