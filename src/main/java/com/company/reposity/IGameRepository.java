package com.company.reposity;

import com.company.model.lobby.Lobby;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGameRepository {
    // Read
    ArrayList<Lobby> getAllLobbiesArray();
    HashMap<String, Lobby> getAllLobbiesHash();

    Lobby getLobbyName(String lobbyName);
    Lobby getLobbyID(String id);

    // Create


    // Update


    // Delete
}
