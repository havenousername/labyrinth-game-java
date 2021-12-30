/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author andreicristea
 */
public class Player extends InteractiveActor implements Dieable, Cloneable {
    protected AtomicBoolean alive;
    public Player(String name, Position position) {
        super(name, position);
        alive = new AtomicBoolean();
        alive.set(true);
    }
    
    public Player(String name) {
        super(name, new Position(0, 0));
        alive = new AtomicBoolean();
        alive.set(true);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isAlive() {
        return alive.get();
    }

    @Override
    public void die() {
        this.alive.set(false);
    }
    
    @Override
    public Player clone() throws CloneNotSupportedException {
        return new Player(name, position);
    }

    @Override
    public Level getFieldType() {
        return Level.PLAYER;
    }
}
