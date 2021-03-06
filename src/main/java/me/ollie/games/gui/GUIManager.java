package me.ollie.games.gui;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class GUIManager {

    @Getter
    private static final GUIManager instance = new GUIManager();

    private final Map<UUID, GUI> openedGuis = new HashMap<>();

    private final Map<String, Set<Player>> observers = new HashMap<>();


    public void openGuiFor(Player player, GUI gui) {
        openedGuis.put(player.getUniqueId(), gui);

        if (!observers.containsKey(gui.getName()))
            observers.put(gui.getName(), new HashSet<>());

        observers.get(gui.getName()).add(player);

        gui.open(player);
    }

    public void closeGuiFor(Player player) {
        UUID uuid = player.getUniqueId();

        GUI gui = openedGuis.remove(uuid);

        if (gui != null) {
            gui.close(player);

            if (observers.get(gui.getName()) != null)
                observers.get(gui.getName()).remove(player);
        }

    }

    public GUI getGuiFor(Player player) {
        return openedGuis.get(player.getUniqueId());
    }

    public void notifyAllObservers(GUI gui) {
        observers.get(gui.getName()).forEach(player -> gui.redraw());
    }
}
