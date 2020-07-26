package com.company.model.game;

import java.util.ArrayList;

public class Boat {
    private BoatType type;
    private ArrayList<Coordinate> placesTaken = new ArrayList<>();
    private ArrayList<Coordinate> placesDamage = new ArrayList<>();
    private boolean destroyed = false;

    public Boat(BoatType type, ArrayList<Coordinate> placesTaken) {
        this.type = type;
        this.placesTaken = placesTaken;
    }
    public Boat(Coordinate startCoordinate, BoatType type, Direction direction) {
        this.type = type;

        for (int i = 0; i < type.getLength(); i++) {
            placesTaken.add(new Coordinate(
                    (startCoordinate.getX() + (direction.getX() * i)),
                    (startCoordinate.getY() + (direction.getY() * i))
            ));
        }
    }

    // Get
    public BoatType getType() {
        return type;
    }
    public ArrayList<Coordinate> getPlacesTaken() {
        return placesTaken;
    }
    public ArrayList<Coordinate> getPlacesDamage() {
        return placesDamage;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

    // Set
    public void setType(BoatType type) {
        this.type = type;
    }
    public void setPlacesTaken(ArrayList<Coordinate> placesTaken) {
        this.placesTaken = placesTaken;
    }
    public void setPlacesDamage(ArrayList<Coordinate> placesDamage) {
        this.placesDamage = placesDamage;
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // Other
    public boolean addDamage(Coordinate coordinate) {
        if (placesTaken.contains(coordinate)) {
            placesDamage.add(coordinate);

            if (placesDamage.size() == placesTaken.size()) {
                destroyed = true;
            }

            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Coordinate> tempBoatPlace(Coordinate coordinate, BoatType boatType, Direction direction) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        coordinates.add(coordinate);

        for (int i = 0; i < boatType.getLength(); i++) {
            coordinate.setX(coordinate.getX() + direction.getX());
            coordinate.setY(coordinate.getY() + direction.getY());
        }

        return coordinates;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "type=" + type +
                ", placesTaken=" + placesTaken +
                ", placesDamage=" + placesDamage +
                ", destroyed=" + destroyed +
                '}';
    }
}
