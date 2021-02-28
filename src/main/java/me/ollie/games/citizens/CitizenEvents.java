package me.ollie.games.citizens;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizenEvents implements Listener {

    @EventHandler
    public void onInteract(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();

        if (CitizensManager.getInstance().getActions().containsKey(npc.getUniqueId()))
            CitizensManager.getInstance().getActions().get(npc.getUniqueId()).accept(player);
    }
}
