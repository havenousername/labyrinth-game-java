/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import labyrinth.database.Levels;
import labyrinth.database.LevelsHighScore;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

/**
 *
 * @author andreicristea
 */
public class Game implements LevelIterator<Map.Entry<String, GameLevel>> {
    private Map<String, GameLevel> gameLevels = new LinkedHashMap<>();
    private GameLevel level = null;
    private final Iterator levelIterator;
    private final Levels levelsDatabase = new Levels();
    private final LevelsHighScore highscoreDatabase = new LevelsHighScore();
    private boolean isBetterHighScore = false;
    private final Player player;
    private int countLevels = 0;
    
    
    public Game(String playerName) {
        this.player = new Player(playerName);
        readLevels();
        Set levelSet = gameLevels.entrySet();
        this.levelIterator = levelSet.iterator();
    }
    
    public Game() {
        this.player = new Player("");
        readLevels();
        Set levelSet = gameLevels.entrySet();
        this.levelIterator = levelSet.iterator();
    }
    
    
    private void readLevels() {
        List<GameId> gameLevelsDb = levelsDatabase.getData();
        gameLevelsDb.forEach(gameLevel -> {
            gameLevels.put(gameLevel.getId(), new GameLevel(player, gameLevel));
        });
        
        gameLevels = this.gameLevels
                .entrySet()
                .stream()
                .sorted((i1, i2) -> i1.getValue().compareTo(i2.getValue()))
                .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (e1, e2) -> e1, LinkedHashMap::new));
    }
    
   
    
   
    public boolean stepPlayer(Direction direction) {
        boolean stepped = level.movePlayer(direction);

        if (stepped && (isGameEnded() || level.isLevelOver())) {
            recordHighscore();
        }

        return stepped;
    }
    
    public void recordHighscore() {
        Highscore highscore = new Highscore(level.getGameId().difficulty, countLevels, level.getGameId().level, player.name);
        boolean hasInDatabase = highscoreDatabase.getData().stream().filter((highScore) -> highScore.getName().equals(player.getName())).count() > 0;
        System.out.println("Has in database " + hasInDatabase);
        if (hasInDatabase) {
            highscoreDatabase.update(highscore, highscore.getName());
        } else {
            highscoreDatabase.insert(highscore);
        }
        
        highscoreDatabase.loadData();
        isBetterHighScore = highscoreDatabase.getData().stream().filter((highScore) -> 
                (highScore.getName() == null ? player.name == null : highScore.getName().equals(player.name)) && 
                highScore.getTotalLevels() > countLevels).count() == 0;
    }   
    
    public void loadLevel(String difficulty, int level) {
        countLevels++;
        this.level = gameLevels.get(GameId.getId(difficulty, level));
        this.isBetterHighScore = false;
    }
    
    
    public void loadLevel(GameLevel gameLevel) {
        countLevels++;
        this.level = gameLevel;
        this.isBetterHighScore = false;
    }
    
    public void activateLevel(CyclicBarrier barrier) {
        this.level.activate(barrier);
    }
    
    public void printLevel() throws NullPointerException  {
        System.out.println(level.toString());
    }
    
    public String levelMap() {
        return level != null ? level.levelMap() : "";
    }
    
    public Position getPlayerPosition() {
        return new Position(player.position.getX(), player.position.getY());
    }
    
    // getters
    public GameLevel getCurrentLevel() {
        return level;
    }
    
    public LevelCell[][] getLevelCells() {
        return level.getLevelCells();
    }
    
    public boolean isLevelLoaded() {
        return level != null;
    }
    
    public int getLevelRows() {
        return level.getRows();
    }
    
    public int getLevelCols() {
        return level.getCols();
    }
    
    public LevelCell getLevelCell(int col, int row) {
        return level.getLevelCells()[col][row];
    }
    
    public boolean isGameEnded() {
        return !hasNextLevel() && level.isLevelEnded();
    }

    public boolean isIsBetterHighScore() {
        return isBetterHighScore;
    }
    
    public List<Highscore> getHighscores() {
        return this.highscoreDatabase.getData();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayerName(String name) {
        this.player.setName(name);
    }

    @Override
    public Map.Entry<String, GameLevel> nextLevel() {
        return (Map.Entry<String, GameLevel>)(levelIterator.next());
    }

    @Override
    public boolean hasNextLevel() {
        return levelIterator.hasNext();
    }

    @Override
    public boolean activateNextLevel() {
        if (!hasNextLevel()) {
            return false;
        } 
        loadLevel(nextLevel().getValue());
        return true;
    }
}
