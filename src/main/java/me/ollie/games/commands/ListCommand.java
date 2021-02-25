package me.ollie.games.commands;

import me.ollie.games.gui.GUIManager;
import me.ollie.games.lobby.LobbyGUI;
import org.bukkit.entity.Player;

public class ListCommand extends SubCommand {

    public ListCommand() {
        super("list", false, new String[]{"l"});
    }

    @Override
    public void run(Player sender, String[] args) {
        GUIManager.getInstance().openGuiFor(sender, new LobbyGUI().getGui());
    }
}
