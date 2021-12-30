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
import labyrinth.model.Level;
import labyrinth.model.LevelCell;
import labyrinth.model.Position;

/**
 *
 * @author andreicristea
 */
public class LabyrinthUI extends JPanel {
    private Game game;
    private final Image dragonImage,  grass, grassHidden, wall, wallHidden, playerImage;
    private final int tileSize = 64;
    
    public LabyrinthUI(Game game) throws IOException {
        this.game = game;
        System.out.println(game.levelMap());
        dragonImage = ResourceLoader.loadImage("resource/dragon.png");
        grass = ResourceLoader.loadImage("resource/grass.jpg");
        grassHidden = ResourceLoader.loadImage("resource/grass_hidden.jpg");
        wall = ResourceLoader.loadImage("resource/wall.jpg");
        wallHidden = ResourceLoader.loadImage("resource/wall_hidden.jpg");
        playerImage = ResourceLoader.loadImage("resource/player.jpg");
    }
    
    public boolean refresh() {
        if (!game.isLevelLoaded()) return false;
        Dimension dimension = new Dimension(game.getLevelRows() * tileSize, game.getLevelCols() * tileSize);  
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
        
        Position position = game.getPlayerPosition();
        LevelCell[][] levelCells = game.getLevelCells();
        for (var x = 0; x < levelCells.length; x++) {
            for (var y = 0; y < levelCells[x].length; y++ ) {
                Image image = null;
                Level level = levelCells[x][y].getLevel();
//                System.out.println("Visible cells " + game.getCurrentLevel().getPlayerVisibleCells().toString());
                boolean isVisibleCell = game.getCurrentLevel().isPlayerVisibleCell(levelCells[x][y]);
                image = switch (level) {
                    case WALL -> isVisibleCell ? wall : wallHidden;
                    case EMPTY -> isVisibleCell ? grass : grassHidden;
                    case EXIT -> grass;
                    case PLAYER -> playerImage;
                    case ENEMY -> dragonImage;
                    default -> grass;
                };
                if (image == null) continue;
                graphics2d.drawImage(image, y * tileSize, x * tileSize, tileSize, tileSize, null);
            }
        }
    }
}
