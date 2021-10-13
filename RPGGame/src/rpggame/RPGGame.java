/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpggame;

import java.util.ArrayList;

/**
 *
 * @author andreicristea
 */
public class RPGGame {
    
    public static void log(Character attacker, Character defender) {
        System.out.println(
                attacker.getName() + 
                        " attacks with " + 
                        attacker.getAttackValue() + 
                        " attack points on " + defender.getName() + 
                        " with hp: " + defender.getHitPoints() + " ."
        );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Character> characters = new ArrayList<>();
        characters.add(new MainCharacter("Main Character", 1000, 20, 30));
        characters.add(new RedDragon("Red Dragon", 2000, 35));
        characters.add(new BlackDragon("Black Dragon", 1000, 15));
        characters.add(new Berserk("Berserk", 500, 70));
        characters.add(new Defender("Defender", 2500, 10));
        characters.add(new Fighter("Figther", 300, 100));
        
        while (characters.size() != 1) {
            int randomAttacker = (int)(Math.random() * (characters.size() ));
            int randomDefender = (int)(Math.random() * (characters.size() ));
            Character attacker = characters.get(randomAttacker);
            Character defender = characters.get(randomDefender);
            
            if(defender.getHitPoints() <= 0) {
                characters.remove(defender);
                continue;
            }
            
            RPGGame.log(attacker, defender);
            attacker.attack(defender);
        }
        
        System.out.println("Winner: " + characters.get(0));
    }
    
}
