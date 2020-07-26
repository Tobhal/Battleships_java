package com.company.model.game;

import com.company.model.lobby.Options;

public enum BoatType {
    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);

    private final int length;

    BoatType(int length) {
        this.length = length;
    }

    // Get
    public int getLength() {
        return length;
    }

    // Other
}
