package Chess;

public class King extends Piece {
    public King(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Check if the move is one square in any direction
        int currentRow = 8 - Character.getNumericValue(getPosition().charAt(1));
        int currentCol = getPosition().charAt(0) - 'A';
        int newRow = 8 - Character.getNumericValue(newPosition.charAt(1));
        int newCol = newPosition.charAt(0) - 'A';

        int rowDiff = Math.abs(currentRow - newRow);
        int colDiff = Math.abs(currentCol - newCol);

        if (rowDiff > 1 || colDiff > 1 || (rowDiff == 0 && colDiff == 0)) {
            return false; // Invalid move
        }

        // Check if the destination square is occupied by the same color
        Piece destinationPiece = board[newRow][newCol];
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false; // Can't capture own piece
        }

        return true; // Valid move
    }
}
