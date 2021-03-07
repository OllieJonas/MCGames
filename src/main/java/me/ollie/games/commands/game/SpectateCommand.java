package me.ollie.games.commands.game;

import me.ollie.games.commands.SubCommand;
import me.ollie.games.games.AbstractGame;
import me.ollie.games.games.survivalgames.SurvivalGames;
import me.ollie.games.gui.GUIManager;
import me.ollie.games.gui.PlayerListGUI;
import me.ollie.games.lobby.Lobby;
import me.ollie.games.lobby.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpectateCommand extends SubCommand {

    public SpectateCommand() {
        super("spectate", false, NO_ALIASES);
    }

    @Override
    public void run(Player sender, String[] args) {
        if (!LobbyManager.getInstance().isInGame(sender)) {
            sender.sendMessage(ChatColor.RED + "You're not in a lobby!");
            return;
        }

        Lobby lobby = LobbyManager.getInstance().getLobbyFor(sender);
        AbstractGame game = lobby.getGame();

        if (!(game instanceof SurvivalGames)) {
            sender.sendMessage(ChatColor.RED + "Not survival games!");
            return;
        }

        SurvivalGames survivalGames = (SurvivalGames) game;

        if (!survivalGames.isSpectator(sender)) {
            sender.sendMessage(ChatColor.RED + "You are not a spectator! :(");
            return;
        }
        GUIManager.getInstance().openGuiFor(sender, spectateGUI(survivalGames).getGui());
    }

    private PlayerListGUI spectateGUI(SurvivalGames game) {
        return new PlayerListGUI("Spectate - Press ESC to Exit", game::getAlivePlayers, p -> new String[]{ChatColor.GRAY + "Click to spectate " + ChatColor.AQUA + p.getName()}, (p, t) -> p.teleport(t.getLocation()));
    }
}
