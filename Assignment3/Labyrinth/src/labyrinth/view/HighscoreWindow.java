/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import labyrinth.model.Highscore;

/**
 *
 * @author andreicristea
 */
public class HighscoreWindow extends JDialog {
    private final JTable table;
    
    public HighscoreWindow(List<Highscore> highscores, JFrame parent) {
        super(parent, true);
        table = new JTable(new HighscoreTableModel(highscores));
        table.setFillsViewportHeight(true);
        
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(
                table.getModel()
        );
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);
        
        add(new JScrollPane(table));
        setSize(400, 400);
        setTitle("Maximum scores of Labyrithm");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
