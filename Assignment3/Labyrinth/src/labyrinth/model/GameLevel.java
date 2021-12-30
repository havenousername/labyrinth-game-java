/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author andreicristea
 */
public class GameLevel implements Comparable<GameLevel> {
    static private int PLAYER_VISION = 3;
    private final GameId gameId;
    private final int rows, cols;
    private final LevelCell[][] levelCells;
    private Player player;
    private Dragon dragon;
    private Position levelExitPosition;
    private Position playerPosition;
    private List<List<String>> gameLevelOrigin;
    
    public GameLevel(Player player, GameId gameId) {
        // guest actors
        this.player = player;
        dragon = new Dragon(this);
        
        this.gameId = gameId;
        this.gameLevelOrigin = this.gameId.getLevel();
        int maxCol = 0;
        for (List<String> s : gameLevelOrigin) if (s.size() > maxCol) maxCol = s.size();
        rows = maxCol;
        cols = gameLevelOrigin.size();
        levelCells = new LevelCell[cols][rows];
        generateLevels(gameLevelOrigin);
        dragon.initialMove();
    }
    
    public void activate(CyclicBarrier barrier) {
        player.move(playerPosition.getX(), playerPosition.getY());
        this.activateDragon(barrier);
    }
    
    public void reset() {
        this.dragon = new Dragon(this);
        generateLevels(gameLevelOrigin);
    }
    
    public void activateDragon(CyclicBarrier barrier) {
        dragon.act(barrier);
    }
    
    private void generateLevels(List<List<String>> gameLevelChars) {
        for (var i = 0; i < gameLevelChars.size(); i++) {
            for (var j = 0; j < gameLevelChars.get(i).size(); j++) {
                Level level;
                switch(gameLevelChars.get(i).get(j)) {
                    case "P" -> { 
                        System.out.println("Case p");
                        playerPosition = new Position(i, j);
                        System.out.println("P" + playerPosition);
                        level = Level.PLAYER;
                    } 
                    case "#" -> {
                        level = Level.WALL;
                    }
                    case "." -> {
                        level = Level.EXIT;
                        levelExitPosition = new Position(i, j);
                    }
                    case " ", "E" -> {
                        level = Level.EMPTY;
                    }
                    default -> {
                        level = Level.EMPTY;
                    }
                }
                levelCells[i][j] = new LevelCell(level, i, j);
            }
        }
    }
    
    private boolean isBetweenPosition(int i, int j) {
        return i >= player.position.getX() - PLAYER_VISION && i <= player.position.getX() + PLAYER_VISION
                        &&
                      j >= player.position.getY() - PLAYER_VISION && j <= player.position.getY() + PLAYER_VISION;
    }
    
    public List<LevelCell> getPlayerVisibleCells() {
        List<LevelCell> cells = new ArrayList<>();
        
        for (int i = 0; i < levelCells.length; i++) {
            for (int j = 0; j < levelCells[i].length; j++) {
                if (isBetweenPosition(i, j)) {
                    cells.add(levelCells[i][j]);
                }
            }
        }
        
        return cells;
    }
    
    public boolean isPlayerVisibleCell(LevelCell lc) {
        return getPlayerVisibleCells().contains(lc);
    }
    
    public boolean hasEnemyPlayerVisibleCells() {
        return getPlayerVisibleCells().stream().filter(levelCell -> levelCell.getLevel().equals(Level.ENEMY)).count() > 0;
    }
    
    public boolean move(InteractiveActor actor, Position newPosition) {
        if (levelCells[newPosition.getX()][newPosition.getY()].getLevel().level == '#') {
            return false;
        }
        levelCells[actor.getPosition().getX()][actor.getPosition().getY()].setLevel(Level.EMPTY);
        levelCells[newPosition.getX()][newPosition.getY()].setLevel(actor.getFieldType());
        actor.move(newPosition.x, newPosition.y);
        return true;
    }
    
    public boolean movePlayer(Direction direction) {
        return move(this.player, direction);
    }
    
    public boolean move(InteractiveActor actor, Direction direction) {
        return move(actor, actor.position.translate(direction));
    }
    
    public boolean isPlayerDied() {
        return player.isAlive();
    }

    public GameId getGameId() {
        return gameId;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    private List<LevelCell> getItems(int item, boolean isColumn) {
        return Arrays.asList(Stream.of(levelCells)
                .flatMap(Stream::of)
                .filter(levelCell -> isColumn ? 
                        levelCell.getPosition().getX() == item :
                        levelCell.getPosition().getY() == item)
                .toArray(LevelCell[]::new));
    }
    
    public List<LevelCell> getColumn(int x) {
        return getItems(x, false);
    }
    
    public List<LevelCell> getRow(int y) {
        return getItems(y, true);
    }
    
    
    public LevelCell getCell(int x, int y) {
        if (x > getCols() || y > getRows()) {
            return null;
        }
        return levelCells[x][y];
    }

    public LevelCell[][] getLevelCells() {
        return levelCells;
    }

    public Player getPlayer() {
        return player;
    }

    public Dragon getDragon() {
        return dragon;
    }
    
    public boolean isLevelOver() {
        return !this.player.isAlive();
    }
    
    public boolean isLevelWin() {
        return this.player.getPosition().equals(levelExitPosition);
    }
    
    public boolean isLevelEnded() {
        return isLevelWin() || isLevelOver();
    }
    
    public String levelMap() {
        StringBuilder levelStrBuilder = new StringBuilder();
        for (var levelCellRow : levelCells) {
            for (var levelCellItem : levelCellRow) {
                if (levelCellItem == null) {
                    throw new NullPointerException("Game levels rows are not the same size");
                }
                levelStrBuilder.append(levelCellItem.getLevel().level);
            }
            levelStrBuilder.append("\n");
        }
        
        return levelStrBuilder.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder levelStrBuilder = new StringBuilder();
        for (LevelCell[] levelCellRow : levelCells) {
            for (LevelCell levelCellItem : levelCellRow) {
                levelStrBuilder.append(levelCellItem).append(";");
            }
            levelStrBuilder.append("\n");
        }
        
        return levelStrBuilder.toString();
    }

    @Override
    public int compareTo(GameLevel o) {
        return gameId.compareTo(o.gameId);
    }
}

