package com.company.model;

import java.util.HashMap;
import java.util.List;

public class Player {
    private String name;
    private Board personalBoard;
    private int numberOfBoatsAlive = 0;
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
    public void setName(String name) {
        this.name = name;
    }
    public void setPersonalBoard(Board personalBoard) {
        this.personalBoard = personalBoard;
    }
    public void setAttackBoards(HashMap<String, Board> attackBoards) {
        this.attackBoards = attackBoards;
    }
    public void addAttackBoard(Player player) {
        this.attackBoards.put(player.getName(), new Board());
    }
    public void setNumberOfBoatsAlive(int numberOfBoatsAlive) {
        this.numberOfBoatsAlive = numberOfBoatsAlive;
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
    public int getNumberOfBoatsAlive() {
        return numberOfBoatsAlive;
    }

    //Other
    public boolean attackPlayer(Player attackedPlayer, int x, int y) {
        boolean hit = attackedPlayer.getBoard().attacked(x,y);
        int placeValue = attackedPlayer.getBoard().getPlaceValue(x,y);

        if (hit) {
            placeValue += 10;
            attackedPlayer.getBoard().setPlace(x,y, placeValue);
            attackBoards.get(attackedPlayer.getName()).setPlace(x,y, placeValue);
            System.out.println("Hit");
            System.out.println(boatDestroyed(x,y, placeValue - 10, attackedPlayer));
            // boatDestroyed(x,y, placeValue);
            return true;
        } else {
            attackBoards.get(attackedPlayer.getName()).setPlace(x,y, 1);
            System.out.println("Miss");
            return false;
        }
    }

    public void placeBoat(int x, int y, Boat boat, Direction direction) {
        numberOfBoatsAlive += 1;

        if (personalBoard.boatIsOutside(x, y, boat, direction)) {
            for (int i = 0; i < boat.getLength(); i++) {
                personalBoard.setPlace(x,y,boat.getId());
                x += direction.getX();
                y += direction.getY();
            }
        } else {
            // Boat is outside of the board. Do something to fix it.
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
            } else if (boat1.getId() == id - 10) {
                boat = boat1;
                break;
            }
        }

        for (Direction direction : Direction.values()) {
            tempX = x + direction.getX();
            tempY = y + direction.getY();
            end = false;
            while (!end) {
                if (hit >= boat.getLength() - 1) {   // If the boat is destroyed
                    player.removeBoat(1);
                    return true;
                } else if (tempX < 0 || tempY < 0 || tempX > Board.getDefaultX() - 1 || tempY > Board.getDefaultY() - 1) {    // If checking outside of the board    // TODO: change default size to somehing that is better. Test length of x and y and not use default size.
                    end = true;
                } else if (attackBoards.get(player.getName()).getPlaceValue(tempX,tempY) == 0) {    // if the place is 0 (empty place)
                    end = true;
                } else if (attackBoards.get(player.getName()).getPlaceValue(tempX,tempY) == 1) {    // if the place is 1 (Already hit place)
                    end = true;
                } else if (attackBoards.get(player.getName()).getPlaceValue(tempX,tempY) == boat.getId() + 10) {    // If the place is the same hit boat
                    hit++;
                    tempX += direction.getX();
                    tempY += direction.getY();
                } else {
                    end = true;
                }
            }
        }

        return false;
    }

    public boolean noBoatsLeft() {
        return numberOfBoatsAlive == 0;
    }

    public void removeBoat(int numberOfBoats) {
        numberOfBoatsAlive -= numberOfBoats;
    }

    public void printAttackBoard(String player) {
        attackBoards.get(player).print();
    }
}
