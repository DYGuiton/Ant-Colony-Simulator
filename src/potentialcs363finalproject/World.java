/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class World {

    int bounds[] = new int[2];
    FoodList theFoodList;
    Colony theColony;
    int GridWorld[][];
    int stepCounter = 0;
    boolean pheromoneWorld = false;
    int highestValue = 0;

    //should be like an array list of food
    //should be a list of colonies
    //each colony should hold
    //a queen ant that works very specifically
    //an array list of gather ants
    //an array list of worker ants
    public World() {

    }

    public World(int colonySize, int foodcount) {

        bounds[0] = 500;
        bounds[1] = 250;
        theFoodList = new FoodList();
        //theFoodList.add(new Food(77, 50, 10, 10, 2756));
        for (int i = 0; i < foodcount; i++) {
            //(xPos, yPos, xSize, ySize, TotalFood)
            theFoodList.add(new Food(genRandomXPos(), genRandomYPos(), 10, 10, genRandomFoodAmount()));
        }
        theColony = new Colony(new Point2D.Double(250.0, 125.0),
                new Point2D.Double(20.0, 20.0), new Point2D.Double(bounds[0], bounds[1]), colonySize);

        GridWorld = new int[bounds[0]][bounds[1]];
        for (AbstractInteractableObject nextInteractableObject : theFoodList) {
            Food nextFood = (Food) nextInteractableObject;
            int xPos = nextFood.getTrueX();
            int yPos = nextFood.getTrueY();
            int xSize = nextFood.getXSize();
            int ySize = nextFood.getYSize();
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    GridWorld[xPos + i][yPos + j] = 1;
                }
            }

//div100%1
//use the thousandth place for pheromone count
//use the ones place for food
        }

        int xPos = (int) (theColony.location.getX() - (theColony.dimensions.getX() / 2));
        int yPos = (int) (theColony.location.getY() - (theColony.dimensions.getY() / 2));
        int xSize = (int) theColony.dimensions.getX();
        int ySize = (int) theColony.dimensions.getY();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                GridWorld[xPos + i][yPos + j] = 2;
            }
        }

    }

    public void paint(Graphics g) {

        if (pheromoneWorld) {
            for (int i = 0; i < bounds[0]; i++) {
                for (int j = 0; j < bounds[1]; j++) {
                    
                        g.setColor(Color.black);
                        g.drawRect(i,j,1,1);//draw black everywhere
                    
                    if (GridWorld[i][j] > 5) {//if we're looking at a pheremone location
                        if(GridWorld[i][j] > highestValue){
                            highestValue = GridWorld[i][j];
                            //System.out.println("highestValue = " + highestValue);
                        }
                        double percentage = GridWorld[i][j]/Math.pow(10, 3.5);
                        int alpha = (int)(255 * (percentage/100.0f));
                        if(alpha>255){
                            alpha = 255;
                            //System.out.println("We had to do this");
                        }
                        
                        //compute the alpha
                        alpha = 255-alpha;
                        //System.out.println("alpha = " + alpha);
                        g.setColor(new Color(255, 128, 0, alpha));
                        g.drawRect(i,j,1,1);
                    }
                }
            }
        } else {

            g.setColor(Color.black);
            g.drawRect(0, 0, bounds[0], bounds[1]);

            for (AbstractInteractableObject nextFood : theFoodList) {
                nextFood.paint(g);
            }

            g.setColor(Color.magenta);
            theColony.paint(g);
            //g.fillOval(xPos, yPos, 15, 15);
            //g.fillArc(xPos, yPos+20, 15, 15, 15, 330);
        }
    }

    void step() {
        stepCounter++;
        for (AbstractDrone nextAbstractDrone : theColony.theDroneList) {
            Drone nextDrone = (Drone) nextAbstractDrone;
            int xPos = (int) nextDrone.getLocation().getX();
            if (xPos >= bounds[0]) {
                xPos = bounds[0] - 1;
            }
            if (xPos < 0) {
                xPos = 0;
            }

            int yPos = (int) nextDrone.getLocation().getY();
            if (yPos >= bounds[1]) {
                yPos = bounds[1] - 1;
            }
            if (yPos < 0) {
                yPos = 0;
            }

            
            //GATHER FOOD AND RETURN
            if ((GridWorld[xPos][yPos] % 10) == 1) {//if we hit a food location in the grid world
                GatherAndReturn(nextDrone);
            }
            //FOLLOWING FOLLOWING FOLLOWING FOLLOWING
            if (GridWorld[xPos][yPos] > 5) {//if we're not one of the 5 interactable objects or a blank spot(0) 
                FollowScent(nextDrone, xPos, yPos);
                //we must be on a pheromone location (>5)

            }
            if ((GridWorld[xPos][yPos]) % 10 == 2) {//if we're at the colony location
                if (nextDrone.full == true) {
                    nextDrone.beginSearching();
                    theColony.storeFood();
                    //System.out.println("Total Food in Colony = " + theColony.getStoredFood());
                }
            }
            if (nextDrone.returning) {//if we have food and are heading home leave a pheromone trail
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        try {
                            int interactableType = GridWorld[xPos + i][yPos + j] % 10;
                            switch (GridWorld[xPos + i][yPos + j] % 10) {
                                case 1:
                                    //System.out.println("Welp");
                                    GridWorld[xPos + i][yPos + j] += (nextDrone.returnCounter - (10 * (Math.abs(i) + Math.abs(j))));//if we're over a food space keep it marked as food
                                    break;
                                case 2:
                                    GridWorld[xPos + i][yPos + j] = 2;
                                    break;
                                default:
                                    //System.out.println("Hellow");
                                    GridWorld[xPos + i][yPos + j] = (nextDrone.returnCounter - (10 * (Math.abs(i) + Math.abs(j))));//add pheromones to the grid location
                                    break;
                            }
                            nextDrone.decReturnCounter();//decrease the amt of pheromones downstream
                            //System.out.println("Hellow");

                        } catch (Exception e) {
                            //System.out.println("Exception");
                        }
                    }
                }
            }

            if (GridWorld[xPos][yPos] == 0 && nextDrone.following) {
                nextDrone.beginSearching();
            }

            //updateWorld();
        }
        updateWorld();
//        for (AbstractInteractableObject nextFood : theFoodList) {
//            if (nextFood.interact()) {
//                //System.out.println(nextFood.toMiniString());
//            } else {
//                //System.out.println("Couldn't Eat!");
//                //System.out.println(nextFood.toMiniString());
//            }
//        }
        theColony.step();

    }

    public int genRandomXPos() {
        Random dice = new Random();
        int xPos = dice.nextInt(bounds[0] - 15);
        return xPos;
    }

    public int genRandomYPos() {
        Random dice = new Random();
        int yPos = dice.nextInt(bounds[1] - 15);
        return yPos;
    }

    public int genRandomFoodAmount() {
        Random dice = new Random();
        int foodAmount = dice.nextInt(1000 - 500) + 500;
        return foodAmount;
    }

    public boolean compareDronetoFoodXLocation(Drone drone, Food food) {
        if (drone.getLocation().getX() <= (food.getTrueX() + food.getXSize())) {
            if (drone.getLocation().getX() >= food.getTrueX()) {
                return true;
            }
        }
        return false;
    }

    public boolean compareDronetoFoodYLocation(Drone drone, Food food) {
        if (drone.getLocation().getY() <= (food.getTrueY() + food.getYSize())) {
            if (drone.getLocation().getY() >= food.getTrueY()) {
                return true;
            }
        }
        return false;
    }

    public String printGridWorld() {
        String returnMe = "Gridworld = " + "\n" + Arrays.deepToString(GridWorld).replace("], ", "]\n");
        return returnMe;
    }

    public void updateWorld() {

        updatePheromones();

    }

    public void updatePheromones() {
        for (int i = 0; i < bounds[0]; i++) {
            for (int j = 0; j < bounds[1]; j++) {
                if (GridWorld[i][j] > 5) {
                    int interactableType = GridWorld[i][j] % 10;
                    GridWorld[i][j] -= 50;
                    if (GridWorld[i][j] < 50) {
                        GridWorld[i][j] = interactableType;
                    }
                }
            }
        }
    }

    private void GatherAndReturn(Drone nextDrone) {
        if (nextDrone.full == false) {//if we don't already have food
            for (int i = 0; i < theFoodList.size(); i++) {//find the food object in the food list
                if (((Food) theFoodList.get(i)).getAmountEaten() != ((Food) theFoodList.get(i)).getTotalFood()) {
                    if (compareDronetoFoodXLocation(nextDrone, (Food) theFoodList.get(i))
                            && compareDronetoFoodYLocation(nextDrone, (Food) theFoodList.get(i))) {

                        if (theFoodList.get(i).interact()) {//if we can decrement the amt of food in the food object
                            nextDrone.gather();//sets full to to true and gathering to false
                            nextDrone.returnToColony();

                            if (((Food) theFoodList.get(i)).isEmpty()) {
                                System.out.println("We tried to erase");
                                int xEnd = (int) (theFoodList.get(i).getTrueX() + theFoodList.get(i).getXSize());
                                int yEnd = (int) (theFoodList.get(i).getTrueY() + theFoodList.get(i).getYSize());
                                for (int j = (int) theFoodList.get(i).getTrueX(); j < xEnd; j++) {
                                    for (int k = (int) theFoodList.get(i).getTrueY(); k < yEnd; k++) {
                                        //System.out.println("Hellow");
                                        GridWorld[j][k] = 0;
                                    }
                                }
                                theFoodList.remove(i);
                            }
//                                    for (int j = -5; j <= 5; j++) {
//                                        for (int k = -5; k <= 5; k++) {
//                                            try {
//                                                GridWorld[xPos + j][yPos + k] += (500 - 10 * Math.abs(j - k));
//                                            } catch (Exception e) {
//
//                                            }
//                                        }
//                                    }
                        }

                    }
                }
            }
        }

    }

    private void FollowScent(Drone nextDrone, int xPos, int yPos) {
        if (nextDrone.full == false) {//if we don't have food
            if (nextDrone.returning == false) {//if we're not already trying to go home
                int nuXPos = xPos;
                int nuYPos = yPos;
                for (int i = -2; i < 2; i++) {
                    for (int j = -2; j < 2; j++) {//check numbers w/in a 5x5 square
                        try {
                            if ((GridWorld[xPos + i][yPos + j] % 10) == 1) {//if one of the squares in the vicinity is a food object
                                //go to it
                                nuXPos = xPos + i;
                                nuYPos = yPos + j;
                                //GatherAndReturn(nextDrone);
                                break;

                            } else if (GridWorld[xPos + i][yPos + j] > GridWorld[xPos][yPos]) {//if one of the squares has a larger value
                                //set nu position to the higher value position
                                nuXPos = xPos + i;
                                nuYPos = yPos + j;
                                //System.out.println("We found a higher position");
                            }
                        } catch (Exception e) {
                            //System.out.println("We had an exception");
                        }
                    }
                }
                if ((nuXPos != xPos) && (nuYPos != yPos)) {
                    //System.out.println("xPos , nuXPos : " + xPos + ", " + nuXPos);
                    //System.out.println("yPos , nuYPos : " + yPos + ", " + nuYPos);
                    nextDrone.follow(nuXPos, nuYPos);

                } else {
                    //System.out.println("Finding our own way" + nextDrone.toString());
                    nextDrone.divert();
                }

            }
        }
    }
    
    public void pheromoneViewSwitch(){
        pheromoneWorld = !pheromoneWorld;
    }
}
