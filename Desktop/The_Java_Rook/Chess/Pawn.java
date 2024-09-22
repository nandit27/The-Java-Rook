package Chess;

public class Pawn extends Piece {

    public Pawn(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(position.charAt(1)) - 1;  // '2' -> 6
        int currentCol = Character.toUpperCase(position.charAt(0)) - 'A';  // 'A' -> 0
        
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;  // '3' -> 5
        int newCol = Character.toUpperCase(newPosition.charAt(0)) - 'A';  // 'A' -> 0

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;  // Out of bounds
        }

        // Determine the direction of movement based on color (White moves "up", Black moves "down")
        int direction = isWhite ? 1 : -1;

        // Basic move (one square forward)
        if (newCol == currentCol && newRow == currentRow + direction) {
            return board[newRow][newCol] == null;  // Only move forward if the square is empty
        }

        // Initial two-square move
        if (newCol == currentCol && newRow == currentRow + 2 * direction) {
            // Pawns can move two squares forward only if they're at their initial position and both squares are empty
            if ((isWhite && currentRow == 1) || (!isWhite && currentRow == 6)) {
                return board[newRow][newCol] == null && board[currentRow + direction][currentCol] == null;
            }
        }

        // Capture move (diagonal move)
        if (Math.abs(newCol - currentCol) == 1 && newRow == currentRow + direction) {
            Piece target = board[newRow][newCol];
            return target != null && target.isWhite() != isWhite;  // Capture only if the target piece is of the opposite color
        }

        return false;  // If none of the conditions are met, the move is invalid
    }
}