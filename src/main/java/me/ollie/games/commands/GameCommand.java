package me.ollie.games.commands;

import me.ollie.games.commands.test.YanSucksCommand;

public class GameCommand extends AbstractCommand {

    @Override
    public void addSubCommands() {
        // testing
        addSubCommand(new YanSucksCommand());
        // admin
        addSubCommand(new CreateLobbyCommand());
        addSubCommand(new ForceStartCommand());

        // public
        addSubCommand(new ListCommand());
        addSubCommand(new VoteCommand());
        addSubCommand(new LeaveCommand());
    }
}
