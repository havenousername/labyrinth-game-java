/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import labyrinth.model.LevelCell;

/**
 *
 * @author andreicristea
 */
public class TimerPanel extends JPanel {
    private final JLabel timerLabel;
    private final long startTime;
    private final AtomicLong currentTime;
    private final ExecutorService executorService;
    private final AtomicInteger seconds;
    private final GameWindow gameWindow;
    private final AtomicBoolean recalculate;
    private final JLabel levelLabel;
    
    
    TimerPanel(GameWindow parentFrame, int height) {
        setSize(parentFrame.getSize().width, height);
        setLayout(new FlowLayout());
        gameWindow = parentFrame;
        
        timerLabel = new JLabel();
        levelLabel = new JLabel();
        updateLevel();
        
        startTime = System.currentTimeMillis();
        currentTime = new AtomicLong();
        recalculate = new AtomicBoolean();
        recalculate.set(true);
        executorService = Executors.newSingleThreadExecutor();
        seconds = new AtomicInteger();
        seconds.set(WIDTH);
        add(levelLabel);
        add(timerLabel);
        recalculateCurrentTime();
    }
    
    private static String getSecond(Duration dur) {
        return (dur.toSeconds() == 1 ? "second" : "seconds");
    }
    
    private static String getMinute(Duration dur) {
        return (dur.toMinutes() == 1 ? "minute" : "minutes");
    }
    
    private static String getHour(Duration dur) {
        return (dur.toMinutes() == 1 ? "hour" : "hours");
    }
    
    private static String convertToTimeString(final int time) {
       Duration dur = Duration.ofSeconds(time);
       var seconds = dur.toSeconds() % 60;
       var minutes = dur.toMinutes() % 60;
       var hours = dur.toHours();
       if (hours == 0 && minutes == 0) {
           return String.format("Current session time %d " + 
                   getSecond(dur), seconds);
       } else if (hours == 0 ) {
           return String.format("Current session time %d " + 
                   getMinute(dur) + " %d " + getSecond(dur) , minutes, seconds);
       }  
       return String.format("Current session time %d  " + getHour(dur) +
               " %d " + getMinute(dur) + " %d " + getSecond(dur),
               hours,
               minutes,
               seconds
       );
    }
    
    public void close() {
        recalculate.set(false);
        this.executorService.shutdown();
        this.gameWindow.remove(this);
    }
    
    public void updateLevel() {
        levelLabel.setText("Current level " + gameWindow.getGame().getCurrentLevel().getGameId().getId());
    }
    
    private void recalculateCurrentTime() {
        this.executorService.submit(() -> {
            while (!gameWindow.getGame().isGameEnded() && 
                    !gameWindow.getGame().getCurrentLevel().isLevelOver() &&
                    recalculate.get()) {
                currentTime.set(System.currentTimeMillis());
                int sec = Math.round(this.currentTime.get() - this.startTime);
                seconds.set(sec / 1000);
                timerLabel.setText(convertToTimeString(seconds.get()));
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TimerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
