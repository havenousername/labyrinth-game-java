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
public enum Level {
    WALL('#'), EMPTY(' '), EXIT('.'), PLAYER('P'), ENEMY('E');
    Level(char level) {
        this.level = level;
    }
    
    public final char level;
}
