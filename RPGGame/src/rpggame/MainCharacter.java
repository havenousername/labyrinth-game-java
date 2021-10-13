/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpggame;

/**
 *
 * @author andreicristea
 */
public class MainCharacter extends Character {
    private float defenceValue;
    
    public MainCharacter(String name, int hitPoints, int attackValue, float defenceValue) {
        super(name, hitPoints, attackValue);
        this.defenceValue = defenceValue;
    }
    
    @Override
    public void beDamaged(int damagePoints) {
         this.hitPoints -= (damagePoints / this.defenceValue); 
    };
}
