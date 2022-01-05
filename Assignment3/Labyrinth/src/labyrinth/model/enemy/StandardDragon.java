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
import labyrinth.model.Dieable;
import labyrinth.model.Direction;
import labyrinth.model.DirectionBound;
import labyrinth.model.GameLevel;
import labyrinth.model.InteractiveActor;
import labyrinth.model.PopulatedLevel;
import labyrinth.model.Position;
import labyrinth.model.RandomMovable;

/**
 *
 * @author andreicristea
 */
public class StandardDragon extends InteractiveActor implements AttackingActiveEnemy, RandomMovable<Void> {
    private final GameLevel container;
    private Direction direction;
    private Thread action;
    public StandardDragon(GameLevel container) {
        super("Dragon", new Position(0,0));
        this.container = container;
    }
    
    public StandardDragon(Position position, GameLevel container) {
        super("Dragon", position);
        this.container = container;
    }
    
    public StandardDragon(String name, Position position, GameLevel container) {
        super(name, position);
        this.container = container;
    }
    
    @Override
    public void initialMove() {
        move(true);
    }
    
    private void move(boolean isInitial) {
        var moved = false;
        while (!moved) {
            if (direction == null) {
               nextRandom();
            } 
            
            var position = isInitial ? randomPosition() 
                    : this.position.translate(direction);
            if (!isInitial) {
               var isPositionWall = 
                       container.getCell(position.getX(), position.getY())
                               .getLevel().level == labyrinth.model.LevelCellChar.WALL.level;
               var isPositionExit = container.getCell(position.getX(), 
                       position.getY()).getLevel().level == 
                       labyrinth.model.LevelCellChar.EXIT.level;
               if (isPositionExit || isPositionWall) {
                   direction = null;
                   moved = false;
               }
            }
            moved = container.move(this, position);
        }
    }
    
    @Override
    public void act(CyclicBarrier barrier) {
        action = new Thread(() -> {
            synchronized(container) {
                while (container.getPlayer().isAlive() && !container.isLevelEnded()) {
                    try {
                        attack(container);
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
    public void attack(PopulatedLevel level) {
        if (level.hasEnemyPlayerVisibleCells()) {
            kill(level.getPlayer());
        }
    }
    
    @Override
    public Position randomPosition() {
        int randomPositionX = ThreadLocalRandom.current().nextInt(0, container.getCols());
        int randomPositionY = ThreadLocalRandom.current().nextInt(0, container.getRows());
        return new Position(randomPositionX, randomPositionY);
    }
    
    @Override
    public Void nextRandom() {
        Random random = new Random();
        int randomDirOne = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
        int randomDirTwo = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;

        boolean correntDirection = false;

        while (!correntDirection) {
            try {
                direction = Direction.getDirection(randomDirOne, randomDirTwo);
                correntDirection = true;
            } catch (IllegalArgumentException ex) {
                randomDirOne = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
                randomDirTwo = random.nextInt(DirectionBound.UPPER_BOUND.bound - DirectionBound.LOWER_BOUND.bound + 1) + DirectionBound.LOWER_BOUND.bound;
            }
        }
        return null;
    }

    @Override
    public void kill(Dieable dieable) {
        if (dieable.isAlive()) {
            dieable.die();
        }
    }
    
    @Override
    public StandardDragon clone() throws CloneNotSupportedException {
        return new StandardDragon(name, position, container);
    }

    public GameLevel getContainer() {
        return container;
    }
    
    @Override
    public labyrinth.model.LevelCellChar getFieldType() {
        return labyrinth.model.LevelCellChar.ENEMY;
    }

    @Override
    public PopulatedLevel getLevel() {
        return this.container;
    }

    @Override
    public void stopAct() {
        try {
            action.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(StandardDragon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
