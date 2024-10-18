public class King extends Piece {
    public King(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public String isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(getPosition().charAt(1)) - 1;
        int currentCol = Character.toUpperCase(getPosition().charAt(0)) - 'A';
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;
        int newCol = Character.toUpperCase(newPosition.charAt(0)) - 'A';

        // Ensure within board limits
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return "Out Of Bounds"; 
        }

        // Check if moving to the same position
        if (newPosition.equals(getPosition())) {
            return "Can't move to the same position"; 
        }

        // Check Castling

        if (!hasMoved) {
            if (isWhite) {
                if (newPosition.equals("G1") && !board[0][7].hasMoved && board[0][5] == null && board[0][6] == null) {
                    return "Castling to G1";
                }
                if (newPosition.equals("C1") && !board[0][0].hasMoved && board[0][1] == null && board[0][2] == null && board[0][3] == null) {
                    return "Castling to C1";
                }
            } else {
                if (newPosition.equals("G8") && !board[7][7].hasMoved && board[7][5] == null && board[7][6] == null) {
                    return "Castling to G8";
                }
                if (newPosition.equals("C8") && !board[7][0].hasMoved && board[7][1] == null && board[7][2] == null && board[7][3] == null) {
                    return "Castling to C8";
                }
            }
        }
        
        // Check if the move is one square in any direction
        int rowDiff = Math.abs(currentRow - newRow);
        int colDiff = Math.abs(currentCol - newCol);

        if (rowDiff <= 1 && colDiff <= 1) {
            // Allow the move if the target square is empty or contains an opponent's piece
            if (board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite) {
                return "Valid Move";
            }
        }

        return "Invalid Move"; 
    }
}