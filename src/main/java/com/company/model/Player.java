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
    public Board getAttackBoard(String playerName) {
        return attackBoards.get(playerName);
    }

    //Other
    public boolean attackPlayer(Player player, int x, int y) {
        boolean hit = player.getBoard().attacked(x,y);
        int placeValue = player.getBoard().getPlaceValue(x,y);

        if (hit) {
            player.getBoard().setPlace(x,y, placeValue + 10);
            attackBoards.get(player.getName()).setPlace(x,y, placeValue + 10);
            System.out.println(boatDestroyed(x,y, placeValue, player));
            // boatDestroyed(x,y, placeValue);
            return true;
        } else {
            attackBoards.get(player.getName()).setPlace(x,y, 1);
            return false;
        }
    }

    public void placeBoat(int x, int y, Boat boat, Direction direction) {
        for (int i = 0; i < boat.getValue(); i++) {
            getBoard().setPlace(x,y,boat.getId());
            switch (direction) {
                case UP:
                    y--;
                    break;
                case DOWN:
                    y++;
                    break;
                case LEFT:
                    x--;
                    break;
                case RIGHT:
                    x++;
                    break;
            }

        }
    }

    public boolean boatDestroyed(int x, int y, int id, Player player) {
        Boat boat = null;
        boolean end = false;
        int tempX, tempY;
        int hit = 0;

        for (Boat boat1 : Boat.values()) {
            if (boat1.getId() == id) {
                boat = boat1;
                break;
            }
        }

        for (Direction direction : Direction.values()) {
            tempX = x;
            tempY = y;
            while (!end) {
                if (hit == boat.getValue()) {
                    return true;
                } else if (tempX < 0 || tempY < 0 || tempX > Board.getDefaultSize() || tempY > Board.getDefaultSize()) {    // TODO: change default size to somehing that is better. Test length of x and y and not use default size.
                    break;
                } else if (attackBoards.get(player.getName()).getPlaceValue(tempX,tempY) == 0) {
                    break;
                } else if (attackBoards.get(player.getName()).getPlaceValue(tempX,tempY) == boat.getId() + 10) {
                    hit++;
                    tempX += direction.getX();
                    tempY += direction.getY();
                }
            }
        }

        return false;
    }
}
