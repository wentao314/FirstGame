package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cashedHashCode;

    Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        // TODO
        this.isFirstMove = false;
        this.cashedHashCode = computeHashCode();
    }

    /**
     * Calculates the hash code for the piece based on their attributes.
     * @return the calculated hash code
     */
    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result +(isFirstMove ? 1 : 0);
        return result;
    }

    /**
     * Checking object equality based on same position, type, alliance and first move status.
     * @param other the reference object with which to compare.
     * @return {@code true} if the object is identical to the {@link Piece}, otherwise {@code false}
     */
    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
               pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
    }

    /**
     * Returns the hash code of the piece.
     * @return hash code of the piece
     */
    @Override
    public int hashCode() {
        return computeHashCode();
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    /**
     * Analyzes all possible moves a piece can make and returns only the valid ones.
     * @param board current board
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    /**
     * Moves a piece using the concrete moving pattern of it.
     * @param move the move made
     * @return the piece with overridden information
     */
    public abstract Piece movePiece(Move move);

    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }
}
