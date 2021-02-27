package me.ollie.games.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.Set;
import java.util.logging.Logger;

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
        Logger.getLogger("Minecraft").info(String.valueOf(original == null));
        if (original == null || original.isEmpty() || toRemove == null || toRemove.isEmpty())
            return original;

        original.removeAll(toRemove);
        return original;
    }
}
