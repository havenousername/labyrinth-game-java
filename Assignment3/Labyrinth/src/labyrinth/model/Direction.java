/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.logging.Logger;

/**
 *
 * @author andreicristea
 */
public enum Direction {
    DOWN(DirectionBound.CURRENT_BOUND, DirectionBound.UPPER_BOUND), 
    LEFT(DirectionBound.LOWER_BOUND, DirectionBound.CURRENT_BOUND),
    UP(DirectionBound.CURRENT_BOUND, DirectionBound.LOWER_BOUND), 
    RIGHT(DirectionBound.UPPER_BOUND, DirectionBound.CURRENT_BOUND);
    
    Direction(final DirectionBound x, final DirectionBound y) {
        this.x = x;
        this.y = y;
    }
    
    public final DirectionBound x, y;
    
    public static Direction getDirection(int x, int y) throws IllegalArgumentException {
        if (x == DirectionBound.CURRENT_BOUND.bound) {
            if (y == DirectionBound.UPPER_BOUND.bound) {
                return Direction.DOWN;
            } else if (y == DirectionBound.LOWER_BOUND.bound) {
                return Direction.UP;
            } else {
                throw new IllegalArgumentException("Such direction does not exist");
            }
        } else if (x == DirectionBound.UPPER_BOUND.bound && y == DirectionBound.CURRENT_BOUND.bound) {
           return Direction.RIGHT;
        } else if (y == DirectionBound.LOWER_BOUND.bound && y == DirectionBound.CURRENT_BOUND.bound) {
            return Direction.LEFT;
        } else {
            throw new IllegalArgumentException("Such direction does not exist");
        }
    }
}
