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
    private ArrayList<Boat> boats = new ArrayList<>();
    private ArrayList<Coordinate> misses = new ArrayList<>();

    private HashMap<String, ArrayList<Coordinate>> enemyShots = new HashMap<>();
    private HashMap<String, ArrayList<Coordinate>> enemyHits = new HashMap<>();

    private int boatsAlive = 0;

    public Player(String name, Options options) {
        this.name = name;
        this.gameOptions = options;

        //updateOptions();
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
    public ArrayList<Coordinate> getMisses() {
        return misses;
    }
    public ArrayList<Boat> getBoats() {
        return boats;
    }
    public HashMap<String, ArrayList<Coordinate>> getEnemyShots() {
        return enemyShots;
    }
    public HashMap<String, ArrayList<Coordinate>> getEnemyHits() {
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

        updateOptions();
    }
    public void setMisses(ArrayList<Coordinate> misses) {
        this.misses = misses;
    }
    public void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }
    public void setEnemyShots(HashMap<String, ArrayList<Coordinate>> enemyShots) {
        this.enemyShots = enemyShots;
    }
    public void setEnemyHits(HashMap<String, ArrayList<Coordinate>> enemyHits) {
        this.enemyHits = enemyHits;
    }
    public void setBoatsAlive(int boatsAlive) {
        this.boatsAlive = boatsAlive;
    }

    // Add
    private void addMiss(Coordinate coordinate) {
        misses.add(coordinate);
    }

    // Other
    private void updateOptions() {
        System.out.println(gameOptions.getBoats().size());
        boatsAlive = gameOptions.getBoats().size();
        System.out.println(boatsAlive);
    }

    // - Place
    public boolean canPlaceBoat(Coordinate coordinate, BoatType boatType, Direction direction) {
        if (boatIsInsideBoard(coordinate, boatType, direction)) {
            if (boats.size() == 0) {
                return true;
            }

            ArrayList<Coordinate> tempBoatPlace = new ArrayList<>(Boat.tempBoatPlace(coordinate, boatType, direction));

            for (Coordinate coordinate1 : tempBoatPlace) {
                for (Boat boat : boats) {
                    if (boat.getPlacesTaken().contains(coordinate1)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean placeBoat(Coordinate coordinate, BoatType boatType, Direction direction) {
        if (boatIsInsideBoard(coordinate, boatType, direction)) {
            boats.add(new Boat(coordinate, boatType, direction));
            boatsAlive++;
            return true;
        } else {
            // Cant place Boat there
            return false;
        }
    }

    public boolean boatIsInsideBoard(Coordinate coordinate, BoatType boatType, Direction direction) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        x += (direction.getX() * boatType.getLength());
        y += (direction.getY() * boatType.getLength());

        if (x >= 0 && x < gameOptions.getBoardX())
            if (y >= 0 && y < gameOptions.getBoardY())
                if (coordinate.getX() >= 0 && coordinate.getX() < gameOptions.getBoardX())
                    if (coordinate.getY() >= 0 && coordinate.getY() < gameOptions.getBoardY())
                        return true;


        // (x >= 0 || gameOptions.getBoardX() > x) || (y >= 0 || gameOptions.getBoardY() > y)
        return false;
    }

    public boolean boatDestroyed(Coordinate coordinate) {
        for (Boat boat : boats) {
            if (boat.getPlacesTaken().contains(coordinate) && boat.isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    // - Attack
    public boolean attackPlayer(Coordinate coordinate, Player attackPlayer) {
        if (!enemyShots.containsKey(attackPlayer.getName())) {  // Adds key and empty value to hashmaps, if they are not already added for that player
            enemyShots.put(attackPlayer.getName(), new ArrayList<>());
            enemyHits.put(attackPlayer.getName(), new ArrayList<>());
        }
        ArrayList<Coordinate> shotList = enemyShots.get(attackPlayer.getName());
        ArrayList<Coordinate> hitList = enemyHits.get(attackPlayer.getName());

        shotList.add(coordinate);

        if (attackPlayer.hitBoat(coordinate)) {
            hitList.add(coordinate);
            return true;
        } else {
            return false;
        }
    }

    public boolean hitBoat(Coordinate coordinate) {
        for (Boat boat : boats) {
            if (boat.getPlacesTaken().contains(coordinate)) { // Hits a boat
                boat.addDamage(coordinate);

                if (boat.isDestroyed()) {
                    boatsAlive--;

                    if (boatsAlive == 0) {
                        status = PlayerStatus.OUT;
                    }
                }

                return true;
            } else {
                addMiss(coordinate);
            }
        }

        return false;
    }

    public boolean canShoot(Coordinate coordinate, Player player) {
        if (enemyShots.isEmpty()) {
            return true;
        } else {
            ArrayList<Coordinate> shots = new ArrayList<>(enemyShots.get(player.getName()));

            if (shots.contains(coordinate)) {
                return false;
            } else {
                return (coordinate.getX() < gameOptions.getBoardX() && coordinate.getX() >= 0) && (coordinate.getY() < gameOptions.getBoardY() && coordinate.getY() >= 0);
            }
        }
    }

    // Print
    private String[][] generateEmptyBoard() {
        String[][] board = new String[gameOptions.getBoardY()][gameOptions.getBoardX()];

        for (int i = 0; i < gameOptions.getBoardY(); i++) {
            for (int l = 0; l < gameOptions.getBoardX(); l++) {
                board[i][l] = "00";
            }
        }

        return board;
    }

    private void printBoard(String[][] board) {
        // Print the board
        int index = gameOptions.getBoardY() - 1;
        for (int i = gameOptions.getBoardY() - 1; i >= 0; i--) {
            System.out.print(index-- + " | ");
            for (int l = 0; l < gameOptions.getBoardX(); l++) {
                System.out.print(board[i][l] + " ");
            }
            System.out.print("\n");
        }

        // Fill with bottom numbers
        System.out.print("   ");
        for (int i = 0; i < gameOptions.getBoardX(); i++) {
            System.out.print(" --");
        }

        System.out.println();
        index = 0;
        System.out.print("   ");
        for (int i = 0; i < gameOptions.getBoardX(); i++) {
            System.out.print(" " + index++ + " ");
        }
    }

    public void printPrivateBoard() {
        String[][] board = generateEmptyBoard();

        // Place misses
        for (Coordinate miss : misses) {
            board[miss.getY()][miss.getX()] = "MM";
        }

        // Place all boats
        int index = 1;
        for (Boat boat : boats) {
            if (boat.isDestroyed()) {
                for (Coordinate coordinate : boat.getPlacesTaken()) {
                    board[coordinate.getY()][coordinate.getX()] = "D" + index;
                }
            } else {
                for (Coordinate coordinate : boat.getPlacesTaken()) {
                    board[coordinate.getY()][coordinate.getX()] = "A" + index;
                }

                for (Coordinate coordinate : boat.getPlacesDamage()) {
                    board[coordinate.getY()][coordinate.getX()] = "H" + index;
                }
            }
            index++;
        }

        printBoard(board);
    }

    public void printAttackBoard(String playerString) {
        String[][] board = generateEmptyBoard();
        ArrayList<Coordinate> shots = enemyShots.get(playerString);
        ArrayList<Coordinate> hits = enemyHits.get(playerString);

        // Place shots
        for (Coordinate coordinate : shots) {
            board[coordinate.getY()][coordinate.getX()] = "MM";
        }

        for (Coordinate coordinate : hits) {
            board[coordinate.getY()][coordinate.getX()] = "HH";
        }

        printBoard(board);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name= '" + name + '\'' +
                "\n\tid= " + id.toString() +
                "\n\tstatus= " + status +
                "\n\tgameOptions= " + gameOptions +
                "\n\tboats= " + boats +
                "\n\tenemyShots= " + enemyShots +
                "\n\tenemyHits= " + enemyHits +
                "\n\tboatsAlive= " + boatsAlive +
                '}';
    }
}
