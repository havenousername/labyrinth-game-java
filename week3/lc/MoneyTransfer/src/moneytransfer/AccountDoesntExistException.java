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
public class AccountDoesntExistException extends Exception {
    
    private final String account;

    public AccountDoesntExistException(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }
}
