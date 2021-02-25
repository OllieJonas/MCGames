package me.ollie.games.games;

import lombok.experimental.UtilityClass;
import me.ollie.games.core.AbstractGameMap;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class MapCollectionFactory {

    public Collection<AbstractGameMap> getMapsFor(AbstractGame game) {
        if (game.getName().equals("Survival Games")) {
            return List.of(new AbstractGameMap() {
                @Override
                public String getName() {
                    return "big boy";
                }
            }, new AbstractGameMap() {
                @Override
                public String getName() {
                    return "gigantic penis";
                }
            });
        } else {
            throw new IllegalArgumentException("this game doesn't exist boss big sad");
        }
    }
}
