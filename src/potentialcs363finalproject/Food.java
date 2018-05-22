/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Admin
 */
public class Food extends AbstractInteractableObject {

    private int totalFood;
    private int amountEaten;

    public Food(int x, int y, int xS, int yS, int tF) {
        super(x, y, xS, yS);
        this.totalFood = tF;
        this.amountEaten = 0;
    }

    public Food(int x, int y, int xS, int yS) {
        super(x, y, xS, yS);
        this.amountEaten = 0;
    }

    @Override
    public void paint(Graphics g) {
        int x = (int) getTrueX();
        int y = (int) getTrueY();
        g.setColor(new Color(0, 150, 200));
        g.fillArc(x, y, xSize, ySize, 0, 360 - getArc());
    }

    public int getArc() {
        double percentageEaten = this.amountEaten / (double) this.totalFood;
        //System.out.println("percentage e = " + percentageEaten);
        int arc = (int) (percentageEaten * 360);
        //System.out.println("arc = " + arc);
        return arc;
    }

    public boolean interact() {
        return takeBite();
    }

    public boolean takeBite() {
        if (this.totalFood > this.amountEaten) {
            this.amountEaten++;
            return true;
        }
        return false;
    }

    //public double getArea() {
    //    
    //}
    public int getAmountEaten() {
        return this.amountEaten;
    }

    public int getTotalFood() {
        return this.totalFood;
    }

    public boolean isEmpty() {
        return this.amountEaten == this.totalFood;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("I am an Interactable Object of type: Food\n");
        sb.append("\tCoordinates: (").append(getX()).append(",").append(getY()).append(")\n");
        sb.append("\txPos = ").append(this.xPos).append("\n");
        sb.append("\tyPos = ").append(this.yPos).append("\n");
        sb.append("\txSize = ").append(this.xSize).append("\n");
        sb.append("\tySize = ").append(this.ySize).append("\n");
        sb.append("\tTotal Food = ").append(this.totalFood).append("\n");
        sb.append("\tAmount Eaten = ").append(this.amountEaten).append("\n");
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append("I am an Interactable Object of type: Food\n");
        sb.append("\tTotal Food = ").append(this.totalFood).append("\n");
        sb.append("\tAmount Eaten = ").append(this.amountEaten).append("\n");
        return sb.toString();
    }

}
