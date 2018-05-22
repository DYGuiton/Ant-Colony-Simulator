/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class AnimationController extends Thread {

    Panel thePanel;
    int fps = 20;//translates to approx. 24 fps
    StopWatch timer = new StopWatch();
    World theWorld;
    boolean running = true;

    public AnimationController(Panel p, int colonySize, int food) {
        thePanel = p;
        theWorld = new World(colonySize, food);//model
    }

    @Override
    public void run() {
        int stepsToTake = 24;
        timer.start();
        for (;;) {
            if (running) {
                //stepsToTake--;
                step();
            }
            delay();
            //System.out.println("Hellow");
            if (stepsToTake==0){
                timer.stop();
                timer.read();
                stepsToTake--;
            }
        }
    }

    public void paint(Graphics g) {
        theWorld.paint(g);
    }

    public void step() {
        //System.out.println("Running");
        theWorld.step();
        thePanel.repaint();
    }

    private void delay() {
        try {
            sleep(fps);
        } catch (Exception e) {

        }
    }
    
    public void PheromoneViewSwitch(){
        theWorld.pheromoneViewSwitch();
    }
    
    public void setFPS(int nufps){
        fps = nufps;
    }
}
