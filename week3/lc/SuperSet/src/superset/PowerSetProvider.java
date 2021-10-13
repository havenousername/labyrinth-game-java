/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author andreicristea
 */
public class PowerSetProvider {
    
    public <Element> Set<Set<Element>> getPowerSet(Set<Element> set) {
        Set<Set<Element>> powerSet = new HashSet<>();
        
        powerSet.add(new HashSet<>());
        if (!set.isEmpty()) {
            Element element = getElementWithoutElem() 
        }
    }
     
}
