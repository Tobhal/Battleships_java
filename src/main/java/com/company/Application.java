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
    public static boolean end = true;
    public static int x = 0;
    public static int y = 0;

    public static HashMap<String, Player> players = new HashMap<>();
    public static ArrayList<Boat> boatsToPlace = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Application - test game");

        //Game setup
        Board.setDefaultSize(10);
        boatsToPlace.add(Boat.CARRIER);
        boatsToPlace.add(Boat.BATTLESHIP);
        boatsToPlace.add(Boat.CRUISER);
        boatsToPlace.add(Boat.SUBMARINE);
        boatsToPlace.add(Boat.DESTROYER);

        //Add players
        System.out.println("Add player");

       while (end) {
            System.out.print("add player or move on (1/2): ");
            switch (sc.next()) {
                case "1":
                    addPlayer();
                    break;
                case "2":
                    end = false;
                    System.out.println(players.size());
                    break;
                default:
                    System.out.println("Pleas enter 1 or 2");
                    break;
            }
        }
        /*
        System.out.println("petter \narne \n");
        players.put("petter", new Player("petter", new Board()));
        players.put("arne", new Player("arne", new Board()));
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
            int x, y;
            Direction direction;

            System.out.println(player.getName() + " place your boat" + (boatsToPlace.size() == 1 ? ":" : "s:"));

            for (Boat boat : boatsToPlace) {
                System.out.println(boat.name());

                System.out.print("X: ");
                x = Integer.parseInt(sc.next());

                System.out.print("Y: ");
                y = Integer.parseInt(sc.next());

                System.out.print("Direction (up, down, left, right): ");
                direction = Direction.valueOf(sc.next().toUpperCase());

                player.placeBoat(x, y, boat, direction);
            }

            player.getBoard().print();
            System.out.println("----------");
        }


        //Players attack
        System.out.println("********************");
        System.out.println("***  Attack Face ***");
        System.out.println("********************");
        System.out.println("Players:");
        for (Player player1 : players.values())
            System.out.println("* " + player1.getName());


        end = false;
        while (!end) {
            for (Player player : players.values()) {

                System.out.print(player.getName() + " select player to attack (name of player, not you): ");
                String playerString = sc.next();

                System.out.print("Attack X: ");
                x = Integer.parseInt(sc.next());

                System.out.print("Attack Y: ");
                y = Integer.parseInt(sc.next());

                player.attackPlayer(players.get(playerString), x, y);

                if (players.get(playerString).hasNoBoatsLeft()) {
                    System.out.println("** " + player.getName() + " won the game! ** ");
                    end = true;
                    break;
                }
            }
        }

        //End
        sc.close();
    }

    public static void addPlayer() {
        String playerName;

        System.out.print("Player Name: ");
        playerName = sc.next();

        if (players.containsKey(playerName))
            addPlayer();
        else
            players.put(playerName, new Player(playerName, new Board()));
    }
}
