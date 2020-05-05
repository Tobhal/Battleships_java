package com.company.model;

import java.util.HashMap;

public class Player {
    private String name;
    private Board personalBoard;
    private HashMap<String, Board> attackBoards = new HashMap<>();

    public Player() {

    }
    public Player(String name) {
        this.name = name;
    }
    public Player(String name, Board personalBoard) {
        this.name = name;
        this.personalBoard = personalBoard;
    }
    public Player(String name, Board personalBoard, HashMap<String, Board> attackBoards) {
        this.name = name;
        this.personalBoard = personalBoard;
        this.attackBoards = attackBoards;
    }


    //Set
    public void addAttackBoard(Player player) {
        this.attackBoards.put(player.getName(), new Board());
    }

    //Get
    public String getName() {
        return this.name;
    }
    public Board getBoard() {
        return personalBoard;
    }

    //Other
    public boolean attackPlayer(Player player, int x, int y) {
        boolean hit = player.getBoard().attaked(x,y);

        if (hit) {
            player.getBoard().setPlace(x, y, 2);
            attackBoards.get(player.getName()).setPlace(x,y,2);
            return true;
        } else {
            attackBoards.get(player.getName()).setPlace(x,y,3);
            return false;
        }
    }
}
