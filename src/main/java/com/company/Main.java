package com.company;


import com.company.model.game.Boat;
import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.game.player.Player;
import com.company.model.lobby.Lobby;
import com.company.model.lobby.Options;

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




        Boat boat1 = new Boat(new Coordinate(5, 5), BoatType.CARRIER, Direction.RIGHT);
        boat1.addDamage(new Coordinate(6,5));
        System.out.println(boat1);
    }
}

/*

 */