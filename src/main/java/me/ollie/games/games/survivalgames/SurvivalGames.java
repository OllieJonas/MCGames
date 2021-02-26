package me.ollie.games.games.survivalgames;

import me.ollie.games.games.AbstractGame;

public class SurvivalGames extends AbstractGame {

    enum Phase {
        COUNTDOWN,
        GRACE_PERIOD,
        GAME,
        DEATHMATCH,
        END;
    }

    public SurvivalGames() {
        super("Survival Games");
    }

    @Override
    public void load() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }
}
