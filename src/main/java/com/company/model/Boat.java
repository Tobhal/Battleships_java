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
}
