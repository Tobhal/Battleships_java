package com.company.model.lobby;

import com.company.model.game.BoatTpes;
import com.company.model.game.Direction;

import java.util.ArrayList;

public class Options {
    private int boardX = 10;
    private int boardY = 10;
    private ArrayList<BoatTpes> boats = new ArrayList<>() {{
        add(BoatTpes.CARRIER);
        add(BoatTpes.BATTLESHIP);
        add(BoatTpes.SUBMARINE);
        add(BoatTpes.CRUISER);
        add(BoatTpes.DESTROYER);
    }};
    private ArrayList<Direction> directions = new ArrayList<>() {{
        add(Direction.UP);
        add(Direction.DOWN);
        add(Direction.LEFT);
        add(Direction.RIGHT);
    }};

    public Options() {}
}
