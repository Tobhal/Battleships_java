package com.company;


import com.company.model.game.Boat;
import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.game.player.Bot;
import com.company.model.game.player.Player;
import com.company.model.lobby.Lobby;
import com.company.model.lobby.Options;

import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        // Initialize lobby
        //Lobby lobby1 = new Lobby("Lobby 1");

        // Configure options
        //Options options = new Options();

        // Add players
        //Player player1 = new Player("Player 1", options);
        //lobby1.addPlayer(player1);

        // Start game



        // Players place their boats



        // Players attack



        // Game ends

        HashMap<String, Player> players = new HashMap<>();

        Options options = new Options();

        options.setBoats(new ArrayList<>() {{
            add(BoatType.CARRIER);
            add(BoatType.BATTLESHIP);
            add(BoatType.CRUISER);
            add(BoatType.SUBMARINE);
            add(BoatType.DESTROYER);
        }});

        players.put("Player 1", new Player("Player 1", options));
        players.put("Player 2", new Player("Player 2", options));

        for (Player player : players.values()) {
            System.out.println(player.boatIsInsideBoard(new Coordinate(0,0), options.getBoats().get(0), Direction.UP));

            player.placeBoat(new Coordinate(0, 0), options.getBoats().get(0), Direction.UP);
            player.placeBoat(new Coordinate(1, 0), options.getBoats().get(1), Direction.UP);
            player.placeBoat(new Coordinate(2, 0), options.getBoats().get(2), Direction.UP);
            player.placeBoat(new Coordinate(3, 0), options.getBoats().get(3), Direction.UP);
            player.placeBoat(new Coordinate(4, 0), options.getBoats().get(4), Direction.UP);
        }

        for (Player player : players.values()) {
            System.out.println();
            System.out.println(player);
        }

        //players.get("Player 1").printBoard();

        players.get("Player 2").attackPlayer(new Coordinate(4,0), players.get("Player 1"));
        players.get("Player 2").attackPlayer(new Coordinate(4,1), players.get("Player 1"));
        players.get("Player 2").attackPlayer(new Coordinate(3,3), players.get("Player 1"));
        players.get("Player 2").attackPlayer(new Coordinate(3,2), players.get("Player 1"));
        players.get("Player 2").attackPlayer(new Coordinate(3,1), players.get("Player 1"));

        players.get("Player 1").printPrivateBoard();
        System.out.println();
        System.out.println();
        players.get("Player 2").printAttackBoard("Player 1");

        /*
        Boat boat1 = new Boat(new Coordinate(5, 5), BoatType.CARRIER, Direction.RIGHT);
        boat1.addDamage(new Coordinate(6,5));
        System.out.println(boat1);

        System.out.println();

        Player player1 = new Player("Player 1", new Options());
        System.out.println(player1);
         */
    }
}

/*

 */