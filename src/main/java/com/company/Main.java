package com.company;

import com.company.model.Board;
import com.company.model.Player;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Player> players = new HashMap<>();

        Board.setDefaultSize(5);

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

        players.get("player 1").getBoard().setPlace(1,1,1);
        System.out.println(player1.getBoard().toString());

        System.out.println(player2.attackPlayer(player1, 1,1));

        System.out.println(player1.getBoard().toString());

    }
}
