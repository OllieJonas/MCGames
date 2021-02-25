package me.ollie.games.commands;

import me.ollie.games.commands.test.YanSucksCommand;

public class GameCommand extends AbstractCommand {

    @Override
    public void addSubCommands() {
        addSubCommand(new YanSucksCommand());
    }
}
