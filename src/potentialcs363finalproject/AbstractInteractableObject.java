/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 *
 * @author Admin
 */
public abstract class AbstractInteractableObject {

    protected int xPos, yPos, xSize, ySize;
    protected Point2D.Double location;

    public AbstractInteractableObject(int x, int y, int xS, int yS) {
        this.xPos = x;
        this.yPos = y;
        this.xSize = xS;
        this.ySize = yS;
        this.location = new Point2D.Double(getX(), getY());
    }
    
    public Point2D.Double getLocation(){
        return location;
    }

    public double getX() {
        double x = (double) (this.xPos + (this.xSize / 2));
        return x;
    }
    
    public int getTrueX(){
        return this.xPos;
    }

    public double getY() {
        double y = (double)(this.yPos + (this.ySize / 2));
        return y;
    }
    
    public int getTrueY(){
        return this.yPos;
    }
    
    public int getXSize(){
        return this.xSize;
    }
    
    public int getYSize(){
        return this.ySize;
    }
    
    abstract public boolean interact();
    
    abstract public void paint(Graphics g);

    abstract public String toString();
    
    abstract public String toMiniString();
    
    
}
