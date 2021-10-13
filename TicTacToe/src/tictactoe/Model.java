/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author andreicristea
 */
public class Model {
    private int size;
    private Player actualPlayer;
    private Player[][] table;
    
    public Model(int size) {
        this.size = size;
        this.actualPlayer = Player.X;
        
        this.table = new Player[size][size];
        
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                this.table[i][i] = Player.NOBODY;
            }
        } 
    }
    
    public Player step(int row, int column) {
        if (table[row][column] != Player.NOBODY) {
            return table[row][column];
        }
        
        table[row][column] = actualPlayer;
        
        if (actualPlayer == Player.X) {
            actualPlayer = Player.O;
        } else {
            actualPlayer = Player.X;
        }
        
        
        return table[row][column];
    }
    
    public Player findWinner() {
        for (var i = 0; i < size; ++i) {
            if (table[0][i] != Player.NOBODY) {
                boolean ok = true;
                
                for (var j = 1; ok && j < size; ++j) {
                    ok = ok && table[j][i] == table[0][i];
                }
                
                if (ok) {
                    return table[0][i];
                }
            }
        }
        
        
        for (var i = 0; i < size; ++i) {
            if (table[0][i] != Player.NOBODY) {
                boolean ok = true;
                
                for (var j = 1; ok && j < size; ++j) {
                    ok = ok && table[i][j] == table[i][0];
                }
                
                if (ok) {
                    return table[i][0];
                }
            }
        }
        
        if (table[0][0] != Player.NOBODY) { 
            boolean ok = true;
            for (int j = 1; ok && j < size; ++j) {
                ok = ok && table[j][j] == table[0][0];
            }
            if (ok) {
                return table[0][0];
            }
        }
        
        
        if (table[0][size - 1] != Player.NOBODY) {
            boolean ok = true;
            for (int j = 1; ok && j < size; ++j) {
                ok = ok && table[j][size - 1 - j] == table[0][size - 1];
            }
            if (ok) {
                return table[0][size - 1];
            }
        }
        
        return Player.NOBODY;
    }
    
    public Player getActualPlayer() {
        return actualPlayer;
    }
}
