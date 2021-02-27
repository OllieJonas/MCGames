package me.ollie.games;

import lombok.Getter;
import me.ollie.games.commands.GameCommand;
import me.ollie.games.commands.test.TestCommand;
import me.ollie.games.events.ChatEvents;
import me.ollie.games.gui.GUIEvents;
import me.ollie.games.lobby.LobbyItems;
import me.ollie.games.util.BossBarManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Games extends JavaPlugin {

    @Getter
    public static Games instance;

    public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 473.5, 10.5, 98.5, -180F, 0F);

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();

        instance = this;
    }

    @Override
    public void onDisable() {
        BossBarManager.getInstance().destroy();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("game").setExecutor(new GameCommand());
        getCommand("test").setExecutor(new TestCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);
        Bukkit.getPluginManager().registerEvents(new GUIEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyItems(), this);
    }
}
