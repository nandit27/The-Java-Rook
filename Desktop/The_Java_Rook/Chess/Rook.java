package Chess;

public class Rook extends Piece {

    public Rook(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = 8 - Character.getNumericValue(position.charAt(1));
        int currentCol = position.charAt(0) - 'A';
        
        int newRow = 8 - Character.getNumericValue(newPosition.charAt(1));
        int newCol = newPosition.charAt(0) - 'A';

        // Check if the move is either vertical or horizontal
        if (currentRow == newRow || currentCol == newCol) {
            // Check for obstacles between the current position and new position
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

        return false;  // Rook moves in a straight line
    }
}
