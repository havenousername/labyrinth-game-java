/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import labyrinth.model.Game;
import resource.ResourceLoader;
import labyrinth.model.LevelCellChar;
import labyrinth.model.LevelCell;

/**
 *
 * @author andreicristea
 */
public final class LabyrinthUI extends JPanel {
    public static final int TILE_SIZE = 64;
    private final Game game;
    private Image dragonImage,  grass, grassHidden, wall, wallHidden, playerImage;
    
    public LabyrinthUI(Game game) throws IOException {
        this.game = game;
        setImages();
    }
    
    public void setImages() throws IOException {
        dragonImage = ResourceLoader.loadImage(game.getCurrentLevel().getGameId().levelSrcs.getDragon());
        grass = ResourceLoader.loadImage(game.getCurrentLevel().getGameId().levelSrcs.getGrass());
        grassHidden = ResourceLoader.loadImage(game.getCurrentLevel().getGameId().levelSrcs.getGrassHidden());
        wall = ResourceLoader.loadImage(game.getCurrentLevel().getGameId().levelSrcs.getWall());
        wallHidden = ResourceLoader.loadImage(game.getCurrentLevel().getGameId().levelSrcs.getWallHidden());
        playerImage = ResourceLoader.loadImage(game.getCurrentLevel().getGameId().levelSrcs.getPlayer());
    }
    
    public boolean refresh() {
        if (!game.isLevelLoaded()) return false;
        Dimension dimension = new Dimension(game.getLevelRows() * TILE_SIZE, game.getLevelCols() * TILE_SIZE);  
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setSize(dimension);
        
        repaint();
        return true;
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        if (!game.isLevelLoaded()) return;
        Graphics2D graphics2d = (Graphics2D)graphics;
        LevelCell[][] levelCells = game.getCurrentLevel().getCameraVisionCells();
        for (var x = 0; x < levelCells.length; x++) {
            for (var y = 0; y < levelCells[x].length; y++ ) {
                Image image = null;
                LevelCellChar level = levelCells[x][y].getLevel();
                boolean isVisibleCell = game.getCurrentLevel().isPlayerVisibleCell(levelCells[x][y]);
                image = switch (level) {
                    case WALL -> isVisibleCell ? wall : wallHidden;
                    case EMPTY -> isVisibleCell ? grass : grassHidden;
                    case EXIT -> isVisibleCell ?  grass : wallHidden;
                    case PLAYER -> playerImage;
                    case ENEMY -> isVisibleCell ? dragonImage : grassHidden;
                    default -> grass;
                };
                if (image == null) continue;
                graphics2d.drawImage(image, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }    
}
