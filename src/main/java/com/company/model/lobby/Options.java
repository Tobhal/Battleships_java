package com.company.model.lobby;

import com.company.model.game.BoatType;
import com.company.model.game.Direction;

import java.util.ArrayList;

public class Options {
    private int boardX = 10;
    private int boardY = 10;
    private ArrayList<BoatType> boats = new ArrayList<>() {{
        add(BoatType.CARRIER);
        add(BoatType.BATTLESHIP);
        add(BoatType.SUBMARINE);
        add(BoatType.CRUISER);
        add(BoatType.DESTROYER);
    }};
    private ArrayList<Direction> directions = new ArrayList<>() {{
        add(Direction.UP);
        add(Direction.DOWN);
        add(Direction.LEFT);
        add(Direction.RIGHT);
    }};

    public Options() {}

    // Get
    public int getBoardX() {
        return boardX;
    }
    public int getBoardY() {
        return boardY;
    }

    public ArrayList<BoatType> getBoats() {
        return boats;
    }
    public ArrayList<Direction> getDirections() {
        return directions;
    }

    // Set
    public void setBoardX(int boardX) {
        this.boardX = boardX;
    }
    public void setBoardY(int boardY) {
        this.boardY = boardY;
    }

    public void setBoats(ArrayList<BoatType> boats) {
        this.boats = boats;
    }
    public void setDirections(ArrayList<Direction> directions) {
        this.directions = directions;
    }

    // Other

}
