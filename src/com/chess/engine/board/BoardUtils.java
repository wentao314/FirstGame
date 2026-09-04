package com.chess.engine.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] EIGHTH_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(1);
    public static final boolean[] SIXTH_RANK = initRow(2);
    public static final boolean[] FIFTH_RANK = initRow(3);
    public static final boolean[] FOURTH_RANK = initRow(4);
    public static final boolean[] THIRD_RANK = initRow(5);
    public static final boolean[] SECOND_RANK = initRow(6);
    public static final boolean[] FIRST_RANK = initRow(7);

    public static final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinate();

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    private static String[] initializeAlgebraicNotation() {
        return new String[] {
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
    }

    private static Map<String, Integer> initializePositionToCoordinate() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for(int i = 0; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

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
        return row;
    }

    /**
     * Checks if given coordinate is in bounds of the board (from 0-63).
     * @param coordinate given coordinate
     * @return true if coordinate is in bounds, otherwise false
     */
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];
    }

}
