/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model.enemy;

import labyrinth.model.Dieable;

/**
 *
 * @author andreicristea
 */
public interface Enemy {
    void kill(Dieable dieable);
}
