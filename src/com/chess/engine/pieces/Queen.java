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

public class Queen extends Piece {

    // since the queen possesses the abilities of both the rook and bishop, this is going to be a combination of both offsets
    private static final int [] CANDIDATE_MOVE_VECTOR_COORDINATES = {-17, -15, -10, -8, -6, -1, 1, 6, 8, 10, 15, 17};

    /**
     * Allows creating a new queen with given piece position and alliance.
     * @param piecePosition the position of the queen after creation
     * @param pieceAlliance either white or black
     */
    public Queen(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.QUEEN, piecePosition, pieceAlliance, true);
    }

    public Queen(final Alliance pieceAlliance,
                 final int piecePosition,
                 final boolean isFirstMove) {
        super(PieceType.QUEEN, piecePosition, pieceAlliance, isFirstMove);
    }

    /**
     * Analyzes all possible moves a queen can make and returns only the valid ones.
     * @param board current board
     * @return a collection of valid moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        // cycles through each of the offsets and checks all the possible moves
        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition;
            // checking if the position is still on the board
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                // checking for invalid moves
                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                    isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;
                // checking if the new position with offset applied is still on the board
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    // if the tile isn't occupied, the queen may move there
                    if(!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this,candidateDestinationCoordinate));
                    } else {
                        // check if the piece at destination belongs to the enemy. if true, the queen may move there.
                        // since there is a blocking piece, the queen mustn't move further.
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
    public Queen movePiece(Move move) {
        return new Queen(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    // visualization for the queen as string
    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }

    // edge-cases for invalid moves (combination of rook and bishop)
    // will return true, if candidate offset results in an illegal move
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
