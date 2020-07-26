package com.company;

import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.game.player.Bot;
import com.company.model.game.player.Player;
import com.company.model.game.player.PlayerStatus;
import com.company.model.lobby.Lobby;
import com.company.model.lobby.Options;

import java.util.ArrayList;
import java.util.Scanner;


public class Application {
    public static Scanner sc = new Scanner(System.in);
    public static boolean running = true;
    public static int x, y, bots = 0;
    public static Direction direction;

    public static ArrayList<Player> playersOut = new ArrayList<>();
    public static ArrayList<Player> playersIn = new ArrayList<>();

    public static void main(String[] args) {

        Lobby lobby = new Lobby("Lobby 1");
        Options options = new Options();

        lobby.setOptions(options);

        // Adding players
        while (running) {
            int input = -1;

            while (!(input < 4) || !(input > 0)) {
                System.out.print("Add player (1), bot (2) or continue (3): ");

                input = Integer.parseInt(sc.next());
            }

            switch (input) {
                case 1: playerAdd(lobby); break;
                case 2: botAdd(lobby); break;
                case 3: running = false; break;
            }
        }


        playersIn.addAll(lobby.getPlayers().values());

        // Players place boats
        for (Player player : lobby.getPlayers().values()) {
            if (player instanceof Bot) {
                Bot bot = (Bot)player;

                for (BoatType boatType : lobby.getOptions().getBoats()) {
                    bot.placeBoat(boatType);
                }

                System.out.println();
                player.printPrivateBoard();
                System.out.println("\n");
            } else {
                playerPlace(lobby, player);
            }
        }

        System.out.println("*** *** *** ***");
        System.out.println("  Attack Face  ");
        System.out.println("*** *** *** ***");
        System.out.print("\n\n\n\n");

        // Players attack
        running = true;
        while (running) {
            for (Player player : playersIn) {
                player.setStatus(PlayerStatus.TURN);

                if (player instanceof  Bot) {
                    Bot bot = (Bot)player;

                    bot.attackPlayer(playersIn);
                } else {
                    playerAttack(lobby, player);
                }

                if (player.getStatus().equals(PlayerStatus.OUT) || player.getBoatsAlive() <= 0) {
                    playersOut.add(player);
                }

                player.setStatus(PlayerStatus.WAITING);
            }

            for (Player player : playersOut) {
                playersIn.remove(player);
                player.setStatus(PlayerStatus.OUT);
            }

            if (playersIn.size() == 1) {
                System.out.println("\n\n\n");
                System.out.println("*** *** *** *** *** *** ***");
                System.out.println("Player " + playersIn.get(0).getName() + " won the game!");
                System.out.println("*** *** *** *** *** *** ***");
                System.out.println("\n\n\n");
                running = false;
            }
        }

        System.out.println("Player boards:");
        for (Player player : lobby.getPlayers().values()) {
            System.out.println(player.getName() + " - " + player.getBoatsAlive());
            player.printPrivateBoard();

            System.out.println("\nHits:");
            for (Player player1 : lobby.getPlayers().values()) {
                if (!player1.getName().equals(player.getName())) {
                    System.out.println(player1.getName() + " -> " +(lobby.getOptions().getTotalHits() - player.getEnemyHits().get(player1.getName()).size()) + " -> " + player.getEnemyHits().get(player1.getName()).toString());
                }
            }

            System.out.println();
        }


    }

    // Player
    public static void playerAdd(Lobby lobby) {
        System.out.print("Player name: ");
        String name = sc.next();

        if (!lobby.getPlayers().containsKey(name)) {
            lobby.addPlayer(new Player(name, lobby.getOptions()));
        } else {
            System.out.print("That name exists, try again!");
        }

        System.out.println();
    }

    public static void playerPlace(Lobby lobby, Player player) {
        System.out.println("\n" + player.getName() + " Place your boats:");

        for (BoatType boatType : player.getGameOptions().getBoats()) {
            System.out.println(boatType + "(" + boatType.getLength() + ")");

            x = -1;
            while (!(x < lobby.getOptions().getBoardX()) || !(x >= 0 )) {
                System.out.print("X = ");
                x = Integer.parseInt(sc.next());
            }

            y = -1;
            while (!(y < lobby.getOptions().getBoardY()) || !(y >= 0 )) {
                System.out.print("Y = ");
                y = Integer.parseInt(sc.next());
            }

            System.out.print("Direction = ");
            direction = Direction.valueOf(sc.next().toUpperCase());

            player.placeBoat(new Coordinate(x, y), boatType, direction);
            player.printPrivateBoard();

            System.out.print("\n");
        }
    }

    public static void playerAttack(Lobby lobby, Player player) {
        System.out.print(player.getName() + " Select players to attack (");
        for (Player player1 : lobby.getPlayers().values()) {
            if (!player1.getName().equals(player.getName())) {
                System.out.print(player1.getName() + ", ");
            }
        }

        System.out.print(")");

        String playerToAttack = "";
        while (!lobby.getPlayers().containsKey(playerToAttack)) {
            System.out.print("\nPlayer = ");
            playerToAttack = sc.next();
        }

        System.out.println("\nWhere to attack?");

        x = -1;
        while (!(x < lobby.getOptions().getBoardX()) || !(x >= 0 )) {
            System.out.print("X = ");
            x = Integer.parseInt(sc.next());
        }

        y = -1;
        while (!(y < lobby.getOptions().getBoardY()) || !(y >= 0 )) {
            System.out.print("Y = ");
            y = Integer.parseInt(sc.next());
        }

        player.attackPlayer(new Coordinate(x,y), lobby.getPlayers().get(playerToAttack));

        System.out.println();
        System.out.println();

        player.printAttackBoard(playerToAttack);

        System.out.println();
        System.out.println();
    }

    // Bot
    public static void botAdd(Lobby lobby) {
        lobby.addPlayer(new Bot("bot_" + ++bots, lobby.getOptions()));
        System.out.println("Bot_" + bots + " added");
    }
}