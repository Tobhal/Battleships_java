package com.company.model.lobby;

import com.company.model.game.player.Player;

import java.util.HashMap;
import java.util.UUID;

public class Lobby {
    private String name;
    private String password;    // Probably not going to use for a while, but i want to remember to have it here...
    private UUID id = UUID.randomUUID();
    private LobbyStatus status = LobbyStatus.LOBBY;
    private Options options;

    private HashMap<String, Player> players;

    public Lobby(String name) {
        this.name = name;
    }
    public Lobby(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Get


    // Set


    // Other
    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }
}
