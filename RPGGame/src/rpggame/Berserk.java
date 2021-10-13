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
public class Berserk extends Orc {
    public Berserk(String name, int hitPoints, int attackValue) {
        super(name, hitPoints, attackValue);
    }
  
    
   @Override
   public void beDamaged(int damagePoints) {
        this.hitPoints /= 2;
   };
}
