package me.ollie.games.commands.test;

import me.ollie.games.commands.AbstractCommand;
import me.ollie.games.util.potion.Potion;

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
        addSubCommand(new KitsCommand());
        addSubCommand(new DrybonyValleyCommand());
        addSubCommand(new TestSpawnLocations());
        addSubCommand(new AddLeaderboardScoreCommand());
        addSubCommand(new PotionsCommand());
    }
}
