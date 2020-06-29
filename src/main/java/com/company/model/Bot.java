package com.company.model;

import java.util.ArrayList;
import java.util.Random;
//import static com.company.Application.directionsUse;

public class Bot extends Player {
    Player lastPlayerAttacked;          //Last player
    Direction lastBoatHitDirection;     //The direction the boat is going
    boolean lastShotHit;                //Last shot hit? true = yes
    boolean boatDestroyed;              //If there is a boat the bot is "working on"
    int lastHitX, lastHitY;             //Last shot that hit, (x,y)
    int lastShotX, lastShotY;           //Last shot, (x,y)
    int firstBoatHitX, firstBoatHitY;   //The first (x,y) that hit a boat, this so if the bot finds an end to a boat and the boat still is not destroyed it can go back and go the other way

    StringBuilder turnText = new StringBuilder();

    public Bot(String name, Board personalBoard) {
        super(name, personalBoard);
    }

    //Get
    public Player getLastPlayerAttacked() {
        return lastPlayerAttacked;
    }

    public void placeBoat(Boat boat) {
        setNumberOfBoatsAlive(getNumberOfBoatsAlive() + 1);

        int x = 0, y = 0;
        Direction direction = null;

        boolean running = true;

        while (running) {
            x = new Random().nextInt(Board.getDefaultX());
            y = new Random().nextInt(Board.getDefaultY());
            direction = Direction.getRandomDirection(getUseDirections());

            if (getBoard().boatIsInsideBoard(x, y, boat, direction)) {  // if boat is inside of the board
                if (!getBoard().boatsOverlap(x, y, boat, direction)) {   // if boat does not overlap another boat
                    running = false;
                }
            }
        }
        getBoard().setBoat(x, y, boat, direction);
    }

    public boolean attackPlayer(ArrayList<Player> players) {
        //TODO #1: Make bot attack a player.
        //TODO #2: Se if i can change some variables to temp variables

        turnText = new StringBuilder();
        int boatId;
        Direction direction;

        turnText.append(getName()).append(": ");

        if (lastPlayerAttacked != null) { // Attack same player
            //System.out.println("Attacking " + lastPlayerAttacked.getName() + " again, because of hit last time");
            turnText.append("Attacking the same player: ").append(lastPlayerAttacked.getName()).append("\n\t");

            if (lastShotHit && lastBoatHitDirection != null) {  // Hit, hit direction known | try the same direction
                turnText.append("Last was a hit, know hit direction");

                // Attack
                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(lastShotX, lastShotY);

                lastShotX += lastBoatHitDirection.getX();   //TODO: See #2
                lastShotY += lastBoatHitDirection.getY();   //TODO: See #2

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(lastShotX, lastShotY)) {   // Can shoot at the new location
                    attack(lastShotX, lastShotY, boatId, lastPlayerAttacked, lastBoatHitDirection);
                } else {    // Shoot a different direction
                    turnText.append("\n\t").append("Trying a different direction");

                    lastBoatHitDirection = Direction.flipDirection(lastBoatHitDirection);   //TODO: See #2

                    lastShotX = firstBoatHitX + lastBoatHitDirection.getX();   //TODO: See #2
                    lastShotY = firstBoatHitY + lastBoatHitDirection.getY();   //TODO: See #2

                    attack(lastShotX, lastShotY, boatId, lastPlayerAttacked, lastBoatHitDirection);
                }
            } else if (lastShotHit && lastBoatHitDirection == null) {   // Hit, hit direction not known | shoot a random direction | normaly after the first hit
                turnText.append("Last was a hit, don't know the direction");

                // Attack
                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(lastShotX, lastShotY);

                ArrayList<Direction> tempDirections = new ArrayList<>(getUseDirections());

                direction = Direction.getRandomDirection(tempDirections);
                tempDirections.remove(direction);

                while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(lastShotX += direction.getX(), lastShotY += direction.getY()) && tempDirections.size() > 0) {
                    direction = Direction.getRandomDirection(tempDirections);
                    tempDirections.remove(direction);
                }

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(lastShotX += direction.getX(), lastShotY += direction.getY())) {   // If can shoot
                    attack(lastShotX += direction.getX(), lastShotY += direction.getY(), boatId, lastPlayerAttacked, direction);
                } else {    // Cant shoot, all directions are used up so there is a problem here...
                    //TODO: Problem 1?
                    turnText.append("\n").append("problem 1...");
                }
            } else if (lastShotHit == false && lastBoatHitDirection != null) {  // Miss, hit direction known | Attack in the opisit direction from lastHitDirection
                turnText.append("Last was a miss, know the direction...");

                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(firstBoatHitX, firstBoatHitY);

                // Attack
                direction = Direction.flipDirection(lastBoatHitDirection);

                lastShotX = firstBoatHitX += direction.getX();
                lastShotY = firstBoatHitY += direction.getY();

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(lastShotX, lastShotY)) {   // Can shoot there
                    attack(lastShotX, lastShotY, boatId, lastPlayerAttacked, direction);
                } else {    // Cant shoot there
                    turnText.append("Problem 2...");
                    return false;   //
                }

            } else if (lastShotHit == false && lastBoatHitDirection == null) {  // Miss, hit direction not known | attack in the opisit direction from the firstBoatHitX and firstBoatHitY (example: was up, now down)
                turnText.append("Last was a miss, don't know the direction");

                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(firstBoatHitX, firstBoatHitY);
                // Attack
                ArrayList<Direction> tempDirections = new ArrayList<>(getUseDirections());

                direction = Direction.getRandomDirection(tempDirections);
                tempDirections.remove(direction);

                while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(lastHitX += direction.getX(), lastHitY += direction.getY()) && tempDirections.size() > 0) {
                    direction = Direction.getRandomDirection(tempDirections);
                    tempDirections.remove(direction);
                }

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(lastHitX += direction.getX(), lastHitY += direction.getY())) {   // If can shoot
                    attack(lastShotX += direction.getX(), lastHitY += direction.getY(), boatId, lastPlayerAttacked, direction);
                } else {    // Cant shoot, all directions are used up so there is a problem here...
                    //TODO: Problem 3?
                    turnText.append("\n").append("problem 3...");
                }


            } else {    // None of the above happened, so i need to write more code
                turnText.append("Not shure what to put here...");
            }


            /*
            if (lastShotHit) { // Last shot was a hit
                if (lastBoatHitDirection != null) { // Attack in same direction
                    lastShotX += lastBoatHitDirection.getX();
                    lastShotY += lastBoatHitDirection.getY();

                    if (getAttackBoard(lastPlayerAttacked.getName()).canShot(lastShotX, lastShotY)) { // If the place can be shot at
                        if (lastPlayerAttacked.getBoard().attacked(lastShotX, lastShotY)) { // If the shot is a hit
                            boatDestroyed = attackPlayer(lastPlayerAttacked, lastShotX, lastShotY); //True if hit
                            lastShotHit = true;
                        } else { // If the shot is a miss
                            lastShotHit = false;
                        }
                    } else { // Can´t shoot there
                        if (getAttackBoard(lastPlayerAttacked.getName()).isInsideOfBoard(lastShotX, lastShotY)) { // The shot is inside of the board

                        } else { // The shot is outside of the board

                        }
                    }






                } else { // Attack in a different location



                }


            } else {    // Miss



            }
            */


        } else { // Attack a random player
            //TODO: Make to use private function attack(){}, maybe
            turnText.append("Attacking a random player: ");

            lastPlayerAttacked = players.get(new Random().nextInt(players.size()));

            turnText.append(lastPlayerAttacked.getName()).append(" -> ");

            int x = new Random().nextInt(Board.getDefaultX());   //TODO: See #2
            int y = new Random().nextInt(Board.getDefaultY());   //TODO: See #2

            while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y)) {   // If x and y wil be at a already shot at place, try again
                x = new Random().nextInt(Board.getDefaultX());   //TODO: See #2
                y = new Random().nextInt(Board.getDefaultY());   //TODO: See #2
            }

            lastShotX = x;   //TODO: See #2
            lastShotY = y;   //TODO: See #2

            if (attackPlayer(lastPlayerAttacked, x, y)) {   // If the shot was a hit, store that
                lastShotHit = true;
                lastHitX = x;
                lastHitY = y;
                firstBoatHitX = x;
                firstBoatHitY = y;

                turnText.append("hit");
                //System.out.println(getName() + "attacked " + lastPlayerAttacked.getName() + "the shot was a hit at " + x + ", " + y);
            } else {    // If the shot was a miss
                //System.out.println(getName() + "attacked " + lastPlayerAttacked.getName() + "the shot was a miss at " + x + ", " + y);
                lastPlayerAttacked = null;

                turnText.append("miss");
            }
        }

        /*
        se if the last shot was a hit, miss or destroyed.
            if it was a miss
                shoot at a random place.

            if it was destroyed
                shoot at a random place.

            if it was a hit
                shot in a direction (up, down, left, right), that is not shot at before
                    if that is a hit save the direction and where it was hit

                if no directions are possible to shoot at, go back to lastBoatHit (X and Y), and try form there
                    if that hit save the direction and where it was hit




                se if that location is already shot at or not.
                    if it is shot at, pick another direction.
                        if there is no
         */



        System.out.println(turnText.toString());

        return false;
    }

    private void attack(int x, int y, int boatId, Player attackPlayer, Direction direction) {
        lastShotHit = attackPlayer(attackPlayer, x, y);
        boatDestroyed = boatDestroyed(x, y, boatId, attackPlayer);

        if (lastShotHit) {
            lastHitX = x;
            lastHitY = y;
            lastBoatHitDirection = direction;
        }

        if (boatDestroyed) {    // The boat is destroyed
            lastPlayerAttacked = null;
            lastBoatHitDirection = null;

            turnText.append("\n").append("Boat destroyed");
        }

        turnText.append("\n").append(lastShotHit ? "Hit" : "Miss");
    }
}
