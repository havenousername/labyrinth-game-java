/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import labyrinth.model.enemy.Enemy;

/**
 *
 * @author andreicristea
 */
public interface PopulatedLevel {
    public Player getPlayer();
    public Enemy getEnemy();
    public boolean isPlayerBetweenPosition(int i, int j, int width);
    public boolean isLevelEnded();
    public boolean hasEnemyPlayerVisibleCells();
    public boolean move(MovableActor a, Position position);
    public int getCols();
    public int getRows();
}
