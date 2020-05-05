package com.company.model;

public enum Boat {
    CARRIER(5, 10),
    BATTLESHIP(4, 11),
    CRUISER(3 ,12),
    SUBMARINE(3, 13),
    DESTROYER(2, 14);

    private final int value;
    private final int id;

    Boat(int value, int id) {
        this.value = value;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }
}
