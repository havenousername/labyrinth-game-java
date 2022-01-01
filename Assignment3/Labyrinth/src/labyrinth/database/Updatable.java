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
public interface Updatable<T, B> {
    public int update(T stored, B updateBy);
}
