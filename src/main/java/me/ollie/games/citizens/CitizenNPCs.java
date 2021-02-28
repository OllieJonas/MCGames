package me.ollie.games.citizens;

import lombok.Getter;
import me.ollie.games.citizens.traits.SpawnNPCTrait;
import me.ollie.games.gui.GUIManager;
import me.ollie.games.lobby.LobbyGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CitizenNPCs {

    @Getter
    private final List<GameNPC> gameListNpc;

    public CitizenNPCs() {
        this.gameListNpc = new ArrayList<>();
    }

    public CitizenNPCs registerNPCs() {
        NPC spawnNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Survival Games - Click to Join");

        spawnNPC.addTrait(new SpawnNPCTrait());

        registerNPC(new GameNPC(spawnNPC.clone(), new Location(Bukkit.getWorld("world"), 467.5, 4.5, 60.5, 0, 0), p -> GUIManager.getInstance().openGuiFor(p, new LobbyGUI().getGui())));
        registerNPC(new GameNPC(spawnNPC.clone(), new Location(Bukkit.getWorld("world"), 479.5, 4.5, 60.5, 0, 0), p -> GUIManager.getInstance().openGuiFor(p, new LobbyGUI().getGui())));
        return this;
    }

    private void registerNPC(GameNPC npc) {
        gameListNpc.add(npc);
    }
}
