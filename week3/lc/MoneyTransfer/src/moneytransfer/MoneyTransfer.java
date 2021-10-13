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
public class MoneyTransfer {

    /**
     * @param args the command line arguments
     * @throws moneytransfer.NotEnoughMoneyException
     * @throws moneytransfer.AccountDoesntExistException
     * @throws moneytransfer.NegativeAmountException
     */
    public static void main(String[] args)
           throws NotEnoughMoneyException, AccountDoesntExistException, NegativeAmountException {
        BankOffice office = new BankOffice();
        
        office.newAccount(new BankAccount("12323424", "Tom"));
        office.newAccount(new BankAccount("2342342", "Josh"));
        office.newAccount(new BankAccount("21324", "Ali"));
        
        office.deposit("12323424", 10000);
        office.deposit("2342342", 15000);
        office.deposit("21324", 17000);
        
        
    }
    
    public static void printBalances(BankOffice bankOffice) {
        bankOffice.getAccounts().forEach(account -> {
            System.out.println("Name: " + account.getOwner() + "; Amount: " + account.getBalance() + "; Account Number: " + account.getAccountNumber());
        });
    }
    
}
