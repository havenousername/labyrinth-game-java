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
public abstract class InteractiveActor implements Movable {
    protected String name;
    protected Position position;
    
    public InteractiveActor(String name, Position position) {
        this.name = name;
        this.position = position;
    }
    
    public abstract Level getFieldType();
   

    @Override
    public void move(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public void move(Direction direction) {
        this.position = this.position.translate(direction);
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    public String getName() {
        return name;
    }
}
