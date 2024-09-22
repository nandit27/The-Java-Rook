package Chess;

public class Queen extends Piece {

    public Queen(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = 8 - Character.getNumericValue(position.charAt(1));
        int currentCol = position.charAt(0) - 'A';
        
        int newRow = 8 - Character.getNumericValue(newPosition.charAt(1));
        int newCol = newPosition.charAt(0) - 'A';

        // Check if the move is vertical, horizontal, or diagonal
        if (currentRow == newRow || currentCol == newCol || Math.abs(newRow - currentRow) == Math.abs(newCol - currentCol)) {
            // Check for obstacles
            int rowStep = Integer.compare(newRow, currentRow);
            int colStep = Integer.compare(newCol, currentCol);
            
            int row = currentRow + rowStep;
            int col = currentCol + colStep;
            while (row != newRow || col != newCol) {
                if (board[row][col] != null) {
                    return false;  // Blocked by another piece
                }
                row += rowStep;
                col += colStep;
            }
            return board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite;
        }

        return false;  // Queen moves like a Rook or Bishop
    }
}