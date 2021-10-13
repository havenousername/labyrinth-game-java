package zoo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andreicristea
 */
public class Tiger extends WildAnimal {
    @Override
    public void makeSound() {
        System.out.println("RRRR, Tiger roars");
    }
    
    @Override
    public void move() {
        System.out.println("Tiger moves");
    }
    
}
