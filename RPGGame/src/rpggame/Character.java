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
public abstract class Character {
    protected String name;
    protected int hitPoints;
    protected int attackValue;

    public String getName() {
        return name;
    }

    public int getAttackValue() {
        return attackValue;
    }
    
    public int getHitPoints() {
        return this.hitPoints;
    }
    
    public void beDamaged(int damagePoints) {
        if (this.isDead()) {
            return;
        }
        this.hitPoints -= damagePoints;
    };
    
    public Boolean isDead() {
        return this.hitPoints <= 0;
    }

    protected Character(String name, int hitPoints, int attackValue) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.attackValue = attackValue;
    }
  
    public void attack(Character ch) {
        ch.beDamaged(this.attackValue);
    }

    @Override
    public String toString() {
        return "Character{" + "name=" + name + ", hp=" + hitPoints + '}';
    }
    
    
}
