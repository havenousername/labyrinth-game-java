package powerset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PowerSetProvider {
    
    public <Element> Set< Set<Element> > getPowerSet(Set<Element> set) {
        Set< Set<Element> > powerSet = new HashSet<>();
        powerSet.add(new HashSet<>());
        if(!set.isEmpty()) {
            Element element = getAnElementOfSet(set);
            Set<Element> setWithoutElem = getSetWithoutElem(set, element);
            Set<Set<Element>> subsetsWithoutElem = getPowerSet(setWithoutElem);
            for(Set<Element> subset : subsetsWithoutElem) {
                Set<Element> subsetWithElem = getSetWithElem(subset, element);
                powerSet.add(subset);
                powerSet.add(subsetWithElem);
            }
        }
        return powerSet;
    }
    
    private <Element> Element getAnElementOfSet(Set<Element> set) {
        Iterator it = set.iterator();
        return (Element) it.next();
    } 
    
    private <Element> Set<Element> getSetWithoutElem(Set set, Element element) {
        Set<Element> setWithoutElem = new HashSet<>(set);
        setWithoutElem.remove(element);
        return setWithoutElem;
    }
    
    private <Element> Set<Element> getSetWithElem(Set set, Element element) {
        Set<Element> setIncludingElem = new HashSet<>(set);
        setIncludingElem.add(element);
        return setIncludingElem;
    }
}
