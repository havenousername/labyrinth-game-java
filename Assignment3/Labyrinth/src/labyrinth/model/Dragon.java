/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreicristea
 */
public class Dragon extends InteractiveActor implements RandomMovable, Enemy {
    private final GameLevel container;
    public Dragon(GameLevel container) {
        super("Dragon", new Position(0,0));
        this.container = container;
    }
    
    public Dragon(Position position, GameLevel container) {
        super("Dragon", position);
        this.container = container;
    }
    
    public Dragon(String name, Position position, GameLevel container) {
        super(name, position);
        this.container = container;
    }
    
    public void initialMove() {
        move(true);
    }
    
    private void move(boolean isInitial) {
        var moved = false;
        while (!moved) {
            var position = isInitial ? randomPosition() : this.position.translate(nextRandom());
            moved = container.move(this, position);
        }
    }
    
    public void act(CyclicBarrier barrier) {
        Thread t1 = new Thread(() -> {
            synchronized(container) {
                while (container.getPlayer().isAlive()) {
                    try {
                        attack(container);
                        TimeUnit.MILLISECONDS.sleep(500);
                        move(false);
//                        System.out.println("container: \n" + container.levelMap());
//                        System.out.println("player alive? " + container.getPlayer().isAlive());
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException ex) {
                        Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        t1.start();
    }
    
    public void attack(GameLevel container) {
        if (container.hasEnemyPlayerVisibleCells()) {
            kill(container.getPlayer());
        }
    }
    
    @Override
    public Position randomPosition() {
        int randomPositionX = ThreadLocalRandom.current().nextInt(0, container.getCols());
        int randomPositionY = ThreadLocalRandom.current().nextInt(0, container.getRows());
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

    @Override
    public void kill(Dieable dieable) {
        if (dieable.isAlive()) {
            dieable.die();
        }
    }
    
    @Override
    public Dragon clone() throws CloneNotSupportedException {
        return new Dragon(name, position, container);
    }

    public GameLevel getContainer() {
        return container;
    }
    
    @Override
    public labyrinth.model.Level getFieldType() {
        return labyrinth.model.Level.ENEMY;
    }
}
