package labyrinth.database;

import labyrinth.model.GameId;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import labyrinth.model.LevelSrcs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andreicristea
 */
public final class Levels extends Database<GameId> {
    public Levels() {
        super("jdbc:derby://localhost:1527", "labyrinth", "levels");
    }

    
    @Override
    protected void loadData() {
        super.loadData((ResultSet rs) -> {
            int level = rs.getInt("level");
            String difficulty = rs.getString("difficulty");
            String pattern = rs.getString("pattern");
            
            // create levels srcs
            String dragonSrc = rs.getString("dragon_src");
            String wallSrc = rs.getString("wall_src");
            String wallHiddenSrc = rs.getString("wall_src_hidden");
            String grassSrc = rs.getString("grass_src");
            String grassHiddenSrc = rs.getString("grass_src_hidden");
            String playerSrc = rs.getString("player_src");
            return new GameId(
                    difficulty, 
                    level, 
                    pattern,
                    new LevelSrcs(dragonSrc, grassSrc, grassHiddenSrc, wallSrc, wallHiddenSrc, playerSrc)
            );
        }, "SELECT * FROM " + tableName + " order by difficulty, level");
    }

    @Override
    protected boolean createTable() {
        try {
            ResultSet res = connection.getMetaData().getTables(null, "APP", tableName.toUpperCase(), null);
            if (!res.next()) {
                String sql = "CREATE TABLE " + tableName + " (" + 
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "level INTEGER NOT NULL, " + 
                    "difficulty VARCHAR(20) DEFAULT 'EASY', " +
                    "pattern VARCHAR(10000) NOT NULL," + 
                    "dragon_src VARCHAR(100) NOT NULL ," +
                    "wall_src VARCHAR(100) NOT NULL ," +
                    "wall_src_hidden VARCHAR(100) NOT NULL ," +
                    "grass_src VARCHAR(100) NOT NULL ," +
                    "grass_src_hidden VARCHAR(100) NOT NULL ," + 
                     "player_src VARCHAR(100) NOT NULL)"
                    ;
                return super.createTable(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Levels.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }  
}
