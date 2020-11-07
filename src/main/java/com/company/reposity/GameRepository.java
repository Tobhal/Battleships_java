package com.company.reposity;

import com.company.model.lobby.Lobby;
import com.company.model.file.RWFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class GameRepository implements IGameRepository {
    HashMap<String, Lobby> lobbies = new HashMap<>();

    public GameRepository(){
        RWFile.read("JSON/Game.json", lobbies);
    }

    /*
    Read
     */
    @Override
    public ArrayList<Lobby> getAllLobbiesArray() {
        ArrayList<Lobby> lobbiesArrayList = new ArrayList<>(lobbies.values());
        lobbiesArrayList.sort(Comparator.comparing(Lobby::getName));    // Sorting the ArrayList based on the lobby name

        return lobbiesArrayList;
    }

    @Override
    public HashMap<String, Lobby> getAllLobbiesHash() {
        return lobbies;
    }

    @Override
    public Lobby getLobbyName(String lobbyName) {
        for (Lobby lobby : lobbies.values())
            if (lobby.getName().equals(lobbyName))
                return lobby;

        return null;
    }

    @Override
    public Lobby getLobbyID(String id) {
        for (Lobby lobby : lobbies.values())
            if (lobby.getId().toString().equals(id))
                return lobby;

        return null;
    }



    /*
    Create
     */


    /*
    Update
     */



    /*
    Delete
     */

}
