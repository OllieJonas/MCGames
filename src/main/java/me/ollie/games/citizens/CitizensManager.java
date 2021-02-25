package me.ollie.games.citizens;

import lombok.Getter;

public class CitizensManager {

    @Getter
    private static final CitizensManager instance = new CitizensManager();

    public CitizensManager() {

    }


}
