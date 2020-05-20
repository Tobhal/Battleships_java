package com.company;

import com.company.model.Board;
import com.company.model.Boat;
import com.company.model.Direction;
import com.company.model.Player;

import java.security.UnresolvedPermission;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Player> players = new HashMap<>();

        Board.setDefaultSize(10);

        // Player setup
        Player player1 = new Player("player 1", new Board());
        Player player2 = new Player("player 2", new Board());
        Player player3 = new Player("player 3", new Board());
        Player player4 = new Player("player 4", new Board());

        players.put(player1.getName(), player1);
        players.put(player2.getName(), player2);
        players.put(player3.getName(), player3);
        players.put(player4.getName(), player4);

        for (Player player : players.values()) {
            for (Player otherPlayer : players.values()) {
                if (!player.getName().equals(otherPlayer.getName())) {
                    player.addAttackBoard(otherPlayer);
                }
            }
        }

        // "Game"
        for (Player player : players.values()) {
            // System.out.println(player.getName());
            player.placeBoat(0,5, Boat.CARRIER, Direction.RIGHT);
            player.placeBoat(1,0, Boat.BATTLESHIP, Direction.DOWN);
            player.placeBoat(2,0, Boat.CRUISER, Direction.DOWN);
            player.placeBoat(3,0, Boat.SUBMARINE, Direction.DOWN);
            player.placeBoat(4,0, Boat.DESTROYER, Direction.DOWN);

            // player.getBoard().print();
        }

        // False
        System.out.println("False:");
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.UP));
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.LEFT));
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.UP_LEFT));
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.UP_RIGHT));
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.DOWN_LEFT));

        System.out.println("\nTrue:");
        // True
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.DOWN));
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.RIGHT));
        System.out.println(player1.getBoard().boatIsOutside(0,0, Boat.CARRIER, Direction.DOWN_RIGHT));

        System.out.println("----****----");

        System.out.println("Player 2 board:");
        player2.getBoard().print();

        System.out.println("\nBoat destroyed?");
        player1.attackPlayer(player2,4,0);
        player1.attackPlayer(player2,4,1);

        player1.attackPlayer(player2,0,0);
        player1.attackPlayer(player2,0,1);
        player1.attackPlayer(player2,0,2);
        player1.attackPlayer(player2,0,3);
        player1.attackPlayer(player2,0,4);

        player1.attackPlayer(player2,0,5);
        player1.attackPlayer(player2,1,5);
        player1.attackPlayer(player2,2,5);
        player1.attackPlayer(player2,3,5);
        player1.attackPlayer(player2,4,5);
        player1.attackPlayer(player2,5,5);


        System.out.println("\nTest if correct");
        player1.getAttackBoard("player 2").print();
    }
}

/*

 */