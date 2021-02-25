package me.ollie.games.lobby;

import lombok.Getter;

import java.util.Set;

public class LobbyManager {

    private Set<Lobby> lobbies;

    @Getter
    private static LobbyManager INSTANCE = new LobbyManager();
}
