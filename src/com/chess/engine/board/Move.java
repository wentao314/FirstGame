package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.board.Board.Builder;

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

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public abstract Board execute();

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

        /**
         * Constructs board after making a move.
         * @return the new board after the move
         */
        @Override
        public Board execute() {
            // builder for new board
            final Builder builder = new Builder();
            // player moving piece: pieces that aren't the moved piece are still on their positions
            for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
                // TODO: hashcode and equals for pieces
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            // same thing for opponent (opponent doesn't have a moved piece hence missing if statement)
            for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            // move the moved piece
            builder.setPiece(null);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
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

        @Override
        public Board execute() {
            return null;
        }
    }
}
