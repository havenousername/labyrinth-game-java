/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author andreicristea
 */
public class GameOverDialog extends OKCancelDialog {
    private final JLabel gameOverLabel;
    private String gameOverText;
    private final Consumer<Boolean> onProccessOk;
    
    public GameOverDialog(JFrame frame, String name, Consumer<Boolean> onProccessOk) {
        super(frame, name, "Play Again", "Quit");
        gameOverLabel = new JLabel();
        gameOverLabel.setSize(200, 200);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        gameOverLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        this.onProccessOk = onProccessOk;
        setLayout(new BorderLayout());
        add("Center", gameOverLabel);
        add("South", btnPanel);
        pack();
        setResizable(false);
    }

    @Override
    protected boolean processOK() {
        if (onProccessOk != null) {
            onProccessOk.accept(true);
        }
        return true;
    }

    @Override
    protected void processCancel() {
        System.exit(0);
    }

    public String getGameOverText() {
        return gameOverText;
    }
    
    public void setGameOverText(String txt) {
        gameOverText = txt;
        gameOverLabel.setText(gameOverText);
        pack();
    }
}
