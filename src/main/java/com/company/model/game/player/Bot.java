package com.company.model.game.player;

import com.company.model.game.BoatType;
import com.company.model.game.Direction;

import java.util.ArrayList;
import java.util.Random;
//import static com.company.Application.directionsUse;

/*
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

    public void placeBoat(BoatType boatType) {
        setNumberOfBoatsAlive(getNumberOfBoatsAlive() + 1);

        int x = 0, y = 0;
        Direction direction = null;

        boolean running = true;

        while (running) {
            x = new Random().nextInt(Board.getDefaultX());
            y = new Random().nextInt(Board.getDefaultY());
            direction = Direction.getRandomDirection(getUseDirections());

            if (getBoard().boatIsInsideBoard(x, y, boatType, direction)) {  // if boat is inside of the board
                if (!getBoard().boatsOverlap(x, y, boatType, direction)) {   // if boat does not overlap another boat
                    running = false;
                }
            }
        }
        getBoard().setBoat(x, y, boatType, direction);
    }
/*
    public boolean attackPlayer(ArrayList<Player> players) {
        //TODO #1: Make bot attack a player.
        //TODO #2: Se if i can change some variables to temp variables

        turnText = new StringBuilder();
        int boatId, x = 0, y = 0;
        Direction direction;

        boatDestroyed = false;

        turnText.append(getName()).append(" (").append(getNumberOfBoatsAlive()).append(")").append(": ").append("\n\t");

        if (lastPlayerAttacked != null) { // Attack same player
            //System.out.println("Attacking " + lastPlayerAttacked.getName() + " again, because of hit last time");
            turnText.append("Attacking the same player: ").append(lastPlayerAttacked.getName()).append("\n\t");

            if (lastShotHit && lastBoatHitDirection != null) {  // Hit, hit direction known | try the same direction
                /*  Case 1

                    Take the known direction and add the x, y to lastHitX and lastHitY.
                    Then se if that spot can be shot at.
                        Yes: Shoot there
                        No: Flip the direction.
                            Add that direction to firstHitX and firstHitY (so now the attack is the other direction)
                            Se if that spot can be attacked,
                                For now just shoot there (later think of what to do)
                 */
/*
                turnText.append("Case 1");

                // Attack
                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(lastShotX, lastShotY);

                x = lastShotX + lastBoatHitDirection.getX();   //TODO: See #2
                y = lastShotY + lastBoatHitDirection.getY();   //TODO: See #2

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y)) {   // Can shoot at the new location
                    attack(x, y, boatId, lastPlayerAttacked, lastBoatHitDirection);
                } else {    // Shoot a different direction
                    turnText.append("\n\t\t").append("Trying a different direction");

                    lastBoatHitDirection = Direction.flipDirection(lastBoatHitDirection);   //TODO: See #2

                    x = firstBoatHitX + lastBoatHitDirection.getX();   //TODO: See #2
                    y = firstBoatHitY + lastBoatHitDirection.getY();   //TODO: See #2

                    attack(x, y, boatId, lastPlayerAttacked, lastBoatHitDirection);
                }
            } else if (lastShotHit == false && lastBoatHitDirection != null) {  // Miss, hit direction known | Attack in the opisit direction from lastHitDirection
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
/*
                turnText.append("Case 2.");

                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(firstBoatHitX, firstBoatHitY);

                // Attack
                direction = Direction.flipDirection(lastBoatHitDirection);

                x = firstBoatHitX + direction.getX();
                y = firstBoatHitY + direction.getY();

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y)) {   // Can shoot there
                    attack(x, y, boatId, lastPlayerAttacked, direction);
                } else {    // Cant shoot there
                    turnText.append("Problem 2...");
                    return false;
                }

            } else if (lastShotHit && lastBoatHitDirection == null) {   // Hit, hit direction not known | shoot a random direction | normaly after the first hit
                /*  Case 3

                    Because I do not know the direction the boat is going, I need to try a "random" direction.

                    Make a temp list of all directions in use.
                    Take a random direction from that list.
                        Can I shoot there?
                            Yes: Attack there.
                            No: Remove that direction from the list and try a different direction

                        If no direction is found there is a bug somewhere?
                 */
/*
                turnText.append("Case 3");

                // Attack
                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(lastShotX, lastShotY);

                ArrayList<Direction> tempDirections = new ArrayList<>(getUseDirections());

                direction = Direction.getRandomDirection(tempDirections);

                x = lastShotX + direction.getX();
                y = lastShotY + direction.getY();

                tempDirections.remove(direction);

                while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y) && tempDirections.size() > 0) {
                    direction = Direction.getRandomDirection(tempDirections);

                    x = lastShotX + direction.getX();
                    y = lastShotY + direction.getY();

                    tempDirections.remove(direction);
                }

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y)) {   // If can shoot
                    attack(x, y, boatId, lastPlayerAttacked, direction);
                } else {    // Cant shoot, all directions are used up so there is a problem here...
                    //TODO: Problem 1?
                    turnText.append("\n").append("problem 1...");
                }
            }  else if (lastShotHit == false && lastBoatHitDirection == null) {  // Miss, hit direction not known | attack in the opisit direction from the firstBoatHitX and firstBoatHitY (example: was up, now down)
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
/*
                turnText.append("Case 4");

                boatId = getAttackBoard(lastPlayerAttacked.getName()).getPlaceValue(firstBoatHitX, firstBoatHitY);
                // Attack
                ArrayList<Direction> tempDirections = new ArrayList<>(getUseDirections());

                direction = Direction.getRandomDirection(tempDirections);

                x = lastHitX + direction.getX();
                y = lastHitY + direction.getY();

                tempDirections.remove(direction);

                while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y) && tempDirections.size() > 0) {
                    direction = Direction.getRandomDirection(tempDirections);

                    x = lastHitX + direction.getX();
                    y = lastHitY + direction.getY();

                    tempDirections.remove(direction);
                }

                if (getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y)) {   // If can shoot
                    attack(x, y, boatId, lastPlayerAttacked, direction);
                } else {    // Cant shoot, all directions are used up so there is a problem here...
                    //TODO: Problem 3? Have gotten this 1 time
                    turnText.append("\n").append("problem 3...");

                    direction = Direction.getRandomDirection(tempDirections);

                    x = lastHitX + direction.getX();
                    y = lastHitY + direction.getY();

                    tempDirections.remove(direction);

                    while (!getAttackBoard(lastPlayerAttacked.getName()).canShot(x, y) && tempDirections.size() > 0) {
                        direction = Direction.getRandomDirection(tempDirections);

                        x = lastHitX + direction.getX();
                        y = lastHitY + direction.getY();

                        tempDirections.remove(direction);
                    }

                    attack(x, y, boatId, lastPlayerAttacked, direction);
                }


            } else {    // None of the above happened, so i need to write more code
                turnText.append("Not shure what to put here...");
            }


        } else { // Attack a random player
            //TODO: Make to use private function attack(){}, maybe
            turnText.append("Attacking a random player: ");

            lastPlayerAttacked = players.get(new Random().nextInt(players.size()));

            turnText.append(lastPlayerAttacked.getName()).append(" -> ");

            x = new Random().nextInt(Board.getDefaultX());   //TODO: See #2
            y = new Random().nextInt(Board.getDefaultY());   //TODO: See #2

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

        System.out.println(turnText.toString());
        return false;
    }

    private void attack(int x, int y, int boatId, Player attackPlayer, Direction direction) {
        lastShotHit = attackPlayer(attackPlayer, x, y);
        boatDestroyed = boatDestroyed(x, y, boatId, attackPlayer);

        lastShotX = x;
        lastShotY = y;

        turnText.append("\n\t").append("X:").append(x).append(" - Y:").append(y).append(" - Dir: ").append(direction.toString());

        if (lastShotHit) {
            lastHitX = x;
            lastHitY = y;
            lastBoatHitDirection = direction;
        }

        turnText.append("\n").append(lastShotHit ? "Hit" : "Miss");

        if (boatDestroyed) {    // The boat is destroyed
            lastPlayerAttacked = null;
            lastBoatHitDirection = null;

            turnText.append("\n").append("Boat destroyed");
        }
    }

}
*/