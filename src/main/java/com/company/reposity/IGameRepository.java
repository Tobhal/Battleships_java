package com.company.reposity;

import com.company.model.game.player.Player;
import com.company.model.lobby.Lobby;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGameRepository {
    // Read
    ArrayList<Lobby> getAllLobbiesArray();
    HashMap<String, Lobby> getAllLobbiesHash();

    Lobby getLobby(String id);

    Player getPlayer(String lobbyID, String playerID);

    // Create


    // Update


    // Delete
}
