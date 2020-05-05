/* Board info:
0 - empty
1 - Boat
2 - Hit
3 - Miss

10 - Carrier
11 - Battleship
12 - Cruiser
13 - Submarine
14 - Destroyer
 */

package com.company.model;

public class Board {
    private int[][] boardMatrix;
    private static int defaultSize = 10;

    public Board() {
        this.boardMatrix = new int[defaultSize][defaultSize];
    }
    public Board(int size) {
        this.boardMatrix = new int[size][size];
    }
    public Board(int x, int y) {
        this.boardMatrix = new int[x][y];
    }

    //Set
    public static void setDefaultSize(int size) {
        defaultSize = size;
    }

    public void setPlace(int x, int y, int value) {
        this.boardMatrix[x][y] = value;
    }

    //Get
    public int getPlaceValue(int x, int y) {
        return boardMatrix[x][y];
    }


    //Other
    public void resetBoard() {
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int l = 0; l < boardMatrix.length; l++) {
                boardMatrix[i][l] = 0;
            }
        }
    }

    public boolean attacked(int x, int y) {
        switch (boardMatrix[x][y]) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return true;
            default:
                return false;
        }
    }


    //Print
    public void print() {
        StringBuilder string = new StringBuilder();

        for (int[] x : boardMatrix) {
            for (Integer y : x) {
                string.append("|").append(y);
            }
            string.append("|\n");
        }
        System.out.println(string.toString());
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (int[] x : boardMatrix) {
            for (Integer y : x) {
                string.append(y);
            }
            string.append("\n");
        }
        return string.toString();
    }
}

/*
TODO: make attacking a board work.
    Now when attacking i change the value to 2, but i need to change it to something that makes the boatDestroyed() method from Player work.
    This can be something like adding 10 to the value (so the value is 20 to 24), the 2 indication that the boat is hit.
 */