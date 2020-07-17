/* Board info:
0 && 00 - empty
1 && 01 - Miss

10 - Carrier
11 - Battleship
12 - Cruiser
13 - Submarine
14 - Destroyer

20 - Destroyed Carrier
21 - Destroyed Battleship
22 - Destroyed Cruiser
23 - Destroyed Submarine
24 - Destroyed Destroyer
 */

package com.company.model.game;

public class Board {
    private int[][] boardMatrix;
    private static int defaultX = 10;
    private static int defaultY = 10;

    public Board() {
        this.boardMatrix = new int[defaultX][defaultY];
    }
    public Board(int size) {
        this.boardMatrix = new int[size][size];
    }
    public Board(int x, int y) {
        this.boardMatrix = new int[y][x];
    }

    //Set
    public static void setDefaultSize(int size) {
        defaultX = size;
        defaultY = size;
    }
    public static void setDefaultSize(int x, int y) {
        defaultX = x;
        defaultY = y;
    }
    public void setPlace(int x, int y, int value) {
        this.boardMatrix[y][x] = value;
    }
    public void setBoat(int x, int y, BoatTpes boatTpes, Direction direction) {   // TODO: Add some error handling, trow error if boat is outside board...
        for (int i = 0; i < boatTpes.getLength(); i++) {
            setPlace(x, y, boatTpes.getId());
            x += direction.getX();
            y += direction.getY();
        }
    }

    //Get
    public int getPlaceValue(int x, int y) {
        return boardMatrix[y][x];
    }
    public static int getDefaultSize() {
        return defaultX * defaultY;
    }
    public static int getDefaultX() {
        return defaultX;
    }
    public static int getDefaultY() {
        return defaultY;
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
        switch (boardMatrix[y][x]) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return true;
            default:
                return false;
        }
    }

    public boolean boatIsInsideBoard(int x, int y, BoatTpes boatTpes, Direction direction) {
        int boatEndX = x + (boatTpes.getLength() * direction.getX());
        int boatEndY = y + (boatTpes.getLength() * direction.getY());

        if (boatEndX < 0 || boatEndX > Board.getDefaultX() || boatEndY < 0 || boatEndY > Board.getDefaultY()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean boatsOverlap(int x, int y, BoatTpes boatTpes, Direction direction) {
        for (int i = 0; i < boatTpes.getLength(); i++) {
            if (boardMatrix[y][x] == 0) {        //Has not hit something
                x += direction.getX();
                y += direction.getY();
            } else if (boardMatrix[y][x] != 0) {    //Boat hits something
                return true;
            }
        }
        return false;   //Does not overlap a boat
    }

    public boolean canShot(int x, int y) {
        if (isInsideOfBoard(x, y)) {
            return boardMatrix[y][x] == 0;
        } else {
            return false;
        }
    }

    public static boolean isInsideOfBoard(int x, int y) {
        return (y < defaultY && y > 0) && (x < defaultX && x > 0);
    }

    //Print
    public void print() {
        StringBuilder string = new StringBuilder();

        string.append("  ");

        for (int i = 0; i < Board.getDefaultX(); i++) {
            string.append((i < Board.getDefaultX() ? "  " : " ")).append(i);
        }
        string.append("\n");

        int index = 0;

        for (int[] x : boardMatrix) {
            string.append(index).append(" ");
            index++;

            for (Integer y : x) {
                string.append("|");
                        //.append(y == 0 ? "00" : y);
                if (y == 0)
                    string.append("00");
                else if (y == 1)
                    string.append("01");
                else
                    string.append(y);
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

 */