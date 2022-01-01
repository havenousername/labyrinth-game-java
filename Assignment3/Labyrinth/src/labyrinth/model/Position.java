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
public class Position {
    protected int x;
    protected int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public Position translate(Direction d) {
//        System.out.println("Translate later: x: " +  d.x.bound  + ", y: " + d.y.bound);
        return new Position(x + d.x.bound, y + d.y.bound);
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }
}
