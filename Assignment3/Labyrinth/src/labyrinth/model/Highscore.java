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
public class Highscore {
    private final String difficulty;
    private final int totalLevels;
    private final int lastLevel;
    private final String name;

    public Highscore(String difficulty, int totalLevels, int lastLevel, String name) {
        this.difficulty = difficulty;
        this.totalLevels = totalLevels;
        this.lastLevel = lastLevel;
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getTotalLevels() {
        return totalLevels;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Highscore other = (Highscore) obj;
        if (this.totalLevels != other.totalLevels) {
            return false;
        }
        if (this.lastLevel != other.lastLevel) {
            return false;
        }
        if (!Objects.equals(this.difficulty, other.difficulty)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Highscore{" + "difficulty=" + difficulty + ", totalLevels=" + totalLevels + ", lastLevel=" + lastLevel + ", name=" + name + '}';
    }
}
