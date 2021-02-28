package me.ollie.games.citizens;

import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class CitizensManager {

    @Getter
    private static CitizensManager instance;
    @Getter
    private final Map<UUID, Consumer<Player>> actions;

    public CitizensManager() {
        this.actions = new HashMap<>();
        new CitizenNPCs().registerNPCs().getGameListNpc().forEach(this::registerNPC);

        instance = this;
    }

    public void registerNPC(GameNPC npc) {
        npc.getNpc().spawn(npc.getLocation());
        actions.put(npc.getNpc().getUniqueId(), npc.getOnClick());
    }

    public void destroy() {
        actions.keySet().stream().map(uuid -> CitizensAPI.getNPCRegistry().getByUniqueId(uuid)).forEach(NPC::destroy);
    }


}