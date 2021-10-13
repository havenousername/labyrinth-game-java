/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author andreicristea
 */
class BaseWindow extends JFrame {
    
    
    public BaseWindow() {
        setTitle("Tic-Tac-Toe");
        this.setSize(400, 450);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        URL url = BaseWindow.class.getResource("icon.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }
    
    private void showExitInformation() {
        int n = JOptionPane.showConfirmDialog(this, "Do you really want to quit?", "Really?", JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (n == JOptionPane.YES_OPTION) {
            this.doUponExit();
        }
    }
    
    protected void doUponExit() {
        this.dispose();
    }
}
