package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import static com.chess.gui.Table.*;

/**
 * A GUI panel responsible for displaying the history of moves played in the game.
 * It displays moves for both white and black in a scrollable tabular format.
 */
public class GameHistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);

    /**
     * Constructs a new game history panel, initializing the layout, custom data model,
     * and scrollable table view.
     */
    GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Updates and refreshes the move history panel based on the current state of the board and the move log.
     * @param board the current board
     * @param moveHistory log containing moves executed during game
     */
    void redo(final Board board,
              final MoveLog moveHistory) {
        int currentRow = 0;
        this.model.clear();
        for(final Move move: moveHistory.getMoves()) {
            final String moveText = move.toString();
            if(move.getMovedPiece().getPieceAlliance().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            } else if(move.getMovedPiece().getPieceAlliance().isBlack()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }

        if(moveHistory.getMoves().size() > 0) {
            final Move lastMove = moveHistory.getMoves().get(moveHistory.size() - 1);
            final String moveText = lastMove.toString();
            if(lastMove.getMovedPiece().getPieceAlliance().isWhite()) {
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow, 0);
            } else if(lastMove.getMovedPiece().getPieceAlliance().isBlack()) {
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board), currentRow - 1, 1);
            }
        }

        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    /**
     * Calculates string representation for check and checkmate in the game history log.
     * @param board current board
     * @return string indicating state
     */
    private String calculateCheckAndCheckMateHash(final Board board) {
        if(board.currentPlayer().isInCheckMate()) {
            return "#";
        } else if(board.currentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }

    /**
     * Custom data model representing the structure and contents of the game history table.
     * Each row corresponds to a single turn, containing columns for white's and black' move.
     */
    private static class DataModel extends DefaultTableModel {

        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        /**
         * Constructs an empty data model for storing moves.
         */
        DataModel() {
            this.values = new ArrayList<>();
        }

        /**
         * Clears all stored move records from the history model and resets row count.
         */
        public void clear() {
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.values == null) {
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int column) {
            final Row currentRow = this.values.get(row);
            if(column == 0) {
                return currentRow.getWhiteMove();
            } else if(column == 1) {
                return currentRow.getBlackMove();
            }
            return null;
        }

        @Override
        public void setValueAt(final Object atValue,
                               final int row,
                               final int column) {
            final Row currentRow;
            if(this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if(column == 0) {
                currentRow.setWhiteMove((String)atValue);
                fireTableRowsInserted(row, row);
            } else if(column == 1) {
                currentRow.setBlackMove((String)atValue);
                fireTableCellUpdated(row, column);
            }
        }

        @Override
        public Class<?> getColumnClass(final int column) {
            return Move.class;
        }

        @Override
        public String getColumnName(final int column) {
            return NAMES[column];
        }
    }

    /**
     * Data wrapper representing a single row entry in the game history table,
     * containing text notations for both white's move and black's move.
     */
    private static class Row {

        private String whiteMove;
        private String blackMove;

        /**
         * Constructs a new empty row entry.
         */
        Row() {
        }

        /**
         * Retrieves the notation for white's move in this turn.
         * @returna string representing white's move
         */
        public String getWhiteMove() {
            return this.whiteMove;
        }

        /**
         * Retrieves the notation for black's move in this turn.
         * @returna string representing black's move
         */
        public String getBlackMove() {
            return this.blackMove;
        }

        /**
         * Sets the move notation for white in this turn.
         * @param move the string notation of white's move
         */
        public void setWhiteMove(final String move) {
            this.whiteMove = move;
        }

        /**
         * Sets the move notation for black in this turn.
         * @param move the string notation of black's move
         */
        public void setBlackMove(final String move) {
            this.blackMove = move;
        }
    }
}
