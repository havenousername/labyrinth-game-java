/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model.enemy;

import java.util.concurrent.CyclicBarrier;
import labyrinth.model.Dieable;
import labyrinth.model.Direction;
import labyrinth.model.Level;
import labyrinth.model.PopulatedLevel;
import labyrinth.model.Position;

/**
 *
 * @author andreicristea
 */
public class ActiveEnemyDecorator implements AttackingActiveEnemy {
    private final AttackingActiveEnemy enemy;

    public ActiveEnemyDecorator(AttackingActiveEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void attack(PopulatedLevel level) {
        this.enemy.attack(level);
    }

    @Override
    public void act(CyclicBarrier barrier) {
        this.enemy.act(barrier);
    }

    @Override
    public void kill(Dieable dieable) {
        this.enemy.kill(dieable);
    }

    @Override
    public void move(int x, int y) {
        this.enemy.move(x, y);
    }

    @Override
    public void move(Direction direction) {
        this.enemy.move(direction);
    }

    @Override
    public Position getPosition() {
        return this.enemy.getPosition();
    }

    @Override
    public Level getFieldType() {
        return this.enemy.getFieldType();
    }

    @Override
    public void initialMove() {
        this.enemy.initialMove();
    }

    @Override
    public PopulatedLevel getLevel() {
        return this.enemy.getLevel();
    }
}
