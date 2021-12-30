/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.view;

import java.awt.BorderLayout;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author andreicristea
 */
public class PlayerNameDialog extends OKCancelDialog {
    private JTextField nameTextField;
    private JLabel errorText;
    private Consumer<Boolean>  onProccessOk;
    
    public PlayerNameDialog(JFrame frame, String title, String name, Consumer<Boolean> onProccessOk) {
        super(frame, title);
        nameTextField = new JTextField();
        nameTextField.setText(name);
        setLayout(new BorderLayout());
        errorText = new JLabel();
        this.onProccessOk = onProccessOk;
        add("North", errorText);
        add("Center", nameTextField);
        add("South", btnPanel);
        pack();
        setResizable(false);
    }
     
    public String getValue() {
        return nameTextField.getText();
    }

    @Override
    protected boolean processOK() {
        if (getValue().equals("") ) {
            errorText.setText("Please enter name more than 0 chars");
            pack();
            return false;
        }
        if (onProccessOk != null) {
            this.onProccessOk.accept(true);
        }
        errorText.setText("");
        return true;
    }

    @Override
    protected void processCancel() {}
}
