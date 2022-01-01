/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model.enemy;

import java.util.concurrent.CyclicBarrier;
import labyrinth.model.MovableActor;
import labyrinth.model.PopulatedLevel;

/**
 *
 * @author andreicristea
 */
public interface AttackingActiveEnemy extends MovableActor, Enemy  {
    public void attack(PopulatedLevel level);
    public void act(CyclicBarrier barrier);
    public void initialMove();
    public PopulatedLevel getLevel();
}
