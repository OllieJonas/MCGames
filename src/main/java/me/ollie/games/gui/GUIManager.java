package me.ollie.games.gui;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class GUIManager {

    @Getter
    private static final GUIManager instance = new GUIManager();

    private final Map<UUID, GUI> openedGuis = new HashMap<>();

    private final Map<GUI, Set<Player>> observers = new HashMap<>();


    public void openGuiFor(Player player, GUI gui) {
        openedGuis.put(player.getUniqueId(), gui);

        if (!observers.containsKey(gui))
            observers.put(gui, new HashSet<>());

        observers.get(gui).add(player);

        gui.open(player);
    }

    public void closeGuiFor(Player player) {
        UUID uuid = player.getUniqueId();

        GUI gui = openedGuis.remove(uuid);

        if (observers.get(gui) != null)
            observers.get(gui).remove(player);

        if (gui != null)
            gui.close(player);
    }

    public GUI getGuiFor(Player player) {
        return openedGuis.get(player.getUniqueId());
    }

    public void notifyAllObservers(GUI gui) {
        observers.get(gui).forEach(player -> gui.redraw());
    }
}
