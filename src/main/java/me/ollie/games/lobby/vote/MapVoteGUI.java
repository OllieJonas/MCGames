package me.ollie.games.lobby.vote;

import me.ollie.games.gui.GUI;

public class MapVoteGUI {

    private GUI gui;

    public MapVoteGUI() {

        initGui();
    }

    private void initGui() {
        this.gui = new GUI("Map Voting", 54, true) {
            @Override
            public void addItems() {

            }
        };
    }

    public void update() {

    }
}
