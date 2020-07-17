package com.company;

import com.company.model.game.*;
import com.company.model.game.player.Bot;
import com.company.model.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Application {

    public static Scanner sc = new Scanner(System.in);
    public static boolean running = true;
    public static int x = 0, y = 0, bots = 0;

    public static HashMap<String, Player> players = new HashMap<>();
    public static ArrayList<BoatTpes> boatsToPlace = new ArrayList<>();

    public static ArrayList<String> playersOut = new ArrayList<>();
    public static ArrayList<Direction> directionsUse = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Application - test game");

        //Game setup
        Board.setDefaultSize(10);

        boatsToPlace.add(BoatTpes.CARRIER);
        boatsToPlace.add(BoatTpes.BATTLESHIP);
        boatsToPlace.add(BoatTpes.CRUISER);
        boatsToPlace.add(BoatTpes.SUBMARINE);
        boatsToPlace.add(BoatTpes.DESTROYER);

        directionsUse.add(Direction.UP);
        directionsUse.add(Direction.DOWN);
        directionsUse.add(Direction.LEFT);
        directionsUse.add(Direction.RIGHT);

        /*
        directionsUse.add(Direction.UP_LEFT);
        directionsUse.add(Direction.UP_RIGHT);
        directionsUse.add(Direction.DOWN_RIGHT);
        directionsUse.add(Direction.DOWN_LEFT);
        */

        Player.setUseDirections(directionsUse);

        //Add players
        System.out.println("Add player");

        addBot();
        addBot();
        addBot();
        addBot();
        addBot();

        /*
        while (running) {
            System.out.print("add player or move on (1/2/3(bot)): ");
            switch (sc.next()) {
                case "1":
                    addPlayer();
                    break;
                case "2":
                    running = false;
                    System.out.println(players.size());
                    break;
                case "3":
                    addBot();
                default:
                    System.out.println("Pleas enter 1 or 2");
                    break;
            }
        }
        */
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
            if (player instanceof Bot) {
                System.out.println(player.getName() + " placed his boats");

                for (BoatTpes boatTpes : boatsToPlace) {
                    Bot bot = (Bot)player;

                    bot.placeBoat(boatTpes);
                }
            } else {
                System.out.println("\n\n\n");
                System.out.println(player.getName() + " place your boat" + (boatsToPlace.size() == 1 ? ":" : "s:"));

                for (BoatTpes boatTpes : boatsToPlace) {
                    placeBoat(player, boatTpes);
                }
            }
            
            player.getBoard().print();
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

                if (player instanceof Bot) {
                    Bot bot = (Bot)player;

                    ArrayList<Player> availablePlayer = new ArrayList<>();

                    players.values().forEach(p -> {
                        if (!p.getName().equals(player.getName()))
                            availablePlayer.add(p);
                    });

                    bot.attackPlayer(availablePlayer);

                } else {
                    System.out.println("\n\n\n\n\n\n");
                    System.out.println("Players available:");

                    for (Player player1 : players.values())
                        System.out.print((playersOut.contains(player1.getName()) ? "" : player1.getName() + ", "));

                    System.out.print("\n");

                    System.out.print(player.getName() + " select player to attack (name of player, not you): \n");

                    attackPlayer(player);
                }

                if (player.getNumberOfBoatsAlive() <= 0) {
                    playersOut.add(player.getName());
                }

                System.out.println("\n");
                //TimeUnit.SECONDS.sleep(1);

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

    public static void addBot() {
        bots++;
        String botName = "bot_" + bots;

        players.put(botName, new Bot(botName, new Board()));
    }

    public static void placeBoat(Player player, BoatTpes boatTpes) {
        player.getBoard().print();

        System.out.println(boatTpes.name() + "(" + boatTpes.getLength() + ")");

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
        Direction direction = Direction.valueOf(sc.next().toUpperCase());   //TODO: #1
        System.out.println("-----");

        if (player.getBoard().boatsOverlap(x, y, boatTpes, direction)) {
            System.out.println("Boat overlaps another boat. Try again!");
            placeBoat(player, boatTpes);
        } else if (player.getBoard().boatIsInsideBoard(x, y, boatTpes, direction)) {
            player.placeBoat(x, y, boatTpes, direction);
        } else {
            System.out.println("\nThe boat wil be outside of the board.\nPleace try again, and do not place the boat outside of the board\n");
            placeBoat(player, boatTpes);
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

/*
TODO: #1 - Change code so it checks directionUse, so you cant use a direction that cant be used

*/