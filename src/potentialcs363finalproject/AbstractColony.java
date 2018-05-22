/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 *
 * @author Admin
 */
public abstract class AbstractColony {

    protected Point2D.Double location;
    protected Point2D.Double dimensions;
    protected Point2D.Double WorldBounds;
    protected int food;
    protected int currentPopulation;
    protected final static int MAX_SIZE = 100000;
    
    protected DroneList theDroneList;
    
    //protected DroneList theDroneList;

    public AbstractColony() {
        theDroneList = new DroneList();
    }
    
    abstract public void addDrone(AbstractDrone nuDrone);
    
    abstract public void birthDrone();
    
    abstract public void paint(Graphics g);
    
    abstract public void step();
    
}
