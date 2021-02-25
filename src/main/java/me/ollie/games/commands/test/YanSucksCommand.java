package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import org.bukkit.entity.Player;

public class YanSucksCommand extends SubCommand {

    public YanSucksCommand() {
        super("yansucks", false, new String[]{"ys"});
    }

    @Override
    public void run(Player player, String[] args) {
        player.sendMessage("yan sucks (arguments: " + String.join(", ", args) + ")");
    }
}
