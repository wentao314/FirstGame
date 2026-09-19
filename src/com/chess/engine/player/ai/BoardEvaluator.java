package com.chess.engine.player.ai;

import com.chess.engine.board.Board;

public interface BoardEvaluator {

    /**
     * Evaluates situation for given board and search depth.
     * The more positive the number is the more likely white is to win and the more negative the number is the more likely black is to win.
     * @param board given board
     * @param depth given evaluation depth
     * @return number indicating likeliness to win
     */
    int evaluate(Board board, int depth);

}
