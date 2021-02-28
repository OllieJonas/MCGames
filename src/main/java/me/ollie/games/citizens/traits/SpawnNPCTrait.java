package me.ollie.games.citizens.traits;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.event.EventHandler;

public class SpawnNPCTrait extends Trait {

    public SpawnNPCTrait() {
        super("lobbytrait");
    }

    @EventHandler
    public void onRightClick(NPCRightClickEvent event) {
    }
}
