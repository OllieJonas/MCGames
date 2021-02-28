package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.util.FireworkUtil;
import org.bukkit.entity.Player;

public class FireworkCommand extends SubCommand {

    public FireworkCommand() {
        super("firework", true, new String[]{"fw"});
    }

    @Override
    public void run(Player sender, String[] args) {
        FireworkUtil.spawnFireworksAroundPlayer(sender);
    }
}
