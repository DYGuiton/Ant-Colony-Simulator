/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potentialcs363finalproject;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DroneList extends ArrayList<AbstractDrone>{
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        for (AbstractDrone nextDrone : this){
            sb.append("\t").append(nextDrone).append("\n");
    }
        return sb.toString();
    }
    
}
