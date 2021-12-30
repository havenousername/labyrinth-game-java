/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.database;

/**
 *
 * @author andreicristea
 */
public interface Insertable<T> {
    public int insert(T stored);
}
