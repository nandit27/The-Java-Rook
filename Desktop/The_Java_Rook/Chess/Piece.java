package Chess;

public abstract class Piece {
    protected String position; //Current position of piece on board
    protected boolean isWhite; //Indicate whether piece is white(true) or black(false)
    
    //Constructor to initialise the piece's position and color
    public Piece(String position,boolean isWhite){
        this.position=position;
        this.isWhite=isWhite;
    }

    //Each Specific Piece will implement this method to define its own movement rules
    public abstract boolean isValidMove(String newPosition,Piece[][] board);

    //Method to move the piece to a new position if move is valid
    public void move(String newPosition,Piece[][] board){
        if(isValidMove(newPosition,board)){
            this.position=newPosition;
        }
        else{
            System.out.println("Invalid Move!");
        }
    }

    //Method to get position of piece
    public String getPosition(){
        return position;
    }

    //Method to set new position for piece
    public void setPosition(String position){
        this.position=position;
    }

    //Method to check if the piece is White
    public boolean isWhite(){
        return isWhite;
    }

    //Method to set the piece's color
    public void setWhite(boolean isWhite){
        this.isWhite=isWhite;
    }
}
