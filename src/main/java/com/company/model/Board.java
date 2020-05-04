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
    public void setDefaultSize(int size) {
        defaultSize = size;
    }

    public void setPlace(int x, int y, int value) {
        this.boardMatrix[x][y] = value;
    }

    //Get


    //Other
    public void resetBoard() {
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int l = 0; l < boardMatrix.length; i++) {
                boardMatrix[i][l] = 0;
            }
        }
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
