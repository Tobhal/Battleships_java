package com.company.model.lobby;

import com.company.model.game.player.Player;

import java.util.HashMap;
import java.util.UUID;

public class Lobby {
    private String name;
    private String password;    // Probably not going to use for a while, but i want to remember to have it here...
    private UUID id = UUID.randomUUID();
    private LobbyStatus status = LobbyStatus.LOBBY;
    private Options options = new Options();

    private HashMap<String, Player> players = new HashMap<>();

    public Lobby() {

    }
    public Lobby(String name) {
        this.name = name;
    }
    public Lobby(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Get
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public UUID getId() {
        return id;
    }
    public LobbyStatus getStatus() {
        return status;
    }
    public Options getOptions() {
        return options;
    }
    public HashMap<String, Player> getPlayers() {
        return players;
    }

    // Set
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setStatus(LobbyStatus status) {
        this.status = status;
    }
    public void setOptions(Options options) {
        this.options = options;
    }
    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    // Other
    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", options=" + options +
                ", players=" + players +
                '}';
    }
}
