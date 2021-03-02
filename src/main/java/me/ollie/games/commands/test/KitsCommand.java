package me.ollie.games.commands.test;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.games.survivalgames.kit.KitGUI;
import me.ollie.games.games.survivalgames.kit.KitManager;
import me.ollie.games.gui.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class KitsCommand extends SubCommand {

    public KitsCommand() {
        super("kits", true, NO_ALIASES);
    }

    @Override
    public void run(Player sender, String[] args) {
        GUIManager.getInstance().openGuiFor(sender, new KitGUI(new KitManager((Collection<Player>) Bukkit.getOnlinePlayers())).getGui());
    }
}
