package com.company;

import com.company.model.Board;
import com.company.model.Boat;
import com.company.model.Direction;
import com.company.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Application {

    public static Scanner sc = new Scanner(System.in);
    public static boolean running = true;
    public static int x = 0;
    public static int y = 0;

    public static HashMap<String, Player> players = new HashMap<>();
    public static ArrayList<Boat> boatsToPlace = new ArrayList<>();

    public static ArrayList<String> playersOut = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Application - test game");

        //Game setup
        Board.setDefaultSize(10);
        //boatsToPlace.add(Boat.CARRIER);
        //boatsToPlace.add(Boat.BATTLESHIP);
        //boatsToPlace.add(Boat.CRUISER);
        boatsToPlace.add(Boat.SUBMARINE);
        boatsToPlace.add(Boat.DESTROYER);

        //Add players
        System.out.println("Add player");


        while (running) {
            System.out.print("add player or move on (1/2): ");
            switch (sc.next()) {
                case "1":
                    addPlayer();
                    break;
                case "2":
                    running = false;
                    System.out.println(players.size());
                    break;
                default:
                    System.out.println("Pleas enter 1 or 2");
                    break;
            }
        }
        /*
        System.out.println("Petter \nArne \n");
        players.put("petter", new Player("Petter", new Board()));
        players.put("arne", new Player("Arne", new Board()));
        */

        // Create attack boards for all players
        for (Player player : players.values()) {
            for (Player otherPlayer : players.values()) {
                if (!player.getName().equals(otherPlayer.getName())) {
                    player.addAttackBoard(otherPlayer);
                }
            }
        }

        //Place boats
        for (Player player : players.values()) {
            System.out.println("\n\n\n");
            System.out.println(player.getName() + " place your boat" + (boatsToPlace.size() == 1 ? ":" : "s:"));

            for (Boat boat : boatsToPlace) {
                placeBoat(player, boat);
            }

            System.out.println("----------");
        }


        //Players attack
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("********************");
        System.out.println("***  Attack Face ***");
        System.out.println("********************");
        System.out.println("\n\n\n\n\n");


        running = true;
        while (running) {
            for (Player player : players.values()) {
                if (players.values().size() == 1)
                    playerWon(player);

                if (!running)
                    break;

                if (playersOut.contains(player.getName()))
                    continue;

                System.out.println("Players available:");

                for (Player player1 : players.values())
                    System.out.print((playersOut.contains(player1.getName()) ? "" : player1.getName() + ", "));

                System.out.print("\n");

                System.out.print(player.getName() + " select player to attack (name of player, not you): \n");

                attackPlayer(player);
            }

            for (String playerOut : playersOut)
                players.remove(playerOut);


        }

        //End
        sc.close();
    }

    public static void addPlayer() {
        String playerName;

        System.out.print("Player Name: ");
        playerName = sc.next();
        playerName = playerName.toLowerCase();

        if (players.containsKey(playerName))
            addPlayer();
        else
            players.put(playerName, new Player(playerName, new Board()));
    }

    public static void placeBoat(Player player, Boat boat) {
        player.getBoard().print();

        System.out.println(boat.name() + "(" + boat.getLength() + ")");

        x = -1;
        y = -1;

        while (!(x < Board.getDefaultX()) || !(x > -1)) {
            System.out.print("X: ");
            x = Integer.parseInt(sc.next());
        }

        while (!(y < Board.getDefaultY()) || !(y > -1)) {
            System.out.print("Y: ");
            y = Integer.parseInt(sc.next());
        }

        System.out.print("Direction (up, down, left, right): ");
        Direction direction = Direction.valueOf(sc.next().toUpperCase());

        if (player.getBoard().boatIsOutside(x, y, boat, direction)) {
            player.placeBoat(x, y, boat, direction);
        } else {
            System.out.println("\nThe boat wil be outside of the board.\nPleace try again, and do not place the boat outside of the board\n");
            placeBoat(player, boat);
        }
    }

    public static void attackPlayer(Player player) {
        String playerString = sc.next();
        playerString = playerString.toLowerCase();

        x = -1;
        y = -1;

        if (players.containsKey(playerString) && !playerString.equals(player.getName())) {
            player.printAttackBoard(playerString);

            while (!(x < Board.getDefaultX()) || !(x > -1)) {
                System.out.print("Attack X: ");
                x = Integer.parseInt(sc.next());
            }

            while (!(y < Board.getDefaultY()) || !(y > -1)) {
                System.out.print("Attack Y: ");
                y = Integer.parseInt(sc.next());
            }

            player.attackPlayer(players.get(playerString), x, y);

            // Checks if a player has no boats left
            if (players.get(playerString).noBoatsLeft())
                playersOut.add(playerString);


            // Checks if there is just 1 player left, then that player has won the game
            if (players.values().size() == 1)
                playerWon(player);

            System.out.println("\n\n");
        } else {
            System.out.println("That name is wrong");
            attackPlayer(player);
        }
    }

    public static void playerWon(Player player) {

        System.out.println("\n\n\n\n\n");
        System.out.println("***** " + player.getName() + " won the game! *****");

        running = false;
    }
}
