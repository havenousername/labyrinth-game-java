/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import labyrinth.model.Game;

/**
 *
 * @author andreicristea
 */
public class GameWindow extends JFrame {
    private Game game;
    private LabyrinthUI labyrinthUI;
    private String playerName = "";
    private final PlayerNameDialog playerNameDialog;
    private final AbstractAction playerNameDialogAction = new AbstractAction("Change player name") {
        @Override
        public void actionPerformed(ActionEvent e) {
            playerNameDialog.setVisible(true);
            if ( playerNameDialog.getButtonCode() != OKCancelDialog.OK ) return;
            playerOkDialog.accept(false);
        }
        
    };
    private CyclicBarrier dragonAttack; 
    
    private Consumer<Boolean> playerOkDialog = new Consumer<Boolean>() {
        @Override
        public void accept(Boolean initMain) {
            if (initMain) {
                try {
                    initMainWindow();
                } catch (IOException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            playerName = playerNameDialog.getValue();
            game.setPlayerName(playerName);
        }
    };
    
    
    public GameWindow() {
        game = new Game();
        dragonAttack = new CyclicBarrier(2);
        // player name dialog
        playerNameDialog = new PlayerNameDialog(this, "Name your player", playerName, playerOkDialog);
        playerNameDialog.setVisible(true);
    }
    
    private void initMainWindow() throws IOException {
//        game.nextLevel();
//        game.nextLevel();
        game.activateNextLevel();
        game.activateLevel(dragonAttack);
        
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    dragonAttack.await();
                    labyrinthUI.refresh();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        t1.start();
        
        setTitle("Labyrinth");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JMenu menuGame = new JMenu("Settings");
        
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuHighscores = new JMenuItem(new AbstractAction("Show High scores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighscoreWindow(game.getHighscores(), GameWindow.this);
            }
        });
        
        JMenuItem changePlayerName = new JMenuItem(playerNameDialogAction);
        
        JMenuItem menuExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuGame.add(menuHighscores);
        menuGame.add(changePlayerName);
        menuGame.add(menuExit); 
        menuBar.add(menuGame);
        setLayout(new BorderLayout());
        try {
            labyrinthUI = new LabyrinthUI(game);
            add(labyrinthUI, BorderLayout.CENTER);
        } catch (IOException ex) {}
        setJMenuBar(menuBar);
        
        // key adapter
//        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
