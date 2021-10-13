/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moneytransfer;

/**
 *
 * @author andreicristea
 */
public class NotEnoughMoneyException extends Exception {
    private final BankAccount account;
    private final int amount;
    
    public NotEnoughMoneyException(BankAccount account, int amount) {
        this.account = account;
        this.amount = amount;
    }
    
    public BankAccount getAccount() {
        return account;
    }

    public int getAmount() {
        return amount;
    }
}
