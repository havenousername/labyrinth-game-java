/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model.enemy;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import labyrinth.model.Direction;
import labyrinth.model.DirectionBound;
import labyrinth.model.Position;
import labyrinth.model.RandomMovable;

/**
 *
 * @author andreicristea
 */
public class RandomMovingDragon extends ActiveEnemyDecorator implements RandomMovable<Direction> {
    private Thread action;
    public RandomMovingDragon(AttackingActiveEnemy enemy) {
        super(enemy);
    }
    
    @Override
    public void initialMove() {
        move(true);
    }
    
    private void move(boolean isInitial) {
        var moved = false;
        while (!moved) {
            var position = isInitial ? randomPosition() : this.getPosition().translate(nextRandom());
            moved = getLevel().move(this, position);
        }
    }
    
    private void execution(CyclicBarrier barrier) {
        action = new Thread(() -> {
            synchronized (getLevel()) {
                while (getLevel().getPlayer().isAlive() && !getLevel().isLevelEnded()) {
                    try {
                        attack(getLevel());
                        TimeUnit.MILLISECONDS.sleep(500);
                        move(false);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException ex) {
                        Logger.getLogger(StandardDragon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        action.start();
    }
    
    @Override
    public void stopAct() {
        try {
            this.action.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RandomMovingDragon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void act(CyclicBarrier barrier) {
        execution(barrier);
    }

    @Override
    public Position randomPosition() {
        int randomPositionX = ThreadLocalRandom.current().nextInt(0, getLevel().getCols());
        int randomPositionY = ThreadLocalRandom.current().nextInt(0, getLevel().getRows());
        return new Position(randomPositionX, randomPositionY);
    }
    
    @Override
    public Direction nextRandom() {
        Random random = new Random();
        int randomDirOne = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
        int randomDirTwo = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
        
        boolean correntDirection = false;
        Direction direction;
        
        while (!correntDirection) {
            try {
                direction = Direction.getDirection(randomDirOne, randomDirTwo);
                return direction;
            } catch (IllegalArgumentException ex) {
                randomDirOne = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
                randomDirTwo = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
            }
        }
        
        return Direction.DOWN;
    }
}
