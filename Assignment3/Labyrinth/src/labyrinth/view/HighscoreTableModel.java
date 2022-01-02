/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;
import labyrinth.model.Highscore;

/**
 *
 * @author andreicristea
 */
public class HighscoreTableModel extends AbstractTableModel {
    private final List<Highscore> highscores;
    private final List<String> columnNames = new ArrayList();
    
    HighscoreTableModel(List<Highscore> highscores) {
        this.highscores = highscores.stream().limit(10).collect(Collectors.toList());
        columnNames.add("Name");
        columnNames.add("Difficulty");
        columnNames.add("Completed levels");
        columnNames.add("Last level");
    }
    
    @Override
    public int getRowCount() {
        return highscores.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Highscore highscore = highscores.get(rowIndex);
       Object obj;
       switch(columnIndex) {
           case 0 -> obj = highscore.getName();
           case 1 -> obj = highscore.getDifficulty();
           case 2 -> obj = highscore.getTotalLevels();
           case 3 -> obj = highscore.getLastLevel();
           default -> obj = highscore.getName();
       }
       return obj;
    }
    
    @Override
    public String getColumnName(int i) {
        return columnNames.get(i);
    }
}
