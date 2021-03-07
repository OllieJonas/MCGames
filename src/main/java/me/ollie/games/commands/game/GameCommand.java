package me.ollie.games.commands.game;

import me.ollie.games.commands.AbstractCommand;
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
        addSubCommand(new SpectateCommand());
    }
}
