/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

/**
 *
 * @author Admin
 */
class StopWatch {

    long startTime;
    long endTime;
    long second;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime() - startTime;
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
    }

    public void read() {
        double seconds = (double) endTime / 1000000000.0;
        System.out.println(seconds + " Seconds");
    }

    public double getTime() {
        double time = (double) endTime / 1000000000.0;
        return time;
    }

}
