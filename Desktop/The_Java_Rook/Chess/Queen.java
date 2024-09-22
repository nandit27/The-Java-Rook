package Chess;

public class Queen extends Piece {

    public Queen(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(position.charAt(1)) - 1;
        int currentCol = Character.toUpperCase(position.charAt(0)) - 'A';

        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;
        int newCol = Character.toUpperCase(newPosition.charAt(0)) - 'A';

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;  // Out of bounds
        }

        // Prevent moving to the same position
        if (newPosition.equals(position)) {
            return false;  // Can't move to the same position
        }

        // Check if the move is vertical, horizontal, or diagonal
        if (currentRow == newRow || currentCol == newCol || Math.abs(newRow - currentRow) == Math.abs(newCol - currentCol)) {
            // Check for obstacles in the path
            int rowStep = Integer.compare(newRow, currentRow);  // Can be -1, 0, or 1
            int colStep = Integer.compare(newCol, currentCol);  // Can be -1, 0, or 1

            int row = currentRow + rowStep;
            int col = currentCol + colStep;
            while (row != newRow || col != newCol) {
                if (board[row][col] != null) {
                    return false;  // Blocked by another piece
                }
                row += rowStep;
                col += colStep;
            }

            // Allow the move if the target square is empty or contains an opponent's piece
            return board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite;
        }

        return false;  // Invalid move
    }
}