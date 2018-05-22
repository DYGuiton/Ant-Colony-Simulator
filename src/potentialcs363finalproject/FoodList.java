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
public class FoodList extends ArrayList<AbstractInteractableObject> {

    private static final boolean DEBUG = true;

    public FoodList() {
        super();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("FoodSourceList: ");

        for (AbstractInteractableObject nextS : this) {
            sb.append("\t" + nextS + " \n");
        }
        return sb.toString();
    }

}
