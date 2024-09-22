package Chess;

import java.util.Scanner;

public class Game {
    private ChessBoard board;
    private boolean isWhiteTurn;

    public Game() {
        board = new ChessBoard();
        isWhiteTurn = true;
    }

    public void startGame() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                board.printBoard(isWhiteTurn);
                System.out.println((isWhiteTurn ? "White" : "Black") + "'s turn.");

                // Get the current and new position for the move
                System.out.print("Enter the current position of the piece: ");
                String currentPosition = scanner.nextLine();
                System.out.print("Enter the new position: ");
                String newPosition = scanner.nextLine();

                Piece piece = board.getPieceAt(currentPosition);

                if (piece == null || piece.isWhite() != isWhiteTurn) {
                    System.out.println("Invalid selection, try again.");
                    continue;
                }

                if (board.movePiece(currentPosition, newPosition)) {
                    switchTurn();
                } else {
                    System.out.println("Invalid move, try again.");
                }
            }
        }
    }

    private void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }
}
