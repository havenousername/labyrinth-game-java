/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.Objects;

/**
 *
 * @author andreicristea
 */
public class LevelCell {
    private Level level;
    private final Position position;

    public LevelCell(Level level, Position position) {
        this.level = level;
        this.position = position;
    }
    
    public LevelCell(Level level, int x, int y) {
        this.level = level;
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }
   

    public Level getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.level);
        hash = 83 * hash + Objects.hashCode(this.position);
        return hash;
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
        final LevelCell other = (LevelCell) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "LevelCell{" + "level=" + level.level + ", position=" + position.toString() + '}';
    }
    
    
}
