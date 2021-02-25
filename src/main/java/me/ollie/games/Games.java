package me.ollie.games;

import me.ollie.games.commands.GameCommand;
import me.ollie.games.commands.test.TestCommand;
import me.ollie.games.gui.GUIEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Games extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("game").setExecutor(new GameCommand());
        getCommand("test").setExecutor(new TestCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GUIEvents(), this);
    }
}
