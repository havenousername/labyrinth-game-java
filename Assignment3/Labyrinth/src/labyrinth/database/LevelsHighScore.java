/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.database;

import labyrinth.model.Highscore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreicristea
 */
public class LevelsHighScore extends Database<Highscore> implements Insertable<Highscore> {
    public LevelsHighScore() {
        super("jdbc:derby://localhost:1527", "labyrinth", "highscores");
    }
    
    @Override
    protected boolean createTable() {
       try {
            ResultSet res = connection.getMetaData().getTables(null, "APP", tableName.toUpperCase(), null);
            if (!res.next()) {
                String sql = "CREATE TABLE " + tableName + " (" + 
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                    "name VARCHAR(50) NOT NULL, " +
                    "difficulty VARCHAR(20) NOT NULL, " +
                    "last_level INTEGER NOT NULL, " + 
                    "total_levels INTEGER NOT NULL)"
                    ;
                return super.createTable(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Levels.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return false;
    }

    @Override
    protected void loadData() {
        super.loadData((ResultSet rs) -> {
            int totalLevels = rs.getInt("total_levels");
            int lastLevel = rs.getInt("last_level");
            String difficulty = rs.getString("difficulty");
            String name = rs.getString("name");
            return new Highscore(difficulty, totalLevels, lastLevel, name);
        }, "SELECT * from " + tableName + " order by name, difficulty");
    }

    @Override
    public int insert(Highscore stored) {
        String sql = "INSERT INTO " + tableName + 
                    "(difficulty, last_level, total_levels, name)" + 
                    "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, stored.getDifficulty());
            statement.setInt(2, stored.getLastLevel());
            statement.setInt(3, stored.getTotalLevels());
            statement.setString(4, stored.getName());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LevelsHighScore.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return -1;
    }

    
    
}
