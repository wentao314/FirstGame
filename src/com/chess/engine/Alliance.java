package com.chess.engine;

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
    };

    /**
     * This method establishes directions of movements for the pawns,
     * since they can only move in one direction depending on the alliance.
     *
     * @return int value representing the direction, always -1 for white and 1 for black
     */
    public abstract int getDirection();

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
}