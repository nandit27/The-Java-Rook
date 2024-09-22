package Chess;

public class Bishop extends Piece {

    public Bishop(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {

        // Convert positions like "C1" to board indices
        int currentRow = Character.getNumericValue(position.charAt(1)) - 1;  // '1' -> 7
        int currentCol = position.charAt(0) - 'A';  // 'C' -> 2
        
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;  // '3' -> 5
        int newCol = newPosition.charAt(0) - 'A';  // 'E' -> 4

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;  // Out of bounds
        }

        // Prevent moving to the same position
        if (newPosition.equals(position)) {
            return false;  // Can't move to the same position
        }

        // Check if the move is diagonal (difference in rows == difference in columns)
        if (Math.abs(newRow - currentRow) == Math.abs(newCol - currentCol)) {

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
    
            // Allow the move if the target square is empty or contains an opponent's piece
            return board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite;
        }

        return false;  // Invalid Move
    }
}