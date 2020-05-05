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
        int placeValue;

        if (hit) {
            player.getBoard().setPlace(x, y, 2);
            attackBoards.get(player.getName()).setPlace(x,y,player.getBoard().getPlaceValue(x, y) + 10);
            placeValue = player.getBoard().getPlaceValue(x, y);
            return true;
        } else {
            attackBoards.get(player.getName()).setPlace(x,y,3);
            return false;
        }


    }

    public void placeBoat(int x, int y, Boat boat, Direction direction) {
        for (int i = 0; i < boat.getValue(); i++) {
            getBoard().setPlace(x,y,boat.getId());
            switch (direction) {
                case UP:
                    x--;
                    break;
                case DOWN:
                    x++;
                    break;
                case LEFT:
                    y--;
                    break;
                case RIGHT:
                    y++;
                    break;
            }

        }
    }

    public boolean boatDestroyed(int x, int y, int id) {
        Boat boat = null;
        boolean end = false;
        int tempX, tempY, hit;

        for (Boat boat1 : Boat.values()) {
            if (boat1.getId() == id) {
                boat = boat1;
                break;
            }
        }

        for (Direction direction : Direction.values()) {
            hit = 0;
            tempX = x;
            tempY = y;
            while (!end) {
                if (getBoard().getPlaceValue(tempX + direction.getX(), tempY + direction.getY()) == boat.getId()) {
                    if (hit == boat.getValue()) {
                        end = true;
                    } else {
                        tempX++;
                        tempY++;
                    }
                }
            }

            if (end)
                return true;
        }
        return false;
    }
}
