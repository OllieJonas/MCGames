package me.ollie.games.citizens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class GameNPC {

    private final NPC npc;

    private final Location location;

    private final Consumer<Player> onClick;
}
