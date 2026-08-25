package com.chess.engine.board;

import com.chess.engine.pieces.Piece;


public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    /**
     * A generic move.
     * @param board current board
     * @param movedPiece the moved piece
     * @param destinationCoordinate new destination of the piece after the move
     */
    private Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove extends Move {

        /**
         * A major move.
         * @param board current board
         * @param movedPiece the moved piece
         * @param destinationCoordinate new destination of the piece after the move
         */
        public MajorMove(final Board board,
                  final Piece movedPiece,
                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        /**
         * An attacking move.
         * @param board current board
         * @param movedPiece the moved piece
         * @param destinationCoordinate new destination of the piece after the move
         */
        public AttackMove(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate,
                   final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
