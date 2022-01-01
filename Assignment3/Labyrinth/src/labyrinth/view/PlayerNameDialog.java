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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author andreicristea
 */
public class PlayerNameDialog extends OKCancelDialog {
    private final JTextField nameTextField;
    private final JLabel errorText;
    private final Consumer<Boolean>  onProccessOk;
    private final JLabel gameStartLabel;
    private final JPanel panel;
    private boolean acceptConsumer;
    
    public PlayerNameDialog(JFrame frame, String title, String name, Consumer<Boolean> onProccessOk) {
        super(frame, title, "Submit", null);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        gameStartLabel = new JLabel();
        gameStartLabel.setSize(200, 200);
        gameStartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameStartLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        gameStartLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
        gameStartLabel.setText("Add your name and start game");
        
        nameTextField = new JTextField();
        nameTextField.setText(name);
        
        errorText = new JLabel();
        
        panel.add("South", nameTextField);
        panel.add("Center", errorText);
        panel.add("North", gameStartLabel);
        panel.setVisible(true);
        
        setLayout(new BorderLayout());
        this.onProccessOk = onProccessOk;
        
        add("Center", panel);
        add("South", btnPanel);
        pack();
        setResizable(false);
        acceptConsumer = true;
    }
     
    public String getValue() {
        return nameTextField.getText();
    }

    @Override
    protected boolean processOK() {
        if (getValue().equals("")) {
            errorText.setText("Please enter name more than 0 chars");
            pack();
            return false;
        }
        if (onProccessOk != null && acceptConsumer) {
            this.onProccessOk.accept(true);
        }
        errorText.setText("");
        return true;
    }

    public void setAcceptConsumer(boolean acceptConsumer) {
        this.acceptConsumer = acceptConsumer;
        gameStartLabel.setVisible(this.acceptConsumer);
        pack();
    }

    @Override
    protected void processCancel() {
        if (acceptConsumer) {
            System.exit(0);
        }
    }
}
