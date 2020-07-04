package com.company.model;

public enum Boat {
    CARRIER(5, 10),
    BATTLESHIP(4, 11),
    CRUISER(3 ,12),
    SUBMARINE(3, 13),
    DESTROYER(2, 14);

    private final int length;
    private final int id;

    Boat(int length, int id) {
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
    public static Boat idToBoat(int id) {
        switch (id) {
            case 10: return CARRIER;
            case 11: return BATTLESHIP;
            case 12: return CRUISER;
            case 13: return SUBMARINE;
            case 14: return DESTROYER;
            default: return null;
        }
    }
}
