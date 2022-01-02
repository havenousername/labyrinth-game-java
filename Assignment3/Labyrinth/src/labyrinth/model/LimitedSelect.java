/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author andreicristea
 */
public class LimitedSelect {
    public static <T> List<T> getFirstN(List<T> data, int size) {
        return data.stream().limit(size).collect(Collectors.toList());
    }
}
