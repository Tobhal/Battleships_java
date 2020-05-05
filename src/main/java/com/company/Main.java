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
        player1.getBoard().print();

        System.out.println(player2.attackPlayer(player1, 1,3));
        System.out.println(player2.attackPlayer(player1, 1,2));

        player1.getBoard().print();

        System.out.println("------------------------------");

        player3.placeBoat(1,1, Boat.DESTROYER, Direction.DOWN);
        player3.getBoard().print();

        player4.attackPlayer(player3, 1,1);
        player4.attackPlayer(player3, 2,1);

        player3.getBoard().print();

        System.out.println("------------------------------");

        player4.getAttackBoard(player3.getName()).print();




    }
}
