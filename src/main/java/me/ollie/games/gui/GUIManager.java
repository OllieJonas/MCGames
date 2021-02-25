package me.ollie.games.gui;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager {

    @Getter
    private static final GUIManager instance = new GUIManager();

    private final Map<UUID, GUI> openedGuis = new HashMap<>();


    public void openGuiFor(Player player, GUI gui) {
        openedGuis.put(player.getUniqueId(), gui);
        gui.open(player);
    }

    public void closeGuiFor(Player player) {
        UUID uuid = player.getUniqueId();
        GUI gui = openedGuis.remove(uuid);

        if (gui != null)
            gui.close(player);
    }

    public GUI getGuiFor(Player player) {
        return openedGuis.get(player.getUniqueId());
    }
}
