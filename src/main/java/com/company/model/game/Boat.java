package com.company.model.game;

import java.util.ArrayList;

public class Boat {
    private BoatTpes type;
    private ArrayList<Coordinate> placesTaken;
    private ArrayList<Coordinate> placesDamage = new ArrayList<>();
    private boolean destroyed = false;

    public Boat(BoatTpes type, ArrayList<Coordinate> placesTaken) {
        this.type = type;
        this.placesTaken = placesTaken;
    }
}
