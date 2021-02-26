package me.ollie.games.lobby.vote;

import lombok.Getter;
import me.ollie.games.gui.GUI;

public class MapVoteGUI {

    @Getter
    private GUI gui;

    public MapVoteGUI() {

        initGui();
    }

    private void initGui() {
        this.gui = new GUI("Map Voting", 54) {
            @Override
            public void addItems() {

            }
        };
    }

    public void update() {

    }
}
