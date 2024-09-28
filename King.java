package TheJavaRook;

public class King extends Piece {
    public King(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(getPosition().charAt(1)) - 1;
        int currentCol = Character.toUpperCase(getPosition().charAt(0)) - 'A';
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;
        int newCol = Character.toUpperCase(newPosition.charAt(0)) - 'A';

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;  // Out of bounds
        }

        // Check if moving to the same position
        if (newPosition.equals(getPosition())) {
            return false;  // Can't move to the same position
        }

        // Check if the move is one square in any direction
        int rowDiff = Math.abs(currentRow - newRow);
        int colDiff = Math.abs(currentCol - newCol);

        if (rowDiff <= 1 && colDiff <= 1) {
            // Allow the move if the target square is empty or contains an opponent's piece
            return board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite;
        }

        return false; // Invalid move
    }
}