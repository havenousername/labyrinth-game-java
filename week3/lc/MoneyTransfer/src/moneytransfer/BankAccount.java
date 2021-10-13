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
public class BankAccount {
    private final String accountNumber;
    private final String owner;
    private int balance;

    public BankAccount(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
    }

    public String getOwner() {
        return owner;
    }

    public int getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
   
    
    public synchronized void deposit(int amount) throws NegativeAmountException {
        if (amount < 0) {
            throw new NegativeAmountException();
        }
        
        this.balance += amount;
    }
    
    public synchronized void widthdraw(int amount) throws NotEnoughMoneyException, NegativeAmountException {
        if (amount < 0) {
            throw new NegativeAmountException();
        }
        
        if (this.balance < amount) {
            throw new NotEnoughMoneyException(this, amount);
        }
        
        this.balance -= amount;
    }
    
}
