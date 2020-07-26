package com.company.model.game;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {

    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Get
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Set
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    // Other

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return (this.x == ((Coordinate) o).getX()) && (this.y == ((Coordinate) o).getY());
    }
}