import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessUI extends JFrame {
    private ChessBoard board;
    private boolean isWhiteTurn;
    private String selectedPosition;

    public ChessUI() {
        board = new ChessBoard();
        isWhiteTurn = true;
        selectedPosition = null;

        setTitle("The Java Rook");
        setSize(600, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ChessPanel panel = new ChessPanel();
        add(panel);
        setVisible(true);
    }
    
    private BufferedImage loadPieceImage(String color, String pieceName) {
        try {
            return ImageIO.read(new File("pieceImages\\" + color + pieceName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class ChessPanel extends JPanel {
        private String highlightedSquare = null;

        public ChessPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    handleMousePress(e);
                }
            });
        }

        private void handleMousePress(MouseEvent e) {
            int col = e.getX() / (getWidth() / 8);
            int row = e.getY() / (getHeight() / 8);
            String position = "" + (char) ('A' + col) + (8 - row);

            if (selectedPosition == null) {
                Piece piece = board.getPieceAt(position);
                if (piece != null && piece.isWhite() == isWhiteTurn) {
                    selectedPosition = position;
                    highlightedSquare = position;
                    repaint();
                }
            } else {
                if (board.movePiece(selectedPosition, position)) {
                    isWhiteTurn = !isWhiteTurn;
                }
                selectedPosition = null;
                highlightedSquare = null;
                repaint();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard(g);
        }

        private void drawBoard(Graphics g) {
            int tileSize = getWidth() / 8;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    String currentPosition = "" + (char) ('A' + col) + (8 - row);
                    
                    if ((row + col) % 2 == 0) {
                        g.setColor(new Color(235, 237, 209));
                    } else {
                        g.setColor(new Color(114, 149, 83));
                    }
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);

                    // Highlight selected square
                    if (currentPosition.equals(highlightedSquare)) {
                        g.setColor(new Color(255, 255, 0, 100)); // Semi-transparent yellow
                        g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                    }

                    Piece piece = board.getPieceAt(currentPosition);
                    if (piece != null) {
                        String color = piece.isWhite() ? "w" : "b";
                        BufferedImage pieceImage = loadPieceImage(color, piece.getClass().getSimpleName().toLowerCase().substring(0, 1));
                        if (pieceImage != null) {
                            g.drawImage(pieceImage, col * tileSize, row * tileSize, tileSize, tileSize, this);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessUI::new);
    }
}