public class Rook extends Piece {

    public Rook(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(position.charAt(1)) - 1;
        int currentCol = Character.toUpperCase(position.charAt(0)) - 'A';
        
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;
        int newCol = Character.toUpperCase(newPosition.charAt(0)) - 'A';

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return "Out of bounds";
        }

        // Prevent moving to the same position
        if (newPosition.equals(position)) {
            return "Can't move to the same position";
        }

        // Check if the move is either vertical or horizontal
        if (currentRow == newRow || currentCol == newCol) {
            // Check for obstacles between the current position and new position
            int rowStep = Integer.compare(newRow, currentRow);
            int colStep = Integer.compare(newCol, currentCol);
            
            int row = currentRow + rowStep;
            int col = currentCol + colStep;
            while (row != newRow || col != newCol) {
                if (board[row][col] != null) {
                    return "Invalid Move";  // Blocked by another piece
                }
                row += rowStep;
                col += colStep;
            }
            // Allow the move if the target square is empty or contains an opponent's piece
            if (board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite) {
                return "Valid Move";
            }
        }

        return "Invalid Move";
    }
}