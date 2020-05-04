package com.company;

import com.company.model.Board;
import com.company.model.Player;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1", new Board());
        Player player2 = new Player("Player 2", new Board());
        Player player3 = new Player("Player 3", new Board());
        Player player4 = new Player("Player 4", new Board());

        player1.getBoard().setPlace(1,1,1);
        System.out.println(player1.getBoard().toString());


    }
}
