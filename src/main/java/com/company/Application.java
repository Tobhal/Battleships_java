package com.company;

import com.company.model.game.Boat;
import com.company.model.game.BoatType;
import com.company.model.game.Coordinate;
import com.company.model.game.Direction;
import com.company.model.game.player.Player;
import com.company.model.lobby.Lobby;
import com.company.model.lobby.Options;

import java.util.ArrayList;
import java.util.Scanner;


public class Application {
    public static Scanner sc = new Scanner(System.in);
    public static boolean running = true;
    public static int x, y;
    public static Direction direction;

    public static void main(String[] args) {

        Lobby lobby = new Lobby("Lobby 1");
        Options options = new Options();

        options.setBoats(new ArrayList<>() {{
            add(BoatType.CARRIER);
        }});

        lobby.setOptions(options);

        // Adding players
        lobby.addPlayer(new Player("Player 1", lobby.getOptions()));
        lobby.addPlayer(new Player("Player 2", lobby.getOptions()));

        // Players place boats
        for (Player player : lobby.getPlayers().values()) {
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
                player.printBoard();

                System.out.print("\n");
            }
        }

        // Players attack



    }
}
