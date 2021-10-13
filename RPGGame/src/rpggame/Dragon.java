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
public abstract class Dragon extends Character {
    protected int minimalDamage; 
    public Dragon(String name, int hitPoints, int attackValue, int mininalDamage) {
        super(name, hitPoints, attackValue);
        this.minimalDamage = minimalDamage;
    }
    
    @Override
    public void beDamaged(int damagePoints) {
        if (damagePoints > this.minimalDamage) {
            this.hitPoints -= damagePoints;
        }
    };
}
