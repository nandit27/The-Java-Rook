package Chess;

public class Knight extends Piece {

    public Knight(String position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public boolean isValidMove(String newPosition, Piece[][] board) {
        // Convert positions like "A2" to board indices
        int currentRow = 8 - Character.getNumericValue(position.charAt(1));
        int currentCol = position.charAt(0) - 'A';
        
        int newRow = 8 - Character.getNumericValue(newPosition.charAt(1));
        int newCol = newPosition.charAt(0) - 'A';

        // Check if the move is an L-shape
        int rowDiff = Math.abs(newRow - currentRow);
        int colDiff = Math.abs(newCol - currentCol);
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return board[newRow][newCol] == null || board[newRow][newCol].isWhite() != isWhite;
        }

        return false;  // Knight's L-shape move
    }
}
