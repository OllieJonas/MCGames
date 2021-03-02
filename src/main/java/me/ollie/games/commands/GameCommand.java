package me.ollie.games.commands;

import me.ollie.games.commands.test.ResetPlayerCommand;
import me.ollie.games.commands.test.YanSucksCommand;

public class GameCommand extends AbstractCommand {

    @Override
    public void addSubCommands() {
        // testing
        addSubCommand(new YanSucksCommand());
        addSubCommand(new GameEndCommand());

        // admin
        addSubCommand(new CreateLobbyCommand());
        addSubCommand(new ForceStartCommand());
        addSubCommand(new ResetPlayerCommand());

        // public
        addSubCommand(new ListCommand());
        addSubCommand(new VoteCommand());
        addSubCommand(new LeaveCommand());
    }
}
