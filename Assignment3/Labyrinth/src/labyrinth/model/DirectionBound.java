/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

/**
 *
 * @author andreicristea
 */
public enum DirectionBound {
    LOWER_BOUND(-1),
    CURRENT_BOUND(0),
    UPPER_BOUND(1);
    
    DirectionBound(int bound) {
        this.bound = bound;
    } 
    
    public final int bound;
}
