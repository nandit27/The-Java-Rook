package Chess;

public class Bishop extends Piece {

    public Bishop(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {

        // Convert positions like "C1" to board indices
        int currentRow = 8 - Character.getNumericValue(position.charAt(1));  // '1' -> 7
        int currentCol = position.charAt(0) - 'A';  // 'C' -> 2
        
        int newRow = 8 - Character.getNumericValue(newPosition.charAt(1));  // '3' -> 5
        int newCol = newPosition.charAt(0) - 'A';  // 'E' -> 4

        // Check if the move is diagonal (difference in rows == difference in columns)
        if (Math.abs(newRow - currentRow) != Math.abs(newCol - currentCol)) {
            return false;  // Bishop can only move diagonally
        }

        // Determine direction of movement for both row and column
        int rowDirection = (newRow > currentRow) ? 1 : -1;
        int colDirection = (newCol > currentCol) ? 1 : -1;

        // Check each square along the diagonal path for obstructions
        int tempRow = currentRow + rowDirection;
        int tempCol = currentCol + colDirection;
        
        while (tempRow != newRow && tempCol != newCol) {
            if (board[tempRow][tempCol] != null) {
                return false;  // If any square along the path is occupied, the move is invalid
            }
            tempRow += rowDirection;
            tempCol += colDirection;
        }

        // Capture logic: If the destination square has a piece, ensure it's an opposing piece
        Piece target = board[newRow][newCol];
        if (target != null && target.isWhite() == this.isWhite) {
            return false;  // Invalid if the piece at the destination is of the same color
        }

        return true;  // The move is valid
    }
}