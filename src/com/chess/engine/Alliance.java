package com.chess.engine;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public enum Alliance {
    WHITE {
        /**
         * @return -1 always
         */
        @Override
        public int getDirection() {
            return -1;
        }

        /**
         * @return 1 always
         */
        @Override
        public int getOppositeDirection() {
            return 1;
        }

        /**
         * @return true always
         */
        @Override
        public boolean isWhite() {
            return true;
        }

        /**
         * @return false always
         */
        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.EIGHTH_RANK[position];
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        /**
         * @return 1 always
         */
        @Override
        public int getDirection() {
            return 1;
        }

        /**
         * @return -1 always
         */
        @Override
        public int getOppositeDirection() {
            return -1;
        }

        /**
         * @return false always
         */
        @Override
        public boolean isWhite() {
            return false;
        }

        /**
         * @return true always
         */
        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.FIRST_RANK[position];
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    /**
     * This method establishes directions of movements for the pawns,
     * since they can only move in one direction depending on the alliance.
     *
     * @return int value representing the direction, always -1 for white and 1 for black
     */
    public abstract int getDirection();

    /**
     * The opposite of {@link #getDirection()}.
     *
     * @return always 1 for white and -1 for black
     */
    public abstract int getOppositeDirection();

    /**
     * Checks if a piece's alliance is white
     * @return true if white, otherwise false
     */
    public abstract boolean isWhite();

    /**
     * Checks if a piece's alliance is black
     * @return true if black, otherwise false
     */
    public abstract boolean isBlack();

    /**
     * Checks if a tile is a pawn promotion tile
     * @param position tile coordinate
     * @return true if the position is a promotion square, otherwise false
     */
    public abstract boolean isPawnPromotionSquare(int position);

    /**
     * Specifies the player this method was called on
     * @param whitePlayer the white player
     * @param blackPlayer the black player
     * @return if this was called on a white player this will return the white player, otherwise returns the black player
     */
    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}