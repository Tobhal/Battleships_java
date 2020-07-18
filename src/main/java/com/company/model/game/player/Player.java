package com.company.model.game.player;

import com.company.model.game.Boat;
import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.lobby.Options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Player {
    private String name;
    private UUID id = UUID.randomUUID();
    private PlayerStatus status = PlayerStatus.WAITING;

    private Options gameOptions;
    private ArrayList<Boat> boats;

    private HashMap<String, Player> enemyShots;
    private HashMap<String, Player> enemyHits;

    private int boatsAlive;

    public Player(String name, Options options) {
        this.name = name;
        this.gameOptions = options;
    }

    // Get
    public String getName() {
        return name;
    }
    public UUID getId() {
        return id;
    }
    public PlayerStatus getStatus() {
        return status;
    }
    public Options getGameOptions() {
        return gameOptions;
    }
    public ArrayList<Boat> getBoats() {
        return boats;
    }
    public HashMap<String, Player> getEnemyShots() {
        return enemyShots;
    }
    public HashMap<String, Player> getEnemyHits() {
        return enemyHits;
    }
    public int getBoatsAlive() {
        return boatsAlive;
    }

    // Set
    public void setName(String name) {
        this.name = name;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
    public void setGameOptions(Options gameOptions) {
        this.gameOptions = gameOptions;
    }
    public void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }
    public void setEnemyShots(HashMap<String, Player> enemyShots) {
        this.enemyShots = enemyShots;
    }
    public void setEnemyHits(HashMap<String, Player> enemyHits) {
        this.enemyHits = enemyHits;
    }
    public void setBoatsAlive(int boatsAlive) {
        this.boatsAlive = boatsAlive;
    }

    // Other
    public void placeBoat(Coordinate coordinate, BoatType boatType, Direction direction) {
        if (boatIsInsideBoard(coordinate, boatType, direction)) {
            // Place boat
        } else {
            // Cant place Boat there
        }
    }

    public boolean boatIsInsideBoard(Coordinate coordinate, BoatType boatType, Direction direction) {
        int x = coordinate.getX();
        int y = coordinate.getX();

        x += (direction.getX() * boatType.getLength());
        y += (direction.getY() * boatType.getLength());

        return (x <= 0 || gameOptions.getBoardX() >= x) || (y <= 0 || gameOptions.getBoardY() >= y);
    }

}
