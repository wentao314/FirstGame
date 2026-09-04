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

public class Rook extends Piece {

    // implementing the horizontal/vertical movement of the rooks as values that can be added onto the rooks current position
    // movements could be seen as vectors
    private static final int [] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

    /**
     * Allows creating a new rook with given piece position and alliance.
     * @param piecePosition the position of the rook after creation
     * @param pieceAlliance either white or black
     */
    public Rook(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.ROOK, piecePosition, pieceAlliance, true);
    }

    public Rook(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.ROOK, piecePosition, pieceAlliance, isFirstMove);
    }

    /**
     * Analyzes all possible moves a rook can make and returns only the valid ones.
     * @param board current board
     * @return a collection of valid moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        // cycles through each of the offsets and checks all the possible moves
        for(final int currentCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition;
            // checking if the position is still on the board
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                // checking for invalid moves
                if(isFirstColumnExclusion(candidateDestinationCoordinate, currentCoordinateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, currentCoordinateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += currentCoordinateOffset;
                // checking if the new position with offset applied is still on the board
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    // if the tile isn't occupied, the rook may move there
                    if(!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this,candidateDestinationCoordinate));
                    } else {
                        // check if the piece at destination belongs to the enemy. if true, the rook may move there.
                        // since there is a blocking piece, the rook mustn't move further.
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    // visualization for the rook as string
    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    // edge-cases for invalid moves
    // will return true, if candidate offset results in an illegal move
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
    }
}
