public class Pawn extends Piece {

    public Pawn(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(position.charAt(1)) - 1;  // '2' -> 6
        int currentCol = Character.toUpperCase(position.charAt(0)) - 'A';  // 'A' -> 0
        
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;  // '3' -> 5
        int newCol = Character.toUpperCase(newPosition.charAt(0)) - 'A';  // 'A' -> 0

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return "Out of bounds";
        }

        // Determine the direction of movement based on color (White moves "up", Black moves "down")
        int direction = isWhite ? 1 : -1;

        // Basic move (one square forward)
        if (newCol == currentCol && newRow == currentRow + direction) {
            if (board[newRow][newCol] == null) {
                return "Valid Move";
            }  // Only move forward if the square is empty
        }

        // Initial two-square move
        else if (newCol == currentCol && newRow == currentRow + 2 * direction) {
            // Pawns can move two squares forward only if they're at their initial position and both squares are empty
            if ((isWhite && currentRow == 1) || (!isWhite && currentRow == 6)) {
                if (board[newRow][newCol] == null && board[currentRow + direction][currentCol] == null) {
                    justMoved = true;
                    return "Valid Move";
                }
            }
        }

        // Capture move (diagonal move)
        else if (Math.abs(newCol - currentCol) == 1 && newRow == currentRow + direction) {
            Piece target = board[newRow][newCol];
            if (target != null) {
                if (target.isWhite() != isWhite) {
                    return "Valid Move";
                }
            } else {
                if (newCol - currentCol == -1) {
                    try {   
                        Piece leftPiece = board[currentRow][currentCol - 1];
                        if (isWhite && currentRow == 4 && leftPiece != null && !leftPiece.isWhite && leftPiece.justMoved) {
                            return "En-Passant " + (newCol);
                        }
                        if (!isWhite && currentRow == 3 && leftPiece != null && leftPiece.isWhite && leftPiece.justMoved) {
                            return "En-Passant " + (newCol);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {}
                } else {
                    try {   
                        Piece rightPiece = board[currentRow][currentCol + 1];
                        if (isWhite && currentRow == 4 && rightPiece != null && !rightPiece.isWhite && rightPiece.justMoved) {
                            return "En-Passant " + (newCol);
                        }
                        if (!isWhite && currentRow == 3 && rightPiece != null && rightPiece.isWhite && rightPiece.justMoved) {
                            return "En-Passant " + (newCol);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            }
        }
        return "Invalid Move";
    }
}