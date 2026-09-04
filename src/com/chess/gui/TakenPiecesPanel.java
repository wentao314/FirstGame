package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.chess.gui.Table.*;

public class TakenPiecesPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;
    private MoveLog moveLog;

    private static final Color PANEL_COLOR = Color.decode("#A67C52");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(100, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel() {
        super(new GridLayout(2, 1));
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(moveLog != null) {
                    redo(moveLog);
                }
            }
        });
    }

    public void redo(final MoveLog moveLog) {
        this.moveLog = moveLog;

        southPanel.removeAll();
        northPanel.removeAll();

        final List<Piece>  whiteTakenPieces = new ArrayList<>();
        final List<Piece>  blackTakenPieces = new ArrayList<>();

        for(final Move move: moveLog.getMoves()) {
            if(move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceAlliance().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else if(takenPiece.getPieceAlliance().isBlack()) {
                    blackTakenPieces.add(takenPiece);
                } else {
                    throw new RuntimeException("should not reach here!");
                }
            }
        }

        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        // dynamic image scaling

        int panelWidth = getWidth() > 0 ? getWidth() : TAKEN_PIECES_DIMENSION.width;
        int panelHeight = getHeight() > 0 ? getHeight() : TAKEN_PIECES_DIMENSION.height;

        int halfHeight = panelHeight / 2;

        int cellWidth = panelWidth / 2;
        int cellHeight = halfHeight / 8;

        int sideLength = Math.max(10, Math.min(cellWidth, cellHeight));

        for(final Piece takenPiece: whiteTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(new File("art/pieces/plain/"
                         + takenPiece.getPieceAlliance().toString().substring(0, 1) + "" + takenPiece.toString()
                         + ".gif"));
                final Image scaledImage = image.getScaledInstance(sideLength, sideLength, Image.SCALE_SMOOTH);
                final JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                this.southPanel.add(imageLabel);
            } catch(final IOException e) {
                throw new RuntimeException(e);
            }
        }

        for(final Piece takenPiece: blackTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(new File("art/pieces/plain/"
                        + takenPiece.getPieceAlliance().toString().substring(0, 1) + "" + takenPiece.toString()
                        + ".gif"));
                final Image scaledImage = image.getScaledInstance(sideLength, sideLength, Image.SCALE_SMOOTH);
                final JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                this.northPanel.add(imageLabel);
            } catch(final IOException e) {
                throw new RuntimeException(e);
            }
        }
        validate();
        repaint();
    }
}