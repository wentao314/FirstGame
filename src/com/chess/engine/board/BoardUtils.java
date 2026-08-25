package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SEVENTH_ROW = null;
    public static final boolean[] SECOND_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    /**
     * Helper method to generalize the initialization of the columns.
     * @param columnIndex index in range 0-7
     * @return an array with 64 entries where each column with entered index is marked true, all other entries are marked false
     */
    private static boolean[] initColumn(int columnIndex) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnIndex] = true;
            columnIndex += NUM_TILES_PER_ROW;
        } while(columnIndex < NUM_TILES);
        return column;
    }

    /**
     * Helper method to generalize the initialization of the rows.
     * @param rowIndex index in range 0-7
     * @return an array with 64 entries where each row with entered index is marked true, all other entries are marked false
     */
    private static boolean[] initRow(int rowIndex) {
        final boolean[] row = new boolean[NUM_TILES];
        int offset = 0;
        do {
            row[rowIndex * 8 + offset] = true;
            offset++;
        } while(offset < NUM_TILES_PER_ROW);
    }

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    /**
     * Checks if given coordinate is in bounds of the board (from 0-63).
     * @param coordinate given coordinate
     * @return true if coordinate is in bounds, otherwise false
     */
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
