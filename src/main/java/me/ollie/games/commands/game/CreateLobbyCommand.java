package me.ollie.games.commands.game;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.GameFactory;
import me.ollie.games.lobby.LobbyManager;
import me.ollie.games.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CreateLobbyCommand extends SubCommand {


    public CreateLobbyCommand() {
        super("createlobby", true, NO_ALIASES);
    }

    @Override
    public void run(Player sender, String[] args) {
        String gameStr = "random";
        boolean requiresForceStart = true;

        if (args.length == 1)
            gameStr = args[0];

        if (args.length == 2)
            requiresForceStart = Boolean.parseBoolean(args[1]);

        AbstractGame game = GameFactory.getGame(gameStr);

        LobbyManager.getInstance().createLobby(GameFactory.getGame(gameStr), requiresForceStart);
        sender.sendMessage(ChatColor.GRAY + "Created game of " + ChatColor.AQUA + game.getName() + "." + ChatColor.GRAY + " Requires Force Start: " + ChatColor.AQUA + requiresForceStart);
        MessageUtil.broadcast("A new game of " + game.getName() + " has been created! :)");
    }
}
