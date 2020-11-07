package com.company.model.game.player;

import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.lobby.Options;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player {

    Player lastPlayerAttacked;          //Last player
    Direction lastBoatHitDirection;     //The direction the boat is going
    Coordinate lastShot;
    Coordinate lastHit;
    Coordinate firstBoatHit;            //The first (x,y) that hit a boat, this so if the bot finds an end to a boat and the boat still is not destroyed it can go back and go the other way
    boolean lastShotHit;                //Last shot hit? true = yes
    boolean boatDestroyed;              //If there is a boat the bot is "working on"

    StringBuilder turnText;

    public Bot() {

    }
    public Bot(String name, Options options) {
        super(name, options);
    }

    //Get
    public Player getLastPlayerAttacked() {
        return lastPlayerAttacked;
    }

    public void placeBoat(BoatType boatType) {
        Direction direction = Direction.getRandomDirection(getGameOptions().getDirections());

        Coordinate workingCoordinate = new Coordinate(
                new Random().nextInt(getGameOptions().getBoardX()),
                new Random().nextInt(getGameOptions().getBoardY())
        );

        while (!canPlaceBoat(workingCoordinate, boatType, direction)) {
            direction = Direction.getRandomDirection(getGameOptions().getDirections());
            workingCoordinate.setX(new Random().nextInt(getGameOptions().getBoardX()));
            workingCoordinate.setY(new Random().nextInt(getGameOptions().getBoardY()));
        }

        if (!placeBoat(workingCoordinate, boatType, direction)) {
            placeBoat(boatType);
        }
    }

    public boolean attackPlayer(ArrayList<Player> players) {
        Direction direction;
        Coordinate workingCoordinate = new Coordinate(0,0);
        boatDestroyed = false;

        turnText = new StringBuilder();
        turnText.append(getName()).append("(").append(getBoatsAlive()).append(")");

        if (lastPlayerAttacked != null) { // Attack same player
            if (lastShotHit && lastBoatHitDirection != null) {  // Hit, hit direction known | try the same direction
                turnText.append(" -> ").append("case 1");
                /*  Case 1

                    Take the known direction and add the x, y to lastHitX and lastHitY.
                    Then se if that spot can be shot at.
                        Yes: Shoot there
                        No: Flip the direction.
                            Add that direction to firstHitX and firstHitY (so now the attack is the other direction)
                            Se if that spot can be attacked,
                                For now just shoot there (later think of what to do)
                 */
                workingCoordinate.setX(lastShot.getX() + lastBoatHitDirection.getX());
                workingCoordinate.setY(lastShot.getY() + lastBoatHitDirection.getY());

                if (canShoot(workingCoordinate, lastPlayerAttacked)) {   // Can shoot at the new location
                    attack(workingCoordinate, lastPlayerAttacked, lastBoatHitDirection);
                } else {    // Shoot a different direction
                    lastBoatHitDirection = Direction.flipDirection(lastBoatHitDirection);

                    workingCoordinate.setX(firstBoatHit.getX() + lastBoatHitDirection.getX());
                    workingCoordinate.setY(firstBoatHit.getY() + lastBoatHitDirection.getY());

                    attack(workingCoordinate, lastPlayerAttacked, lastBoatHitDirection);
                }
            } else if (lastShotHit == false && lastBoatHitDirection != null) {  // Miss, hit direction known | Attack in the opisit direction from lastHitDirection
                turnText.append(" -> ").append("case 2");
                /*  Case 2

                    The last shot was a miss, but i know the location.
                    This means that Case 1 has happened, and I am at 1 end of the boat.
                    I then need to go back to the start of where i hit the boat, flip direction and attack from there.

                    Flip the direction
                    Set X, Y to firstHitX and firstHitY += x y form direction.
                    Se if i can attack there
                        Yes: Attack
                        No: For now shoot there, but later I need to try to find out what to do.
                            The same problem as case 1
                 */
                // Attack
                direction = Direction.flipDirection(lastBoatHitDirection);

                workingCoordinate.setX(firstBoatHit.getX() + direction.getX());
                workingCoordinate.setY(firstBoatHit.getY() + direction.getY());

                if (canShoot(workingCoordinate, lastPlayerAttacked)) {   // Can shoot there
                    attack(workingCoordinate, lastPlayerAttacked, direction);
                    return true;
                } else {    // Cant shoot there
                    return false;
                }

            } else if (lastShotHit && lastBoatHitDirection == null) {   // Hit, hit direction not known | shoot a random direction | normaly after the first hit
                turnText.append(" -> ").append("case 3");
                /*  Case 3

                    Because I do not know the direction the boat is going, I need to try a "random" direction.

                    Make a temp list of all directions in use.
                    Take a random direction from that list.
                        Can I shoot there?
                            Yes: Attack there.
                            No: Remove that direction from the list and try a different direction

                        If no direction is found there is a bug somewhere?
                 */
                // Attack
                ArrayList<Direction> tempDirections = new ArrayList<>(getGameOptions().getDirections());

                direction = Direction.getRandomDirection(tempDirections);

                workingCoordinate.setX(lastShot.getX() + direction.getX());
                workingCoordinate.setY(lastShot.getY() + direction.getY());

                tempDirections.remove(direction);

                while (!canShoot(workingCoordinate, lastPlayerAttacked) && tempDirections.size() > 0) {
                    direction = Direction.getRandomDirection(tempDirections);

                    workingCoordinate.setX(lastShot.getX() + direction.getX());
                    workingCoordinate.setY(lastShot.getY() + direction.getY());

                    tempDirections.remove(direction);
                }

                /*
                TODO: Handle what to do if tempDirection is 0 and no valid direction is found.
                If one direction is a already hit place then se the next direction to see if that can be shot at.
                */

                if (canShoot(workingCoordinate, lastPlayerAttacked)) {   // If can shoot
                    attack(workingCoordinate, lastPlayerAttacked, direction);
                    return true;
                } else {    // Cant shoot, all directions are used up so there is a problem here...
                    return false;
                }
            }  else if (lastShotHit == false && lastBoatHitDirection == null) {  // Miss, hit direction not known | attack in the opisit direction from the firstBoatHitX and firstBoatHitY (example: was up, now down)
                turnText.append(" -> ").append("case 4");
                /*  Case 4

                    I miss and do not know the location.
                    This means that case 3 happened and i am trying to find the correct direction of the boat.
                    I need to do most of the same as case 2, but i need to use lastHitX and lastHitY and not lastShotX and lastShotY

                    Make a list of all directions in use.
                    Pick a random one.
                    See if I can shoot there.
                        Yes: Attack
                        No: Remove that from the list and try a different direction.

                        If no direction is found there is a bug somewhere...
                 */
                // Attack
                ArrayList<Direction> tempDirections = new ArrayList<>(getGameOptions().getDirections());

                direction = Direction.getRandomDirection(tempDirections);

                workingCoordinate.setX(lastHit.getX() + direction.getX());
                workingCoordinate.setY(lastHit.getY() + direction.getY());

                tempDirections.remove(direction);

                while (!canShoot(workingCoordinate, lastPlayerAttacked) && tempDirections.size() > 0) {
                    direction = Direction.getRandomDirection(tempDirections);

                    workingCoordinate.setX(lastHit.getX() + direction.getX());
                    workingCoordinate.setY(lastHit.getY() + direction.getY());

                    tempDirections.remove(direction);
                }

                if (canShoot(workingCoordinate, lastPlayerAttacked)) {   // If can shoot
                    attack(workingCoordinate, lastPlayerAttacked, direction);
                    return true;
                } else {    // Cant shoot, all directions are used up so there is a problem here...
                    direction = Direction.getRandomDirection(tempDirections);

                    workingCoordinate.setX(lastHit.getX() + direction.getX());
                    workingCoordinate.setY(lastHit.getY() + direction.getY());

                    tempDirections.remove(direction);

                    while (!canShoot(workingCoordinate, lastPlayerAttacked) && tempDirections.size() > 0) {
                        direction = Direction.getRandomDirection(tempDirections);

                        workingCoordinate.setX(lastHit.getX() + direction.getX());
                        workingCoordinate.setY(lastHit.getY() + direction.getY());

                        tempDirections.remove(direction);
                    }

                    /*
                    TODO: Handle what to do if tempDirection is 0 and no valid direction is found.
                    If one direction is a already hit place then se the next direction to see if that can be shot at.
                    */

                    attack(workingCoordinate, lastPlayerAttacked, direction);
                    return true;
                }


            } else {    // None of the above happened, so i need to write more code
                return false;
            }
        } else { // Attack a random player
            ArrayList<Player> attackPlayers = new ArrayList<>();
            for (Player player : players) {
                if (!player.getName().equals(getName())) {
                    attackPlayers.add(player);
                }
            }

            lastPlayerAttacked = attackPlayers.get(new Random().nextInt(attackPlayers.size()));

            workingCoordinate.setY(new Random().nextInt(getGameOptions().getBoardY()));
            workingCoordinate.setX(new Random().nextInt(getGameOptions().getBoardX()));

            while (!canShoot(workingCoordinate, lastPlayerAttacked)) {   // If x and y wil be at a already shot at place, try again
                workingCoordinate.setY(new Random().nextInt(getGameOptions().getBoardY()));
                workingCoordinate.setX(new Random().nextInt(getGameOptions().getBoardX()));
            }

            lastShot = workingCoordinate;

            if (attack(workingCoordinate, lastPlayerAttacked, lastBoatHitDirection)) {
                firstBoatHit = workingCoordinate;
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private boolean attack(Coordinate coordinate,Player attackPlayer, Direction direction) {
        lastShotHit = attackPlayer(coordinate, attackPlayer);
        boatDestroyed = attackPlayer.boatDestroyed(coordinate);
        lastShot = coordinate;

        /*
        System.out.println("\n" + getName() + "(" + getBoatsAlive() + ")" + " attacked -> " + attackPlayer.getName() + " at -> X:" + coordinate.getX() + " - Y:" + coordinate.getY() +
                " | The shot was a " + (lastShotHit ? "hit":"miss") +
                (boatDestroyed ? " | The Boat is destroyed":"."));
        */

        turnText.append("\n\t").append("Attack: ").append(lastPlayerAttacked.getName());
        turnText.append(" (X:").append(coordinate.getX()).append(" Y:").append(coordinate.getY()).append(")");
        turnText.append("\n\t").append("Last shot was a ").append((lastShotHit ? "hit":"miss"));

        if (lastShotHit) {
            lastHit = coordinate;
            lastBoatHitDirection = direction;
        } else {
            lastPlayerAttacked = null;
        }

        if (boatDestroyed) {    // The boat is destroyed
            lastPlayerAttacked = null;
            lastBoatHitDirection = null;

            boatDestroyed = false;
            lastShotHit = false;

            turnText.append(" | Boat Destroyed");
        }

        System.out.println(turnText.toString() + "\n");
        return lastShotHit;
    }
}

/*
TODO: Rewrite the bot so it works correct...
 */