package Chess;

public class Game {
    private ChessBoard board;
    private boolean isWhiteTurn;

    public Game() {
        board = new ChessBoard();
        isWhiteTurn = true;
    }

    public void startGame() {
        board.printBoard();
        // Implement the game loop where players make moves and the board is updated
    }

    public void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }
}
