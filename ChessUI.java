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
            // Update the file path to load the image for the piece
            return ImageIO.read(new File("pieceImages\\"+color + pieceName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if the image cannot be loaded
        }
    }

    private class ChessPanel extends JPanel {
        public ChessPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int col = e.getX() / (getWidth() / 8);
                    int row = e.getY() / (getHeight() / 8);
                    String position = "" + (char) ('A' + col) + (8 - row);

                    if (selectedPosition == null) {
                        Piece piece = board.getPieceAt(position);
                        if (piece != null && piece.isWhite() == isWhiteTurn) {
                            selectedPosition = position;
                        }
                    } else {
                        if (board.movePiece(selectedPosition, position)) {
                            isWhiteTurn = !isWhiteTurn;
                        }
                        selectedPosition = null;
                    }
                    repaint();
                }
            });
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
                    if ((row + col) % 2 == 0) {
                        g.setColor(new Color(235, 237, 209));
                    } else {
                        g.setColor(new Color(114, 149, 83));
                    }
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);

                    Piece piece = board.getPieceAt("" + (char) ('A' + col) + (8 - row));
                    if (piece != null) {
                        // Determine the color of the piece and load its image
                        String color = piece.isWhite() ? "w" : "b";
                        // Load the image based on the piece's color and type
                        BufferedImage pieceImage = loadPieceImage(color, piece.getClass().getSimpleName().toLowerCase().substring(0, 1));
                        if (pieceImage != null) {
                            // Draw the piece image on the board
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