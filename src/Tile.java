/**
 * Represents a single tile on the chess board as an abstract class.
 *
 * <p>A tile can either be empty or occupied. This is represented by the two concrete subclasses {@link EmptyTile} and {@link OccupiedTile}
 *
 * <p>The {@link Piece} occupying a tile can be retrieved using {@link #getPiece()}.
 */
public abstract class Tile {

    int tileCoordinate;

    /**
     * Creates a tile at the specified coordinate.
     *
     * @param tileCoordinate coordinate of the tile
     */
    public Tile (int tileCoordinate) {
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

        Piece pieceOnTile;

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