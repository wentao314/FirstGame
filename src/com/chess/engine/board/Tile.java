package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single tile on the chess board as an abstract class.
 *
 * <p>A tile can either be empty or occupied. This is represented by the two concrete subclasses {@link EmptyTile} and {@link OccupiedTile}
 *
 * <p>The {@link Piece} occupying a tile can be retrieved using {@link #getPiece()}.
 */
public abstract class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    /**
     * Creates all 64 possible empty tiles, since the classic chess boards format is a 8x8 square.
     * @return immutable map of all empty tiles
     */
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        // using com.google.guava library to return an immutable version of the map so that it cant be altered
        // with methods like .clear(),.remove(),etc.
        return ImmutableMap.copyOf(emptyTileMap);
    }

    /**
     * Allows creating a new occupied tile with given piece.<p>
     * Since the tile constructors are private, this is the only way to create new tiles.
     * @param tileCoordinate coordinate of the new tile
     * @param piece piece on the new tile
     * @return if no piece was given, returns one of the cached tiles, otherwise returns the new tile
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    /**
     * Creates a tile at the specified coordinate.
     *
     * @param tileCoordinate coordinate of the tile
     */
    private Tile (int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    /**
     * Checks whether this tile is occupied.
     *
     * @return {@code true} if this tile contains a piece, otherwise {@code false}
     */
    public abstract boolean isTileOccupied();

    /**
     * Returns the piece occupying this tile.
     *
     * @return the occupying piece, or {@code null} if this tile is empty
     */
    public abstract Piece getPiece();

    /**
     * Represents an empty tile.
     */
    public static final class EmptyTile extends Tile {

        /**
         * Creates an empty tile at the specified coordinate.
         *
         * @param coordinate coordinate of the tile
         */
        public EmptyTile(int coordinate) {
            super(coordinate);
        }

        /**
         * Print visualization of empty tiles as strings.
         * @return visualizing symbol
         */
        @Override
        public String toString() {
            return "-";
        }

        /**
         * Empty tiles cannot be occupied, so this always returns false by definition.
         * @return {@code false} always
         */
        @Override
        public boolean isTileOccupied() {
            return false;
        }

        /**
         * Empty tiles cannot have pieces on them, so this always returns null by definition.
         * @return {@code null} always
         */
        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     * Represents an occupied tile.
     */
    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        /**
         * Creates an occupied tile at the specified coordinate.
         *
         * @param coordinate coordinate of the tile
         * @param pieceOnTile piece occupying the tile
         */
        public OccupiedTile(int coordinate, Piece pieceOnTile) {
            super(coordinate);
            this.pieceOnTile = pieceOnTile;
        }

        /**
         * Print visualization of occupied tiles as strings.
         * @return visualization as occupying piece
         */
        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                   getPiece().toString();
        }

        /**
         * Occupied tiles are always occupied.
         * @return {@code true} always
         */
        @Override
        public boolean isTileOccupied() {
            return true;
        }

        /**
         * Returns the piece currently occupying this tile.
         * @return the piece occupying this tile
         */
        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }
    }

}