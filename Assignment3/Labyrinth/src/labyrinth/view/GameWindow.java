/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import labyrinth.model.Direction;
import labyrinth.model.Game;
import labyrinth.model.GameLevel;
import labyrinth.model.Highscore;
import labyrinth.model.LimitedSelect;

/**
 *
 * @author andreicristea
 */
public class GameWindow extends JFrame {
    private Game game;
    private LabyrinthUI labyrinthUI;
    private String playerName = "";
    private final PlayerNameDialog playerNameDialog;
    private final GameOverDialog gameOverDialog;
    private final AbstractAction playerNameDialogAction = new AbstractAction("Change player name") {
        @Override
        public void actionPerformed(ActionEvent e) {
            playerNameDialog.setAcceptConsumer(false);
            playerNameDialog.setVisible(true);
            if ( playerNameDialog.getButtonCode() != OKCancelDialog.OK ) return;
            playerOkDialog.accept(false);
        }
        
    };
    private final AbstractAction playAgain = new AbstractAction("Play Again") {
        @Override
        public void actionPerformed(ActionEvent e) {
            playerReplay.accept(false);
        }
    };
    private TimerPanel timerPanel;
    
    private CyclicBarrier dragonAttack; 
    private final AtomicBoolean gameEnded;
    
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
    
    private Consumer<Boolean> playerReplay = new Consumer<Boolean>() {
        @Override
        public void accept(Boolean isDialogCall) {
            if (gameEnded.get()) {
                gameEnded.set(true);
            }
            replay();
        }
    };
    
    private int timerPanelHeight = 40;
    
    void replay() {
        game = new Game();
        if (game.getCurrentLevel() != null) {
            game.getCurrentLevel().stopLevel();
        }
        
        gameEnded.set(false);
        dragonAttack = new CyclicBarrier(2);
        
        game.activateNextLevel();
        game.activateLevel(dragonAttack);
        
        refreshAfterDragonAttack();
        playerNameDialog.setAcceptConsumer(true);
        
        timerPanel.close();
        setLayout(new BorderLayout());
        
        timerPanel = new TimerPanel(this, timerPanelHeight);
        add(timerPanel, BorderLayout.NORTH);
        try {
            this.remove(labyrinthUI);
            labyrinthUI = new LabyrinthUI(game);
            add(labyrinthUI, BorderLayout.CENTER);
        } catch (IOException ex) {}
    }
    
    
    public GameWindow() {
        game = new Game();
        gameEnded = new AtomicBoolean();
        gameEnded.set(false);
        dragonAttack = new CyclicBarrier(2);
        // player name dialog
        playerNameDialog = new PlayerNameDialog(this, "Name your player", playerName, playerOkDialog);
        gameOverDialog = new GameOverDialog(this, "Game Over", playerReplay);
        playerNameDialog.setVisible(true);
    }
    
    private void refreshAfterDragonAttack() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    dragonAttack.await();
                    labyrinthUI.refresh();
                    if (!game.getPlayer().isAlive()) {
                        game.recordHighscore();
                        gameOverDialog.setGameOverText("Game ended. You lost!");
                        gameOverDialog.setVisible(true);
                        break;
                    }
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        t1.start();
    }
    
    private void initMenu() {
        JMenu menuGame = new JMenu("Settings");
        
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuHighscores = new JMenuItem(new AbstractAction("Show High scores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighscoreWindow(LimitedSelect.<Highscore>getFirstN(game.getHighscores(), 10), GameWindow.this);
            }
        });
        
        JMenuItem menuReplay = new JMenuItem(playAgain);
        
        JMenuItem menuExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuGame.add(menuReplay);
        menuGame.add(menuHighscores);
        menuGame.add(playerNameDialogAction);
        menuGame.add(menuExit); 
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
    }
    
    private void initMainWindow() throws IOException {
        game.activateNextLevel();
        game.activateLevel(dragonAttack);
        
        timerPanel = new TimerPanel(this, timerPanelHeight);
        setTitle("Labyrinth");
        setSize((GameLevel.CAMERA_VISION + 1) * LabyrinthUI.TILE_SIZE, 
                (GameLevel.CAMERA_VISION + 2) * LabyrinthUI.TILE_SIZE + 35 + timerPanelHeight);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        initMenu();
        setLayout(new BorderLayout());
        add(timerPanel, BorderLayout.NORTH);
        try {
            labyrinthUI = new LabyrinthUI(game);
            add(labyrinthUI, BorderLayout.CENTER);
        } catch (IOException ex) {}
        refreshAfterDragonAttack();
        // key adapter
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                super.keyPressed(keyEvent);
                if (!game.isLevelLoaded() || game.getCurrentLevel().isLevelOver() || game.isGameEnded()) {
//                    System.out.println("Game ended. You lost!");
                    return;
                }
                int keyCode = keyEvent.getKeyCode();
                Direction direction = null;
                switch (keyCode) {
                    case KeyEvent.VK_LEFT -> direction = Direction.LEFT;
                    case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
                    case KeyEvent.VK_UP -> direction = Direction.UP;
                    case KeyEvent.VK_DOWN -> direction = Direction.DOWN;
                }
                
                if (direction != null && game.stepPlayer(direction) ) {
                    labyrinthUI.repaint();
                    if (game.getCurrentLevel().isLevelOver()) {
                        gameEnded.set(true);
                        gameOverDialog.setGameOverText("Game ended. You lost!");
                        gameOverDialog.setVisible(true);
//                        System.out.println("Game ended. You lost!");
                    } else if (game.getCurrentLevel().isLevelWin() && game.isGameEnded()) {
                        gameOverDialog.setGameOverText("Game ended. You won!");
                        gameOverDialog.setVisible(true);
//                        System.out.println("Game ended");
                        gameEnded.set(true);
                    } else if (game.getCurrentLevel().isLevelEnded()) {
                        try {
                            gameEnded.set(false);
                            game.activateNextLevel();
                            game.activateLevel(dragonAttack);
                            timerPanel.updateLevel();
                            labyrinthUI.setImages();
                            refreshAfterDragonAttack();
                        } catch (IOException ex) {
                            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
//        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public AtomicBoolean getGameEnded() {
        return gameEnded;
    }

    public Game getGame() {
        return game;
    }
}
