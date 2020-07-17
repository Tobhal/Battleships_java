package com.company.model.game;

public enum BoatTpes {
    CARRIER(5, 10),
    BATTLESHIP(4, 11),
    CRUISER(3 ,12),
    SUBMARINE(3, 13),
    DESTROYER(2, 14);

    private final int length;
    private final int id;

    BoatTpes(int length, int id) {
        this.length = length;
        this.id = id;
    }

    // Get
    public int getId() {
        return id;
    }
    public int getLength() {
        return length;
    }

    // Other
    public static BoatTpes idToBoat(int id) {
        switch (id) {
            case 10: case 20: return CARRIER;
            case 11: case 21: return BATTLESHIP;
            case 12: case 22: return CRUISER;
            case 13: case 23: return SUBMARINE;
            case 14: case 24: return DESTROYER;
            default: return null;
        }
    }
}
