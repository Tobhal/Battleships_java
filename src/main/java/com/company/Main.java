package com.company;

import com.company.model.Board;
import com.company.model.Boat;
import com.company.model.Direction;
import com.company.model.Player;

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
            System.out.println(player.getName());
            player.placeBoat(0,0, Boat.CARRIER, Direction.DOWN);
            player.placeBoat(1,0, Boat.BATTLESHIP, Direction.DOWN);
            player.placeBoat(2,0, Boat.CRUISER, Direction.DOWN);
            player.placeBoat(3,0, Boat.SUBMARINE, Direction.DOWN);
            player.placeBoat(4,0, Boat.DESTROYER, Direction.DOWN);

            player.getBoard().print();
        }



        System.out.println("attack part");

        player1.getAttackBoard("player 2").print();

        player1.attackPlayer(player2,4,0);
        player1.attackPlayer(player2,4,1);

        System.out.println("Get board");

        player1.getAttackBoard("player 2").print();



    }
}
