package me.ollie.games.util;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class SetUtils {
    /**
     * Calculates the difference between two sets
     *
     * @param original The original set
     * @param toRemove The items to remove
     *
     * @return The difference between two sets
     */
    public <E> Set<E> difference(Set<E> original, Set<E> toRemove) {
        original.removeAll(toRemove);
        return original;
    }
}
