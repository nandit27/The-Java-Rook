public abstract class Piece {
    protected String position; // Current position of piece on board
    protected boolean isWhite; // Indicate whether piece is white (true) or black (false)
    protected String positionOfKing;
    protected boolean hasMoved = false;
    protected boolean justMoved = false;
    
    // Constructor to initialize the piece's position and color
    public Piece(String position, boolean isWhite) {
        this.position = position;
        this.isWhite = isWhite;
        if (isWhite) {
            positionOfKing = "E1";
        } else {
            positionOfKing = "E8";
        }
    }

    // Each specific piece will implement this method to define its own movement rules
    public abstract String isValidMove(String newPosition, Piece[][] board);

    // Method to get position of piece
    public String getPosition() {
        return position;
    }

    public void setPosition(String newPosition) {
        this.position = newPosition;
    }
    
    // Method to check if the piece is White
    public boolean isWhite() {
        return isWhite;
    }

    // Override toString for better output
    @Override
    public String toString() {
        return (isWhite ? "White" : "Black") + " piece at " + position;
    }
}