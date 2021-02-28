package me.ollie.games.commands.test;

import me.ollie.games.commands.AbstractCommand;

public class TestCommand extends AbstractCommand {

    @Override
    public void addSubCommands() {
        addSubCommand(new YanSucksCommand());
        addSubCommand(new OpenTestMenuCommand());
        addSubCommand(new OpenPlayerMenuCommand());
        addSubCommand(new ScoreboardCommand());
        addSubCommand(new ClearInventoryCommand());
        addSubCommand(new FireworkCommand());
        addSubCommand(new ResetPlayerCommand());
    }
}
