package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    /**
     * Allows creating a new king with given piece position and alliance.
     * @param piecePosition the position of the king after creation
     * @param pieceAlliance either white or black
     */
    public King(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KING, piecePosition, pieceAlliance, true);
    }

    public King(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
    }

    /**
     * Analyzes all possible moves a king can make and returns only the valid ones.
     * @param board current board
     * @return a collection of valid moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        // cycles through each of the offsets and checks all the possible moves
        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            // checking for invalid moves
            if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            // checking if the new position with offset applied is still on the board
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                // if the tile isn't occupied, the king may move there
                if(!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    // check if the piece at destination belongs to the enemy. if true, the king may move there.
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    // visualization for the king as string
    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    // edge-cases for invalid moves (snippet from the queen since they move similarly)
    // will return true, if candidate offset results in an illegal move
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
