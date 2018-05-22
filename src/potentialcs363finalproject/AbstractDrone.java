/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 *
 * @author Admin
 */
public abstract class AbstractDrone {

    protected Point2D.Double size;
    protected Point2D.Double location;
    protected Point2D.Double homeLocation;
    protected Point2D.Double WorldBounds;
    protected Color color;
    
    public AbstractDrone(){
        
    }
    
    abstract public boolean step(int food);
    
    abstract public void paint(Graphics g);
    
}
