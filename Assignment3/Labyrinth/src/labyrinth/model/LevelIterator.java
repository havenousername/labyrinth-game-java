/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

/**
 *
 * @author andreicristea
 * @param <LevelType>
 */
public interface LevelIterator<LevelType> {
   LevelType nextLevel();
   boolean activateNextLevel();
   boolean hasNextLevel();
}
