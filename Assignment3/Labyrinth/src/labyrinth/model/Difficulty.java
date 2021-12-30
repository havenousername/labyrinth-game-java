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
public enum Difficulty {
    HARD("HARD"),
    EASY("EASY"),
    MEDIUM("MEDIUM");
    
    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public final String difficulty;
    
    public static int getRepresentation(Difficulty d) {
        return switch (d) {
            case EASY -> 1;
            case MEDIUM -> 2;
            default -> 3;
        };
    }
    
    public static int getRepresentation(String d) {
        return getRepresentation(getDifficulty(d));
    }
    
    public static Difficulty getDifficulty(String s) {
        if (s.equals(Difficulty.EASY.difficulty)) {
            return Difficulty.EASY;
        } else if (s.equals(Difficulty.MEDIUM.difficulty)) {
            return Difficulty.MEDIUM;
        } else {
            return Difficulty.HARD;
        }
    }
            
}
