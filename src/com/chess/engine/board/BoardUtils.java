package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    // helper method to generalize the initialization of the columns
    private static boolean[] initColumn(int columnIndex) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnIndex] = true;
            columnIndex += NUM_TILES_PER_ROW;
        } while (columnIndex < NUM_TILES);
        return column;
    }

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
