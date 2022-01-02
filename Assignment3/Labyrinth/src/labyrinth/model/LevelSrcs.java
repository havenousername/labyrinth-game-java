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
public class LevelSrcs {
    private final String dragon;
    private final String grassHidden;
    private final String grass;
    private final String wall;
    private final String wallHidden;
    private final String player;

    public LevelSrcs(String dragon, String grass, String grassHidden, String wall, String wallHidden, String player) {
        this.dragon = dragon;
        this.grassHidden = grassHidden;
        this.grass = grass;
        this.wall = wall;
        this.wallHidden = wallHidden;
        this.player = player;
    }

    public String getDragon() {
        return dragon;
    }

    public String getGrassHidden() {
        return grassHidden;
    }

    public String getGrass() {
        return grass;
    }

    public String getWall() {
        return wall;
    }

    public String getWallHidden() {
        return wallHidden;
    }

    public String getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "LevelSrcs{" + "dragon=" + dragon + ", grassHidden=" + grassHidden + ", grass=" + grass + ", wall=" + wall + ", wallHidden=" + wallHidden + '}';
    }
}
