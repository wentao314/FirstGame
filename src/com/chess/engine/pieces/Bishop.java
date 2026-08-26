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

public class Bishop extends Piece{

    // implementing the diagonal movement of the bishop as values that can be added onto the bishops current position
    // movements could be seen as vectors
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    /**
     * Allows creating a new bishop with given piece position and alliance.
     * @param piecePosition the position of the bishop after creation
     * @param pieceAlliance either white or black
     */
    public Bishop(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Analyzes all possible moves a bishop can make and returns only the valid ones.
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
                    // if the tile isn't occupied, the bishop may move there
                    if(!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this,candidateDestinationCoordinate));
                    } else {
                        // check if the piece at destination belongs to the enemy. if true, the bishop may move there.
                        // since there is a blocking piece, the bishop mustn't move further.
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

    // visualization for the bishop as string
    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    // edge-cases for invalid moves
    // will return true, if candidate offset results in an illegal move
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }
}
