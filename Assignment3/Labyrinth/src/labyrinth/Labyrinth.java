/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import labyrinth.view.GameWindow;

/**
 *
 * @author andreicristea
 */
public class Labyrinth {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new GameWindow();
        } catch (IOException ex) {
            Logger.getLogger(Labyrinth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
