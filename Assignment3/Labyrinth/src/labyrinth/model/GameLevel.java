/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

import labyrinth.model.cameravision.CameraBottomOverflow;
import labyrinth.model.cameravision.CameraLeftOverflow;
import labyrinth.model.cameravision.CameraRightOverflow;
import labyrinth.model.cameravision.CameraTopOverflow;

/**
 *
 * @author andreicristea
 */
public class GameLevel implements Comparable<GameLevel> {
    public static final int PLAYER_VISION = 3;
    public static final int DRAGON_VISION = 1;
    public static final int CAMERA_VISION = 8;
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
                        playerPosition = new Position(i, j);
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
    
    private boolean isBetweenPosition(int i, int j, int x, int y, int width) {
        return isAroundCellTop(i, x, width) && 
                isAroundCellBottom(i, x, width) && 
                isAroundCellLeft(j, y, width) && 
                isAroundCellRight(j, y, width);
    }
    
    
    private static boolean isAroundCellTop(int i, int x, int width) {
        return i >= x - width;
    }
    
    private static boolean isAroundCellBottom(int i, int x, int width) {
        return i <= x + width;
    }
    
    private boolean isAroundCellRight(int j, int y, int width) {
        return j <= y + width;
    }
    
    private boolean isAroundCellLeft(int j, int y, int width) {
        return j >= y - width;
    }
    
    private boolean isPlayerBetweenPosition(int i, int j, int width) {
        return isBetweenPosition(i, j, player.getPosition().getX(), player.getPosition().getY(), width);
    }
    
    private boolean isDragonBetweenPosition(int i, int j, int width) {
        return isBetweenPosition(i, j, dragon.getPosition().getX(), dragon.getPosition().getY(), width);
    }
    
    private List<LevelCell> getPlayerSurroundingCells(int width) {
        List<LevelCell> cells = new ArrayList<>();
        
        for (int i = 0; i < levelCells.length; i++) {
            for (int j = 0; j < levelCells[i].length; j++) {
                if (isPlayerBetweenPosition(i, j, width)) {
                    cells.add(levelCells[i][j]);
                }
            }
        }
        
        return cells;
    }
    
    public List<LevelCell> getPlayerVisibleCells() {
        return getPlayerSurroundingCells(PLAYER_VISION);
    }
    
    public List<LevelCell> getPlayerAttackCells() {
        return getPlayerSurroundingCells(DRAGON_VISION);
    }
        
    public LevelCell[][] getCameraVisionCells() {
        Deque<List<LevelCell>> cells = new LinkedList<>();
        Position playerPosition = player.getPosition();
        int shiftX = CAMERA_VISION / 2;
        int shiftY = CAMERA_VISION / 2;
        
        int shiftBottom = new CameraBottomOverflow(this.player.getPosition(), shiftX).getCellShiftSize();
        int shiftTop = new CameraTopOverflow(this.player.getPosition(), shiftX, levelCells.length).getCellShiftSize();
        int shiftLeft = new CameraLeftOverflow(this.player.getPosition(), shiftY, levelCells[0].length).getCellShiftSize();
        int shiftRight = new CameraRightOverflow(this.player.getPosition(), shiftY).getCellShiftSize();

        for (int i = 0; i < levelCells.length; i++) {
            List<LevelCell> cellsLocal = new ArrayList<>();
            for (int j = 0; j < levelCells[i].length; j++) {
                if (
                    isAroundCellTop(i, playerPosition.getX(), shiftTop) && 
                    isAroundCellBottom(i, playerPosition.getX(), shiftBottom) && 
                    isAroundCellLeft(j, playerPosition.getY(), shiftLeft) && 
                    isAroundCellRight(j, playerPosition.getY(), shiftRight)
                   ) {
                    cellsLocal.add(levelCells[i][j]);
                }
            }
            
            if (!cellsLocal.isEmpty()) {
                cells.add(cellsLocal);
            }
        }
        
        return cells.stream()
                .map(l -> l.stream().toArray(LevelCell[]::new))
                .toArray(LevelCell[][]::new);
    }
    
    public boolean isPlayerVisibleCell(LevelCell lc) {
        return getPlayerVisibleCells().contains(lc);
    }
    
    public boolean hasEnemyPlayerVisibleCells() {
        return getPlayerAttackCells().stream().filter(levelCell -> levelCell.getLevel().equals(Level.ENEMY)).count() > 0;
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
                    throw new NullPointerException("Game levels rows are not the same size for level with id  " + gameId.getId());
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

