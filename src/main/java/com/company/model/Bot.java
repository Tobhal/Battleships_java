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
            direction = Direction.valueOf(getUseDirections().get(new Random().nextInt(getUseDirections().size())).toString().toUpperCase());

            if (getBoard().boatIsInsideBoard(x, y, boat, direction)) {  // if boat is inside of the board
                if (!getBoard().boatsOverlap(x, y, boat, direction)) {   // if boat does not overlap another boat
                    running = false;
                }
            }
        }
        getBoard().setBoat(x, y, boat, direction);
    }

    public boolean attackPlayer(ArrayList<Player> players) {
        //TODO: Make bot attack a player.

        if (lastPlayerAttacked != null) { // Attack same player
            System.out.println("Attacking " + lastPlayerAttacked.getName() + "again, because of hit last time");

            if (lastShotHit && lastBoatHitDirection != null) {  // Hit, hit direction known | try the same direction
                // Attack
                int boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(lastShotX, lastShotY);

                lastShotX += lastBoatHitDirection.getX();
                lastShotY += lastBoatHitDirection.getY();

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(lastShotX, lastShotY)) {   // Can shoot at the new location
                    attack(lastShotX, lastShotY, boatId, lastPlayerAttacked);
                } else {    // Shoot a diffrent direction
                    lastBoatHitDirection = Direction.flipDirection(lastBoatHitDirection);

                    lastShotX = firstBoatHitX + lastBoatHitDirection.getX();
                    lastShotY = firstBoatHitY + lastBoatHitDirection.getY();

                    attack(lastShotX, lastShotY, boatId, lastPlayerAttacked);
                }
            } else if (lastShotHit && lastBoatHitDirection == null) {   // Hit, hit direction not known | shoot a random direction | normaly after the first hit
                // Attack


            } else if (lastShotHit == false && lastBoatHitDirection != null) {  // Miss, hit direction known | not shure what to do here... just attack random place?
                // Attack

            } else if (lastShotHit == false && lastBoatHitDirection == null) {  // Miss, hit direction not known | attack in the opisit direction from the firstBoatHitX and firstBoatHitY (example: was up, now down)
                // Attack
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
            lastPlayerAttacked = players.get(new Random().nextInt(players.size() - 1));

            int x = new Random().nextInt(Board.getDefaultX());
            int y = new Random().nextInt(Board.getDefaultY());

            while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y)) {   // If x and y wil be at a already shot at place, try again
                x = new Random().nextInt(Board.getDefaultX());
                y = new Random().nextInt(Board.getDefaultY());
            }

            lastShotX = x;
            lastShotY = y;

            if (attackPlayer(lastPlayerAttacked, x, y)) {   // If the shot was a hit, store that
                lastShotHit = true;
                lastHitX = x;
                lastHitY = y;
                firstBoatHitX = x;
                firstBoatHitY = y;

                System.out.println(getName() + "attacked " + lastPlayerAttacked.getName() + "the shot was a hit at " + x + ", " + y);
            } else {    // If the shot was a miss
                System.out.println(getName() + "attacked " + lastPlayerAttacked.getName() + "the shot was a miss at " + x + ", " + y);

                lastPlayerAttacked = null;
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

        return false;
    }

    private void attack(int x, int y, int boatId, Player attackPlayer) {
        lastShotHit = attackPlayer(lastPlayerAttacked, lastShotX, lastShotY);
        boatDestroyed = boatDestroyed(lastShotX, lastShotY, boatId, lastPlayerAttacked);

        if (boatDestroyed) {    // The boat is destroyed
            lastPlayerAttacked = null;
            lastBoatHitDirection = null;
        }
    }
}
