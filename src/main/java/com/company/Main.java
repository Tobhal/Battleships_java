package com.company;


import com.company.model.game.Boat;
import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.game.player.Player;
import com.company.model.lobby.Lobby;
import com.company.model.lobby.Options;

import java.awt.desktop.SystemSleepEvent;
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

        players.put("Player 1", new Player("Player 1", options));
        players.put("Player 2", new Player("Player 2", options));

        for (Player player : players.values()) {
            player.placeBoat(new Coordinate(0, 0), options.getBoats().get(0), Direction.RIGHT);
            player.placeBoat(new Coordinate(0, 1), options.getBoats().get(1), Direction.RIGHT);
            player.placeBoat(new Coordinate(0, 2), options.getBoats().get(2), Direction.RIGHT);
            player.placeBoat(new Coordinate(0, 3), options.getBoats().get(3), Direction.RIGHT);
            player.placeBoat(new Coordinate(0 ,4), options.getBoats().get(4), Direction.RIGHT);
        }

        for (Player player : players.values()) {
            System.out.println();
            System.out.println(player);
        }

        players.get("Player 1").printBoard();
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