package com.company.model;

public enum Boat {
    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);

    public final int length;

    private Boat(int length) {
        this.length = length;
    }
}
