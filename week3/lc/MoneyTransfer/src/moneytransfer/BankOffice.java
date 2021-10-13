/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moneytransfer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andreicristea
 */
public class BankOffice {
    private final Map<String, BankAccount> accounts;

    public BankOffice() {
        this.accounts = new HashMap<>();
    }

    public Collection<BankAccount> getAccounts() {
        return accounts.values();
    }
    
    
    public void newAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }
    
    public void deleteAccount(BankAccount account) {
        accounts.remove(account.getAccountNumber());
    }
    
    public void transferr(String accountOfWithDrawal, String accountOfDeposit, int amount)
        throws NotEnoughMoneyException, AccountDoesntExistException, NegativeAmountException {
        BankAccount accOfWithdrawal = accounts.get(accountOfWithDrawal);
        if (accOfWithdrawal == null) {
            throw new AccountDoesntExistException(accountOfWithDrawal);
        }
        
        BankAccount accOfDeposit = accounts.get(accountOfDeposit);
        if (accOfDeposit == null) {
            throw new AccountDoesntExistException(accountOfWithDrawal);
        }
        
        accOfWithdrawal.widthdraw(amount);
        accOfDeposit.deposit(amount);
    }
    
    public void deposit(String account, int amount) 
             throws AccountDoesntExistException, NegativeAmountException {
        BankAccount accountOfDeposit = accounts.get(account);
        if (accountOfDeposit == null) {
            throw new AccountDoesntExistException(account);
        }
        
        accountOfDeposit.deposit(amount);
    }
}
